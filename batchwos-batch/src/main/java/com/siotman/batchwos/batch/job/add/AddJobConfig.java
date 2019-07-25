package com.siotman.batchwos.batch.job.add;

import com.siotman.batchwos.batch.domain.xml.Record;
import com.siotman.batchwos.wsclient.WsUtil;
import com.siotman.batchwos.wsclient.lamr.LAMRClient;
import com.siotman.batchwos.wsclient.lamr.domain.LamrRequestParameters;
import com.siotman.batchwos.wsclient.lamr.domain.TARGET_DB_TYPE;
import com.siotman.batchwos.wsclient.search.SearchClient;
import com.siotman.batchwos.wsclient.search.domain.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import javax.xml.soap.SOAPMessage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class AddJobConfig {

    private Logger logger = LoggerFactory.getLogger(AddJobConfig.class);

    private static final Long WS_SEARCH_CHUNK = Long.valueOf(100);
    private static final String WS_SEARCH_TIMESPAN_BEGIN = "1945-01-01";
    private static final String WS_SEARCH_TIMESPAN_END = "2019-01-01";

    private String fSep = System.getProperty("file.separator");

    @Autowired
    private SearchClient searchClient;
    @Autowired
    private LAMRClient lamrClient;
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


    /* retrieveNewRecordsStep 시작 */
    @Bean
    @JobScope
    public Step retrieveNewRecordsStep() {
        return this.stepBuilderFactory.get("retrieveNewRecordsStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    SearchResponse currentSearchResponse = searchClient.getCurrentSearchResponse();
                    Long firstRecord = (Long) chunkContext.getAttribute("firstRecord");
                    if (firstRecord == null) firstRecord = Long.valueOf(1);

                    logger.info(String.format("%d번 레코드로부터 리트리브", firstRecord));

                    SOAPMessage response = searchClient.retrieve(
                            currentSearchResponse.getQueryId(),
                            firstRecord, WS_SEARCH_CHUNK,
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

                    WsUtil.printSOAPBody(response, new PrintStream(fos));

                    firstRecord += WS_SEARCH_CHUNK;
                    chunkContext.setAttribute("firstRecord", firstRecord);

//                    Long recordsFound = Long.valueOf(currentSearchResponse.getRecordsFound());
                    Long recordsFound = Long.valueOf(1);
                    if (firstRecord > recordsFound) return RepeatStatus.FINISHED;
                    else return RepeatStatus.CONTINUABLE;
                })).build();
    }
    /* retrieveNewRecordsStep 끝 */


    /*
    * convertAndInsertStep 시작
    *
    * searchResponseXmlReader
    *   - recordMarshaller
    * Xml2EntityProcessor
    * JpaWriter
    * */
    @Bean
    @JobScope
    public Step convertAndInsertStep() {
        return this.stepBuilderFactory.get("convertAndInsertStep")
                .chunk(50)
                .reader(searchResponseXmlReader())
                .writer(writer())
                .build();
    }

    @Bean
    public MultiResourceItemReader<Record> searchResponseXmlReader() {
        Resource[] resources = tempResourcesResolver
                .loadResources("file:./target/temp/fetched_*.xml");

        XStreamMarshaller marshaller = new XStreamMarshaller();

        marshaller.setAnnotatedClasses(Record.class);
        marshaller.setAutodetectAnnotations(true);

        StaxEventItemReader<Record> xmlReader = new StaxEventItemReader<>();
        xmlReader.setFragmentRootElementName("records");
        xmlReader.setUnmarshaller(marshaller);

        MultiResourceItemReader<Record> reader = new MultiResourceItemReader<>();
        reader.setDelegate(xmlReader);
        reader.setName("searchResponseXmlReader");
        reader.setResources(resources);

        return reader;
    }

    @Bean
    public XStreamMarshaller recordMarshaller() {
        XStreamMarshaller marshaller = new XStreamMarshaller();

        marshaller.setAnnotatedClasses(Record.class);
        marshaller.setAutodetectAnnotations(true);

        return marshaller;
    }
    /* convertAndInsertStep 끝 */


    @Bean
    public ItemWriter writer() {
        return new ItemWriterTemp();
    }
    public class ItemWriterTemp implements ItemWriter {

        @Override
        public void write(List list) throws Exception {
            System.out.println("테스팅 라이터");
            String SID = searchClient.getSID();
            List<LamrRequestParameters> params = new ArrayList<>();

            int idx = 0;

            for (int i = 0; i < list.size(); i++) {
                String uid = ((Record) list.get(i)).getUid();

                params.add(
                        LamrRequestParameters.builder()
                                .mapName(String.format("cite_%d", ++idx))
                                .valName("ut")
                                .value(uid).build()
                );
            }

            String response = lamrClient.request(TARGET_DB_TYPE.WOS, params);
            System.out.println(response);

            File responseFile = new File(
                    String.format("target/temp/lamr/lamr_res.xml".replaceAll("/", fSep)));

            if(!responseFile.exists()) {
                responseFile.createNewFile();
            }

            FileWriter fw = new FileWriter(responseFile, true);
            fw.write(response);
            fw.close();
        }
    }

    //
}
