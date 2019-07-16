package com.siotman.wosbatch.soap;

import com.siotman.wosbatch.feature.wsclient.auth.AuthClient;
import com.siotman.wosbatch.feature.wsclient.auth.AuthResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.soap.SOAPException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAuthClient {

    @Test
    public void testAuthenticate() {
        try {
            AuthClient ac = new AuthClient();
            AuthResponse authResponse = ac.authenticate();

            ac.closeSession(authResponse.getSID());
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }
}
