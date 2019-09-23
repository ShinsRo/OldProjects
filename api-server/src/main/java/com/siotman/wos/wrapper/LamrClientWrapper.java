package com.siotman.wos.wrapper;

import com.siotman.wos.domain.jpa.Paper;
import com.siotman.wos.domain.jpa.SourceUrls;
import com.siotman.wos.domain.xml.XmlLamrResponse;
import com.siotman.wos.wsclient.lamr.LAMRClient;
import com.siotman.wos.wsclient.lamr.domain.LamrRequestParameters;
import com.siotman.wos.wsclient.lamr.domain.TARGET_DB_TYPE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.UnmarshallingFailureException;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

@Component
public class LamrClientWrapper extends LAMRClient {
    private Logger logger = LoggerFactory.getLogger(LamrClientWrapper.class);

    public LamrClientWrapper() throws IOException {
        super();
    }

    public enum LAMR_TYPE {
        ADD, UPDATE
    }

    public void getLamrRecordMap(List<Paper> list, LAMR_TYPE lamrType) throws IOException, InterruptedException {
        List<LamrRequestParameters> params  = new ArrayList<>();
        Map<String, Paper> paperMap         = new HashMap<>();

        Iterator<? extends Paper> iter = list.iterator();
        while (iter.hasNext()) {
            Paper paper = iter.next();
            String uid = paper.getUid();

            paperMap.put(uid, paper);

            Map<String, String> identifier = new HashMap<>();

            identifier.put("ut", uid);
//            if (paper.getDoi() != null)             identifier.put("doi",   paper.getDoi());
//            if (paper.getPublishedYear() != null)   identifier.put("year",  paper.getPublishedYear());
//            if (paper.getIssn() != null)            identifier.put("issn",  paper.getIssn());

            params.add(LamrRequestParameters.builder()
                    .mapName(uid)
                    .map(identifier)
                    .build());
        }

        String response = super.request(TARGET_DB_TYPE.WOS, params);

        XStreamMarshaller marshaller = lamrMarshaller();
        StreamSource streamSource = new StreamSource(new StringReader(response));

        XmlLamrResponse lamrResponse = null;
        try {
            lamrResponse = (XmlLamrResponse) marshaller.unmarshal(streamSource);
        } catch (UnmarshallingFailureException ife) {
            logger.error(String.format("Unmarshal error, response: \n %s", response));
            throw ife;
        }

        XmlLamrResponse.Map wrapped = lamrResponse.getFn().getMap();
        Map<String, XmlLamrResponse.Map> recordMap = wrapped.getMap();

        Set<String> uidSet = recordMap.keySet();
        Iterator<String> uidIter = uidSet.iterator();
        while(uidIter.hasNext()) {
            String uid = uidIter.next();
            Paper targetPaper = paperMap.get(uid);

            XmlLamrResponse.Map record = recordMap.get(uid);
            Map<String, XmlLamrResponse.Val> values = record.getMap().get("WOS").getVal();

            String pmid =               (values.containsKey("pmid"))?               values.get("pmid").getValue() : "";
            String sourceURL =          (values.containsKey("sourceURL"))?          values.get("sourceURL").getValue() : "";
            String citingArticlesURL =  (values.containsKey("citingArticlesURL"))?  values.get("citingArticlesURL").getValue() : "";
            String relatedRecordsURL =  (values.containsKey("relatedRecordsURL"))?  values.get("relatedRecordsURL").getValue() : "";
            String timesCited =         (values.containsKey("timesCited"))?         values.get("timesCited").getValue() : "0";

            if          (lamrType.equals(LAMR_TYPE.ADD)) {

                SourceUrls sourceUrls = SourceUrls.builder()
                        .uid(uid)
                        .sourceURL(         sourceURL)
                        .citingArticlesURL( citingArticlesURL)
                        .relatedRecordsURL( relatedRecordsURL)
                        .build();

                targetPaper.setSourceUrls(sourceUrls);

            } else if   (lamrType.equals(LAMR_TYPE.UPDATE)){

                SourceUrls sourceUrls = targetPaper.getSourceUrls();

                sourceUrls.setSourceURL(sourceURL);
                sourceUrls.setRelatedRecordsURL(relatedRecordsURL);
                sourceUrls.setCitingArticlesURL(citingArticlesURL);

            } else {}

            targetPaper.setPmid(pmid);
            targetPaper.setTimesCited(Integer.valueOf(timesCited));
        }
    }

    @Bean
    public XStreamMarshaller lamrMarshaller() {
        XStreamMarshaller marshaller = new XStreamMarshaller();

        marshaller.setAnnotatedClasses(XmlLamrResponse.class);
        marshaller.setAutodetectAnnotations(true);

        return marshaller;
    }
}
