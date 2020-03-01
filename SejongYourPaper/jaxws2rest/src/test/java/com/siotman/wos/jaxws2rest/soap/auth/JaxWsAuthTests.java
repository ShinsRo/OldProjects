package com.siotman.wos.jaxws2rest.soap.auth;

import com.siotman.wos.jaxws2rest.component.AuthSidContainer;
import com.siotman.wos.jaxws2rest.component.AuthenticateServiceWrapper;
import com.siotman.wos.jaxws2rest.exception.SidContainerException;
import com.thomsonreuters.wokmws.cxf.auth.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;

@IfProfileValue(name = "auth-test", value = "true")
@RunWith(SpringRunner.class)
@SpringBootTest
public class JaxWsAuthTests {
    private final String USERNAME = System.getenv("WS_WOS_USERNAME");
    private final String PASSWORD = System.getenv("WS_WOS_PASSWORD");

    @Autowired
    private AuthenticateServiceWrapper authenticateServiceWrapper;
    @Autowired
    private AuthSidContainer authSidContainer;

    @Test
    public void testAuthSidContainer() {
        int AMOUNT = 4;

        for (int i = 0; i < AMOUNT; i++) {
            try {
                String SID = authSidContainer.getSessionId(USERNAME, PASSWORD);
                Assert.assertNotNull(SID);
            } catch (SidContainerException e) {
                e.printStackTrace();
            }
        }
        
        authSidContainer.closeAll();
    }

    @Test
    public void testRetrievingSidAndCloseSession() {

        String SID = null;
        try {
            WOKMWSAuthenticate wokmwsAuthenticatePort
                    = authenticateServiceWrapper.getWOKMWSAuthenticatePort(USERNAME, PASSWORD);

            SID = wokmwsAuthenticatePort.authenticate();
        } catch (AuthenticationExceptionException e) {
            e.printStackTrace();
        } catch (ESTIWSExceptionException e) {
            e.printStackTrace();
        } catch (InternalServerExceptionException e) {
            e.printStackTrace();
        } catch (InvalidInputExceptionException e) {
            e.printStackTrace();
        } catch (QueryExceptionException e) {
            e.printStackTrace();
        } catch (SessionExceptionException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(SID);

        try {
            WOKMWSAuthenticate wokmwsAuthenticatePort
                    = authenticateServiceWrapper.getWOKMWSAuthenticatePort(SID);
            wokmwsAuthenticatePort.closeSession();
        } catch (AuthenticationExceptionException e) {
            e.printStackTrace();
        } catch (ESTIWSExceptionException e) {
            e.printStackTrace();
        } catch (InternalServerExceptionException e) {
            e.printStackTrace();
        } catch (InvalidInputExceptionException e) {
            e.printStackTrace();
        } catch (QueryExceptionException e) {
            e.printStackTrace();
        } catch (SessionExceptionException e) {
            e.printStackTrace();
        }
    }

}
