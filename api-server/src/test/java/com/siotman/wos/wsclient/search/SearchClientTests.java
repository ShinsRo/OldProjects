package com.siotman.wos.wsclient.search;

import com.siotman.wos.wsclient.auth.AuthClient;
import com.siotman.wos.wsclient.search.domain.SearchResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.soap.SOAPException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchClientTests {
    private SearchClient sc;
    private AuthClient ac;
    private String SID;

    @Before
    public void init() {
        try {
            sc = new SearchClient();
            sc.connect();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    @After
    public void close() {
        try {
            sc.close();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearch() {
        try {
            SearchResponse soapResponse = sc.search(
                    "WOS", "TS=(cadmium OR lead)",
                    "WOS", "SCI",
                    null,
                    "2000-01-01", "2011-12-31",
                    "en",
                    1, 5,
                    null, null, null
            );
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRetrieve() {
        try {
            sc.search(
                    "WOS", "AD=(Sejong Univ)",
                    null, null,
                    null,
                    "1945-01-01", "2011-12-31",
                    "en",
                    1, 5,
                    null, null, null
            );

            sc.retrieve(
                    "1",
                    1, 3,
                    null, null, null
            );
            sc.retrieve(
                    "1",
                    1, 3,
                    null, null, null
            );
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }
}
