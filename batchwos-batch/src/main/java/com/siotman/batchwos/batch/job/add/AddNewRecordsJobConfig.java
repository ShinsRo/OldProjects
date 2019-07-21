package com.siotman.batchwos.batch.job.add;

import com.siotman.batchwos.batch.domain.xml.XmlListField;
import com.siotman.batchwos.batch.domain.xml.XmlRecord;
import com.siotman.batchwos.batch.domain.xml.XmlStringField;
import com.siotman.batchwos.wsclient.WsUtil;
import com.siotman.batchwos.wsclient.search.SearchClient;
import com.siotman.batchwos.wsclient.search.domain.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.batch.jsr.item.ItemWriterAdapter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import javax.xml.soap.SOAPMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class AddNewRecordsJobConfig {
    private String fSep = System.getProperty("file.separator");

    private Logger logger = LoggerFactory.getLogger(AddNewRecordsJobConfig.class);
    private static final Long WS_SEARCH_CHUNK = Long.valueOf(100);
    private static final String WS_SEARCH_TIMESPAN_BEGIN = "1945-01-01";
    private static final String WS_SEARCH_TIMESPAN_END = "2019-01-01";

    @Autowired
    private SearchClient searchClient;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private TempResourcesResolver tempResourcesResolver;

    @Bean
    public Job addNewRecordsJob() {
        return this.jobBuilderFactory.get("woksearchJob")
                .start(searchNewRecordsStep())
                .next(retrieveNewRecordsStep())
                .next(convertAndInsertStep())
                .build();
    }

    @Bean
    @JobScope
    public Step searchNewRecordsStep() {
        return this.stepBuilderFactory.get("searchNewRecordsStep")
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
    public Step retrieveNewRecordsStep() {
        return this.stepBuilderFactory.get("retrieveNewRecordsStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    SearchResponse currentSearchResponse = searchClient.getCurrentSearchResponse();
                    Long firstRecord = (Long) chunkContext.getAttribute("firstRecord");
                    if (firstRecord == null) firstRecord = Long.valueOf(1);

                    SOAPMessage response = searchClient.retrieve(
                            currentSearchResponse.getQueryId(),
                            Long.valueOf(2), WS_SEARCH_CHUNK,
                            null, null, null
                    );

                    File responseFile = new File(
                            String.format("target/temp/fetched_%s.xml".replaceAll("/", fSep), firstRecord));

                    try {
                        responseFile.createNewFile();
                    } catch (IOException ioe) {
                        new File("target/temp".replace("/", fSep)).mkdirs();
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
    public Step convertAndInsertStep() {
        return this.stepBuilderFactory.get("convertAndInsertStep")
                .chunk(50)
                .reader(multiSearchResponseItemReader())
                .listener(lisntener())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemWriter writer() {
        return new ItemWriterTemp();
    }

    public class ItemWriterTemp implements ItemWriter {

        @Override
        public void write(List list) throws Exception {
            System.out.println("wiriririr");
        }
    }

    @Bean
    public TempLisntener lisntener() {
        return new TempLisntener();
    }

    public class TempLisntener implements ItemReadListener<XmlRecord> {

        @Override
        public void beforeRead() {

        }

        @Override
        public void afterRead(XmlRecord xmlRecord) {
            System.out.println(xmlRecord.getUid());
            xmlRecord.getXmlStringFields().stream().forEach(field -> {
                System.out.println(field.getLabel() + " : " + field.getValue());
            });
            xmlRecord.getXmlListFields().stream().forEach(field -> {
                System.out.println(field.getLabel() + " : " + field.getValue().get(0));
            });
        }

        @Override
        public void onReadError(Exception e) {

        }
    }

    @Bean
    public StaxEventItemReader searchResponseItemReader() {
        return new StaxEventItemReaderBuilder<XmlRecord>()
                .name("searchResponseItemReader")
                .addFragmentRootElements("records")
                .resource(new FileSystemResource("target/temp/fetched_1.xml"))
                .unmarshaller(recordMarshaller())
                .build();
    }

    @Bean
    public XStreamMarshaller recordMarshaller() {
        Map<String, Class> aliases = new HashMap<>();
        aliases.put("records", XmlRecord.class);
        aliases.put("uid", String.class);
        aliases.put("label", String.class);
        aliases.put("value", String.class);
        aliases.put("title", XmlStringField.class);
        aliases.put("doctype", XmlStringField.class);
        aliases.put("source", XmlStringField.class);
        aliases.put("other", XmlStringField.class);
        aliases.put("authors", XmlListField.class);
        aliases.put("keywords", XmlListField.class);

        XStreamMarshaller marshaller = new XStreamMarshaller();
        marshaller.setAliases(aliases);
        return marshaller;
    }

    @Bean
    public MultiResourceItemReader multiSearchResponseItemReader() {
        return new MultiResourceItemReaderBuilder<>()
                .delegate(searchResponseItemReader())
                .name("multiSearchResponseItemReader")
                .resources(tempResourcesResolver
                        .loadResources("target/temp/fetched_*.xml"))
                .build();
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


}
