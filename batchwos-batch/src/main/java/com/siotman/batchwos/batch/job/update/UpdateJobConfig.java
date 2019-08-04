package com.siotman.batchwos.batch.job.update;

import com.siotman.batchwos.batch.domain.jpa.Paper;
import com.siotman.batchwos.batch.domain.jpa.RecordState;
import com.siotman.batchwos.batch.repo.PaperRepository;
import com.siotman.batchwos.batch.wrapper.LamrClientWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
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

    @Autowired private RabbitTemplate rabbitTemplate;
//    @Bean
//    public Job updateJob() {
//        return this.jobBuilderFactory.get("updateJob")
//                .incrementer(new RunIdIncrementer())
//                .start(fetchStep())
//                .build();
//    }

    @Bean
    public Step fetchStep() {
        return this.stepBuilderFactory.get("fetchStep")
                .<Paper, Paper>chunk(50)
                .reader(papersReader())
                .writer(updateWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Paper> papersReader() {
        return new JpaPagingItemReaderBuilder<Paper>()
                .name("papersReader")
                .pageSize(50)
                .entityManagerFactory(entityManagerFactory)
                .queryString("select p.uid, p.doi, p.issn, p.publishedYear, p.timesCited from Paper p")
                .build();
    }

    @Bean
    public ItemWriter<Paper> updateWriter() {
        return list -> {
            List<Integer> prevTimesCited = new ArrayList<>();
            list.stream().forEach(paper -> prevTimesCited.add(paper.getTimesCited()));
            lamrClientWrapper.getLamrRecordMap((List<Paper>) list);

            for (int i = 0; i < list.size(); i++) {
                Paper item = list.get(i);
                if (prevTimesCited.get(i).equals(item.getTimesCited())) {
                    item.setRecordState(RecordState.COMPLETED);
                }
                else {
                    item.setRecordState(RecordState.SHOULD_UPDATE);
                    StringBuilder bodyBuilder = new StringBuilder()
                            .append("DETAIL_LINK")                         .append("$,")    // TargetType
                            .append(item.getUid())                         .append("$,")    // UID
                            .append(item.getSourceUrls().getSourceURL())   .append("$,")    // targetURL
                            .append("EXTRA");                                               // EXTRA args

                    rabbitTemplate.convertAndSend(
                            "create",
                            "target.update.record",
                            bodyBuilder.toString()
                    );
                    // 메세지 센딩
                }
            }

            paperRepository.saveAll(list);
        };
    }
}
