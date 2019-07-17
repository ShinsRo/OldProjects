package com.siotman.batchwos.wsclient.search;

import com.siotman.batchwos.wsclient.auth.AuthClient;
import com.siotman.batchwos.wsclient.auth.domain.AuthResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.soap.SOAPException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSearchClient {
    private AuthClient ac;
    private String SID;

    @Before
    public void init() {
        try {
            ac = new AuthClient();

            AuthResponse response = ac.authenticate();
            SID = response.getSID();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    @After
    public void close() {
        try {
            ac.closeSession(SID);
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearch() {
        try {
            SearchClient sc = new SearchClient();

            sc.search(
                    SID, "WOS", "TS=(cadmium OR lead)",
                    "WOS", "SCI",
                    null,
                    "2000-01-01", "2011-12-31",
                    "en",
                    Long.valueOf(1), Long.valueOf(5),
                    null, null, null
            );
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRetrieve() {
        try {
            SearchClient sc = new SearchClient();

            sc.search(
                    SID, "WOS", "AD=(Sejong Univ)",
                    null, null,
                    null,
                    "1945-01-01", "2011-12-31",
                    "en",
                    Long.valueOf(1), Long.valueOf(3),
                    null, null, null
            );

            sc.retrieve(
                    SID, "1",
                    Long.valueOf(2), Long.valueOf(1),
                    null, null, null
            );
            sc.retrieve(
                    SID, "1",
                    Long.valueOf(3), Long.valueOf(1),
                    null, null, null
            );
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }
}
