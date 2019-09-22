package com.siotman.batchwos.batch.job.update;

import com.siotman.batchwos.batch.component.ParsingTrigger;
import com.siotman.batchwos.batch.domain.jpa.Paper;
import com.siotman.batchwos.batch.domain.jpa.RecordState;
import com.siotman.batchwos.batch.repo.PaperRepository;
import com.siotman.batchwos.batch.wrapper.LamrClientWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class UpdateJobConfig {
    private Logger logger = LoggerFactory.getLogger(UpdateJobConfig.class);

    @Autowired private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;

    @Autowired private LamrClientWrapper lamrClientWrapper;
    @Autowired private PaperRepository paperRepository;

    @Autowired private EntityManagerFactory entityManagerFactory;

    @Autowired private ParsingTrigger parsingTrigger;

    @Bean
    public Job updateJob() {
        return this.jobBuilderFactory.get("updateJob")
                .incrementer(new RunIdIncrementer())
                .start(fetchAndUpdateStep())
                .build();
    }

    @Bean
    public Step fetchAndUpdateStep() {
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
            LocalDateTime base      = LocalDateTime.now().minusDays(5);
            boolean isLatest        = paper.getLastUpdate().isAfter(base);
            boolean isInProgress    = paper.getRecordState().equals(RecordState.IN_PROGRESS);

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

            Integer shouldUpdate    = 0;

            List<Integer> prevTimesCited = new ArrayList<>();
            list.stream().forEach(paper -> prevTimesCited.add(paper.getTimesCited()));
            lamrClientWrapper.getLamrRecordMap((List<Paper>) list, LamrClientWrapper.LAMR_TYPE.UPDATE);

            for (int i = 0; i < list.size(); i++) {
                Paper item          = list.get(i);

                if (prevTimesCited.get(i).equals(item.getTimesCited())) {
                    item.setRecordState(RecordState.COMPLETED);
                    continue;
                }

                item.setRecordState(RecordState.SHOULD_UPDATE);
                shouldUpdate++;

                parsingTrigger.startOne(
                        ParsingTrigger.TYPE.UPDATE_PARSE_DETAIL,
                        item,
                        "UPDATE"
                );
            }

            paperRepository.saveAll(list);
        };
    }
}