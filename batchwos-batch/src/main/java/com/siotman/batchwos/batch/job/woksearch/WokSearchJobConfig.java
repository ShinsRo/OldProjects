package com.siotman.batchwos.batch.job.woksearch;

import com.siotman.batchwos.wsclient.WsUtil;
import com.siotman.batchwos.wsclient.search.SearchClient;
import com.siotman.batchwos.wsclient.search.domain.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.xml.soap.SOAPMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

@Configuration
public class WokSearchJobConfig {
    private String fSep = System.getProperty("file.separator");

    private Logger logger = LoggerFactory.getLogger(WokSearchJobConfig.class);
    private static final Long WS_SEARCH_CHUNK = Long.valueOf(100);
    private static final String WS_SEARCH_TIMESPAN_BEGIN = "1945-01-01";
    private static final String WS_SEARCH_TIMESPAN_END = "2019-01-01";

    @Autowired
    private SearchClient searchClient;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job wokSearchJob() {
        return this.jobBuilderFactory.get("woksearchJob")
                .listener(new WokSearchJobListener())
                .start(searchStep())
                .next(retrieveStep())
                .next(convertStep())
                .build();
    }

    @Bean
    @JobScope
    public Step searchStep() {
        return this.stepBuilderFactory.get("searchStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    searchClient.search(
                            "WOS", "AD=(Sejong Univ)",
                            null, null,
                            null,
                            WS_SEARCH_TIMESPAN_BEGIN, WS_SEARCH_TIMESPAN_END,
                            "en",
                            Long.valueOf(1), Long.valueOf(0),
                            null, null, null
                    );

                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    @JobScope
    public Step retrieveStep() {
        return this.stepBuilderFactory.get("fetchAllWokResponseStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    SearchResponse currentSearchResponse = searchClient.getCurrentSearchResponse();
                    Long firstRecord = (Long) chunkContext.getAttribute("firstRecord");
                    if (firstRecord == null) firstRecord = Long.valueOf(1);

                    SOAPMessage response = searchClient.retrieve(
                            currentSearchResponse.getQueryId(),
                            Long.valueOf(2), WS_SEARCH_CHUNK,
                            null, null, null
                    );

                    File responseFile = new File(String.format("target/temp/fetched_%s.xml".replaceAll("/", fSep), firstRecord));

                    try {
                        responseFile.createNewFile();
                    } catch (IOException ioe) {
                        new File("target/temp").mkdirs();
                        responseFile.createNewFile();
                    }
                    FileOutputStream fos = new FileOutputStream(responseFile);

                    WsUtil.printSOAPMessage(response, "", new PrintStream(fos));

                    firstRecord += WS_SEARCH_CHUNK;
                    chunkContext.setAttribute("firstRecord", firstRecord);

//                    Long recordsFound = Long.valueOf(currentSearchResponse.getRecordsFound());
                    Long recordsFound = Long.valueOf(30);
                    if (firstRecord > recordsFound) return RepeatStatus.FINISHED;
                    else return RepeatStatus.CONTINUABLE;
                })).build();
    }

    @Bean
    @JobScope
    public Step convertStep() {
        return this.stepBuilderFactory.get("convertStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("testStepTasklet");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    @JobScope
    public Step deleteResourcesStep() {
        return this.stepBuilderFactory.get("deleteResourcesStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("testStepTasklet");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    // tag:: aa

    // end:: bb
}
