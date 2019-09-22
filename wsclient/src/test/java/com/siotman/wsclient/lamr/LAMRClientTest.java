package com.siotman.wsclient.lamr;

import com.siotman.wsclient.WsUtil;
import com.siotman.wsclient.lamr.domain.LamrRequestParameters;
import com.siotman.wsclient.lamr.domain.TARGET_DB_TYPE;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.transform.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LAMRClientTest {
    private LAMRClient lc;

    @Before
    public void init() {
        try {
            lc = new LAMRClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void requestXmlGenTest() {
        LAMRMessageGen lamrMessageGen = LAMRMessageGen.getInstance();

        List<LamrRequestParameters> params = new ArrayList<>();
        Map<String, String> identifier = new HashMap<>();
        identifier.put("doi", "10.1038/npp.2008.18");
        params.add(
                LamrRequestParameters.builder()
                        .mapName("cite_1")
                        .map(identifier).build()
        );

        String ret = lamrMessageGen.getXmlData(TARGET_DB_TYPE.WOS, params);
        System.out.println(ret);
    }

    @Test
    public void requestTest() throws InterruptedException {
        List<LamrRequestParameters> params = new ArrayList<>();
        Map<String, String> identifier = new HashMap<>();

        identifier.put("doi", "10.1038/npp.2008.18");
        params.add(
                LamrRequestParameters.builder()
                        .mapName("cite_1")
                        .map(identifier).build()
        );

        String response = lc.request(TARGET_DB_TYPE.WOS, params);

        try {
            System.out.println(WsUtil.stringXmlBeautifier(response));
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        params = new ArrayList<>();

        identifier.put("issn", "0077-8923");
        params.add(
                LamrRequestParameters.builder()
                        .mapName("cite_1")
                        .map(identifier).build()
        );

        response = lc.request(TARGET_DB_TYPE.JCR, params);
        try {
            System.out.println(WsUtil.stringXmlBeautifier(response));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
