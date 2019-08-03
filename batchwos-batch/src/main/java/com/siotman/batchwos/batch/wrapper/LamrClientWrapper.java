package com.siotman.batchwos.batch.wrapper;

import com.siotman.batchwos.batch.domain.jpa.Paper;
import com.siotman.batchwos.batch.domain.jpa.SourceUrls;
import com.siotman.batchwos.batch.domain.xml.XmlLamrResponse;
import com.siotman.batchwos.wsclient.lamr.LAMRClient;
import com.siotman.batchwos.wsclient.lamr.domain.LamrRequestParameters;
import com.siotman.batchwos.wsclient.lamr.domain.TARGET_DB_TYPE;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

@Component
public class LamrClientWrapper extends LAMRClient {
    public LamrClientWrapper() throws IOException {
        super();
    }

    public void getLamrRecordMap(List<Paper> list) throws IOException {
        List<LamrRequestParameters> params  = new ArrayList<>();
        Map<String, Paper> paperMap         = new HashMap<>();

        Iterator<? extends Paper> iter = list.iterator();
        while (iter.hasNext()) {
            Paper paper = iter.next();
            String uid = paper.getUid();

            paperMap.put(uid, paper);

            Map<String, String> identifier = new HashMap<>();

            identifier.put("ut", uid);
            if (paper.getDoi() != null)             identifier.put("doi",   paper.getDoi());
            if (paper.getPublishedYear() != null)   identifier.put("year",  paper.getPublishedYear());
            if (paper.getIssn() != null)            identifier.put("issn",  paper.getIssn());

            params.add(LamrRequestParameters.builder()
                    .mapName(uid)
                    .map(identifier)
                    .build());
        }

        String response = super.request(TARGET_DB_TYPE.WOS, params);

        XStreamMarshaller marshaller = lamrMarshaller();
        StreamSource streamSource = new StreamSource(new StringReader(response));

        XmlLamrResponse lamrResponse = (XmlLamrResponse) marshaller.unmarshal(streamSource);

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

            SourceUrls sourceUrls = SourceUrls.builder()
                    .sourceURL(         sourceURL)
                    .citingArticlesURL( citingArticlesURL)
                    .relatedRecordsURL( relatedRecordsURL)
                    .build();

            targetPaper.setSourceUrls(sourceUrls);
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
