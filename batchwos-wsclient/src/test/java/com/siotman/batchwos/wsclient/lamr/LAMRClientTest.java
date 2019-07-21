package com.siotman.batchwos.wsclient.lamr;

import com.siotman.batchwos.wsclient.lamr.domain.LamrRequestParameters;
import com.siotman.batchwos.wsclient.lamr.domain.TARGET_DB_TYPE;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        params.add(
                LamrRequestParameters.builder()
                        .mapName("cite_1")
                        .valName("doi")
                        .value("10.1038/npp.2008.18").build()
        );

        String ret = lamrMessageGen.getXmlData(TARGET_DB_TYPE.WOS, params);
        System.out.println(ret);
    }

    @Test
    public void requestTest() {
        List<LamrRequestParameters> params = new ArrayList<>();

        params.add(
                LamrRequestParameters.builder()
                        .mapName("cite_1")
                        .valName("doi")
                        .value("10.1038/npp.2008.18").build()
        );
        String response = lc.request(TARGET_DB_TYPE.WOS, params);

        System.out.println(response);

        params = new ArrayList<>();

        params.add(
                LamrRequestParameters.builder()
                        .mapName("cite_1")
                        .valName("issn")
                        .value("0077-8923").build()
        );

        response = lc.request(TARGET_DB_TYPE.JCR, params);
        System.out.println(response);
    }
}
