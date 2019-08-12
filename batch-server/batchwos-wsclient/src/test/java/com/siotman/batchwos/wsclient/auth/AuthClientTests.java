package com.siotman.batchwos.wsclient.auth;

import com.siotman.batchwos.wsclient.auth.domain.AuthResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.soap.SOAPException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthClientTests {

    @Test
    public void testAuthenticate() {
        try {
            AuthClient ac = new AuthClient();
            AuthResponse authResponse = ac.authenticate();
            System.out.println(authResponse.getSID());
//            ac.closeSession(authResponse.getSID());
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }
}
