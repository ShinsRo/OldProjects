package com.siotman.batchwos.batch.job.add;

import com.siotman.batchwos.batch.domain.jpa.Paper;
import com.siotman.batchwos.batch.domain.xml.XmlRecord;
import com.siotman.batchwos.batch.domain.xml.XmlRecordList;
import com.siotman.batchwos.batch.repo.PaperRepository;
import com.siotman.batchwos.batch.wrapper.SearchClientWrapper;
import com.siotman.batchwos.wsclient.lamr.LAMRClient;
import com.siotman.batchwos.wsclient.lamr.domain.LamrRequestParameters;
import com.siotman.batchwos.wsclient.lamr.domain.TARGET_DB_TYPE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Configuration
public class ConvertStepConfig {
    private Logger logger = LoggerFactory.getLogger(ConvertStepConfig.class);
    @Autowired private PaperRepository paperRepository;
    @Autowired private LAMRClient lamrClient;

    @Bean
    public MultiResourceItemReader<XmlRecord> convertStepReader() {

        XStreamMarshaller marshaller = new XStreamMarshaller();

        marshaller.setAnnotatedClasses(XmlRecord.class);
        marshaller.setAutodetectAnnotations(true);

        StaxEventItemReader<XmlRecord> xmlReader = new StaxEventItemReader<>();
        xmlReader.setFragmentRootElementName("records");
        xmlReader.setUnmarshaller(marshaller);

        MultiResourceItemReader<XmlRecord> reader = new MultiResourceItemReader<>();
        reader.setDelegate(xmlReader);
        reader.setName("convertStepReader");

        return reader;
    }

    @Bean
    public ItemWriter<Paper> convertStepWriter() {
        return list -> {
            List<LamrRequestParameters> params = new ArrayList<>();

            Iterator<? extends Paper> iter = list.iterator();
            while (iter.hasNext()) {
                Paper paper = iter.next();
                String uid = paper.getUid();

                params.add(LamrRequestParameters.builder()
                        .mapName(uid)
                        .value("ut")
                        .value(uid)
                        .build());
            }

            String response = lamrClient.request(TARGET_DB_TYPE.WOS, params);
            System.out.println(response);

//            paperRepository.saveAll(list);
        };
    }

    @Bean
    public XStreamMarshaller lamrMarshaller() {
        XStreamMarshaller marshaller = new XStreamMarshaller();

        marshaller.setAnnotatedClasses(XmlRecord.class);
        marshaller.setAutodetectAnnotations(true);

        return marshaller;
    }

    @Bean
    public ItemProcessor<XmlRecord, Paper> convertStepProcessor() {
        return xmlRecord -> {
            Paper paper = Paper.builder().build();
            paper.setUid(xmlRecord.getUid());
            paper.setTitle(xmlRecord.getTitle());
            paper.setAuthors(xmlRecord.getAuthors());
            paper.setDoctype(xmlRecord.getDoctype());
            paper.setKeywords(xmlRecord.getKeywords());

            Map<String, XmlRecordList> sources = xmlRecord.getSource();
            Map<String, XmlRecordList> others = xmlRecord.getOther();

            Iterator<String> sourcesIter = sources.keySet().iterator();
            Iterator<String> othersIter = others.keySet().iterator();

            while(sourcesIter.hasNext()) {
                String field = sourcesIter.next();
                List<String> values = sources.get(field).getValue();

                switch (field) {
                    case "Issue":   paper.setIssn(values.get(0));   break;
                    case "Pages":   paper.setPages(values.get(0));  break;
                    case "Volume":  paper.setVolume(values.get(0)); break;
                    case "SourceTitle": paper.setSourceTitle(values.get(0));    break;
                    case "Published.BiblioDate":    paper.setPublishedDate(values.get(0));  break;
                    case "Published.BiblioYear":    paper.setPublishedYear(values.get(0));  break;
                    default: logger.warn(field + "는 알 수 없는 필드입니다. (<source>)");
                }
            }

            while(othersIter.hasNext()) {
                String field = othersIter.next();
                List<String> values = others.get(field).getValue();

                switch (field) {
                    case "Identifier.Doi":      paper.setDoi(values.get(0));    break;
                    case "Identifier.Ids":      paper.setIds(values.get(0));    break;
                    case "Identifier.Eissn":    paper.setEissn(values.get(0));  break;
                    case "Identifier.Issn":     paper.setIssn(values.get(0));   break;
                    default: logger.warn(field + "는 알 수 없는 필드입니다. (<other>)");
                }
            }

            return paper;
        };
    }
}
