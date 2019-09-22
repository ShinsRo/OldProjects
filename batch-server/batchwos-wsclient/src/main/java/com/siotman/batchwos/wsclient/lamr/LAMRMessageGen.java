package com.siotman.batchwos.wsclient.lamr;

import com.siotman.batchwos.wsclient.lamr.domain.LamrRequestParameters;
import com.siotman.batchwos.wsclient.lamr.domain.TARGET_DB_TYPE;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class LAMRMessageGen {
    private String WS_WOS_USERNAME = System.getenv("WS_WOS_USERNAME");
    private String WS_WOS_PASSWORD = System.getenv("WS_WOS_PASSWORD");

    private LAMRMessageGen() {
    }

    private static LAMRMessageGen instance;

    public static synchronized LAMRMessageGen getInstance() {
        if (instance == null) {
            instance = new LAMRMessageGen();
        }
        return instance;
    }

    public String getXmlData(TARGET_DB_TYPE targetDbType, List<LamrRequestParameters> params) {
        Resource resource = new ClassPathResource(targetDbType.getResourceFileName());
        String ret = null;
        try {
            final StringBuilder xmlBuilder = new StringBuilder();
            InputStream is = resource.getInputStream();

            buildXml(xmlBuilder, is, params);
            ret = xmlBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    private void buildXml(StringBuilder xmlBuilder, InputStream is, List<LamrRequestParameters> params)
            throws IOException {
        int c;
        while ((c = is.read()) != -1) {
            if (c == '$') {
                StringBuilder placeHolderBuilder = new StringBuilder();

                is.read(); // '{'
                while ((c = is.read()) != '}') {
                    placeHolderBuilder.append((char) c);
                }

                String placeholder = placeHolderBuilder.toString();

                if (placeholder.equals("lookupData")) {
                    params.stream()
                            .forEach(lrp -> xmlBuilder.append(lrp.toString()));
                } else if (placeholder.equals("USERNAME")) {
                    xmlBuilder.append(WS_WOS_USERNAME);
                } else {
                    xmlBuilder.append(WS_WOS_PASSWORD);
                }
                continue;
            }
            xmlBuilder.append((char) c);
        }

    }
}
