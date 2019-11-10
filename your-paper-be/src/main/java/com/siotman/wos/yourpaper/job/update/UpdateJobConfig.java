package com.siotman.wos.yourpaper.job.update;

import com.siotman.wos.jaxws2rest.domain.dto.LamrResultsDto;
import com.siotman.wos.yourpaper.domain.dto.PaperDto;
import com.siotman.wos.yourpaper.domain.entity.MemberPaper;
import com.siotman.wos.yourpaper.domain.entity.Paper;
import com.siotman.wos.yourpaper.domain.entity.RecordState;
import com.siotman.wos.yourpaper.repo.PaperRepository;
import com.siotman.wos.yourpaper.service.AsyncParsingTriggerService;
import com.siotman.wos.yourpaper.service.WokSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class UpdateJobConfig {
    private Logger logger = LoggerFactory.getLogger(UpdateJobConfig.class);

    @Autowired
    private WokSearchService wokSearchService;
    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private AsyncParsingTriggerService parsingTriggerService;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private EntityManagerFactory entityManagerFactory;


    @Bean
    public Job updateJob() {
        return this.jobBuilderFactory.get("updateJob")
                .incrementer(new RunIdIncrementer())
                .start(fetchAndUpdateStep(null))
                .build();
    }

    @Bean
    @JobScope
    public Step fetchAndUpdateStep(@Value("#{jobParameters[launchDate]}")   String launchDate) {
        return this.stepBuilderFactory.get("fetchAndUpdateStep")
                .<Paper, Paper>chunk(50)
                .reader(    papersReader())
                .processor( updateLogProcessor())
                .writer(    updateWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Paper> papersReader() {
        return new JpaPagingItemReaderBuilder<Paper>()
                .name("papersReader")
                .pageSize(50)
                .entityManagerFactory(entityManagerFactory)
                .queryString("select p from Paper p")
                .build();
    }

    @Bean
    public ItemProcessor<Paper, Paper> updateLogProcessor() {
        return paper -> {
            LocalDateTime base      = LocalDateTime.now().minusDays(6);
            boolean isLatest        = paper.getLastUpdates().isAfter(base);
            boolean isInProgress    = paper.getRecordState().equals(RecordState.PARSING);

            if (isLatest || isInProgress) {
                return null;
            } else {
                return paper;
            }
        };
    }

    @Bean
    public ItemWriter<Paper> updateWriter() {

        return list -> {
            if (list.size() == 0) {
                return;
            }

            List<PaperDto> shouldUpdate = new ArrayList<>();
            List<String> prevTimesCited = new ArrayList<>();
            List<String> uids           = new ArrayList<>();
            list.stream().forEach(paper -> {
                prevTimesCited.add(paper.getTimesCited());
                uids.add(paper.getUid());
            });
            List<LamrResultsDto> lamrData = wokSearchService.getLamrData(uids);

            for (int i = 0; i < list.size(); i++) {
                Paper item          = list.get(i);

                Assert.isTrue(item.getUid().equals(lamrData.get(i).getUid()),
                        "DB 레코드와 LMAR레코드의 UID가 일치하지 않으나, 업데이트를 시도했습니다.");

                item.setTimesCited(lamrData.get(i).getTimesCited());

                if (prevTimesCited.get(i).equals(item.getTimesCited())) {
                    item.setRecordState(RecordState.COMPLETED);
                    continue;
                }
                shouldUpdate.add(PaperDto.buildWithMemberPaper(MemberPaper.builder().paper(item).build()));
                item.setRecordState(RecordState.PARSING);

            }
            parsingTriggerService.triggerAll(shouldUpdate);
            paperRepository.saveAll(list);
        };
    }
}