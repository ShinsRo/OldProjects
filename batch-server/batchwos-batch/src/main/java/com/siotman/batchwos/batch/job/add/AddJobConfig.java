package com.siotman.batchwos.batch.job.add;

import com.siotman.batchwos.batch.job.JobStateHolder;
import com.siotman.batchwos.batch.job.JobStateListener;
import com.siotman.batchwos.batch.job.StepStateListener;
import com.siotman.batchwos.batch.wrapper.SearchClientWrapper;
import com.siotman.batchwos.wsclient.search.domain.SearchResponse;
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

import java.util.HashMap;
import java.util.Map;

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

    @Autowired private JobStateHolder           addJobStateHolder;

    @Bean
    public Resource[] xmlResources() { return new Resource[]{}; }

    @Bean
    public Job addJob() {
        return this.jobBuilderFactory.get("addJob")
                .incrementer(new RunIdIncrementer())
                .listener(JobStateListener())
                .start( searchStep())
                .next(  retrieveStep())
                .next(  convertStep())
                .build();
    }

    private JobStateListener JobStateListener() {
        return new JobStateListener(addJobStateHolder);
    }

    @Bean
    @JobScope
    public Step searchStep() {
        return this.stepBuilderFactory.get("searchStep")
                .listener(new StepStateListener(addJobStateHolder, "search"))
                .tasklet(((stepContribution, chunkContext) -> {

                    logger.info("[0100] SearchStep Started");
                    searchClientWrapper.connect();

                    logger.info("[0101] Empty resources.");
                    searchClientWrapper.emptyResource();

                    logger.info("[0102] Searching...");
                    SearchResponse search =
                            searchClientWrapper.search(
                                "AD=(Sejong Univ)",
//                                "2018-01-01", "2019-08-14"
                               "1week"
                    );

                    Map<String, Object> result = new HashMap<>();

                    result.put("recordsFound",      search.getRecordsFound());
                    result.put("recordsSearched",   search.getRecordsSearched());

                    addJobStateHolder.setStepResult("search", result);
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    @JobScope
    public Step retrieveStep() {
        return this.stepBuilderFactory.get("retrieveStep")
                .listener(new StepStateListener(addJobStateHolder, "retrieve"))
                .tasklet(((stepContribution, chunkContext) -> {

                    Integer total    = searchClientWrapper.getCurrentSearchResponse().getRecordsFound();
                    Integer cursor  = searchClientWrapper.getRecordCursor();

                    String LOG_MSG = String.format("[0201] RetrieveStep in [%d/%d]", cursor, total);
                    logger.info(LOG_MSG);

                    searchClientWrapper.retrieveCurrent();

                    Map<String, Object> stepResult = new HashMap<>();
                    if (!stepResult.containsKey("amount")) stepResult.put("total", total);

                    Integer retrieved   = searchClientWrapper.getRecordCursor();
                    retrieved           = (retrieved > total)? total: retrieved;

                    stepResult.put("retrieved", retrieved);
                    addJobStateHolder.setStepResult("retrieve", stepResult);

                    if (searchClientWrapper.hasNext())  return RepeatStatus.CONTINUABLE;
                    else                                return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    @JobScope
    public Step convertStep() {
        return this.stepBuilderFactory.get("convertStep")
                .listener(new StepStateListener(addJobStateHolder, "convert"))
                .listener(  convertStepListener)
                .chunk(     RETRIEVE_CNT_CONSTRAINT)
                .reader(    convertStepReader)
                .processor( convertStepProcessor)
                .writer(    convertStepWriter)

                .build();
    }
}
