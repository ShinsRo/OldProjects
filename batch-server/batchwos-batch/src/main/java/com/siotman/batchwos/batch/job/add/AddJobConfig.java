package com.siotman.batchwos.batch.job.add;

import com.siotman.batchwos.batch.wrapper.SearchClientWrapper;
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
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import static com.siotman.batchwos.batch.common.CONSTANTS.RETRIEVE_CNT_CONSTRAINT;

@Configuration
public class AddJobConfig {
    private Logger logger = LoggerFactory.getLogger(AddJobConfig.class);

    @Autowired private JobBuilderFactory    jobBuilderFactory;
    @Autowired private StepBuilderFactory   stepBuilderFactory;

    @Autowired private SearchClientWrapper  searchClientWrapper;

    @Autowired private ConvertStepListener      convertStepListener;
    @Autowired private MultiResourceItemReader  convertStepReader;
    @Autowired private ItemProcessor            convertStepProcessor;
    @Autowired private ItemWriter               convertStepWriter;


    @Bean
    public Resource[] xmlResources() { return new Resource[]{}; }

    @Bean
    public Job addJob() {
        return this.jobBuilderFactory.get("addJob")
                .incrementer(new RunIdIncrementer())
                .start( searchStep())
                .next(  retrieveStep())
                .next(  convertStep())
                .build();
    }

    @Bean
    @JobScope
    public Step searchStep() {
        return this.stepBuilderFactory.get("searchStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    logger.info("[0100] SearchStep Started");
                    if (searchClientWrapper.getSID() == null) searchClientWrapper.connect();

                    logger.info("[0101] Empty resources.");
                    searchClientWrapper.emptyResource();

                    logger.info("[0102] Searching...");
                    searchClientWrapper.search(
                            "AD=(Sejong Univ)",
//                            "2017-01-01", "2018-01-01"
                           "1week"
                    );
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    @JobScope
    public Step retrieveStep() {
        return this.stepBuilderFactory.get("retrieveStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    Integer left    = searchClientWrapper.getCurrentSearchResponse().getRecordsFound();
                    Integer cursor  = searchClientWrapper.getRecordCursor();

                    String MSG = String.format("[0201] RetrieveStep in [%d/%d]", cursor, left);
                    logger.info(MSG);

                    searchClientWrapper.retrieveCurrent();
                    if (searchClientWrapper.hasNext())  return RepeatStatus.CONTINUABLE;
                    else                                return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    @JobScope
    public Step convertStep() {
        return this.stepBuilderFactory.get("convertStep")
                .listener(  convertStepListener)
                .chunk(     RETRIEVE_CNT_CONSTRAINT)
                .reader(    convertStepReader)
                .processor( convertStepProcessor)
                .writer(    convertStepWriter)

                .build();
    }
}
