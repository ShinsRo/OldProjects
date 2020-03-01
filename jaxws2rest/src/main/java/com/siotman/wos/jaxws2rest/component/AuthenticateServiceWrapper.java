package com.siotman.wos.jaxws2rest.component;

import com.thomsonreuters.wokmws.cxf.auth.WOKMWSAuthenticate;
import com.thomsonreuters.wokmws.cxf.auth.WOKMWSAuthenticateService;
import org.springframework.stereotype.Component;

import javax.xml.ws.BindingProvider;
import java.util.*;

import static com.siotman.wos.jaxws2rest.component.JaxWsUtils.setHeaderOnPort;

@Component
public class AuthenticateServiceWrapper extends WOKMWSAuthenticateService {
    public WOKMWSAuthenticate getWOKMWSAuthenticatePort(final String username, final String password) {
        WOKMWSAuthenticate authenticatePort = super.getWOKMWSAuthenticatePort();
        String authorization = _encodeAuthorizationInfo(username, password);

        setHeaderOnPort((BindingProvider) authenticatePort, "Authorization", Collections.singletonList(authorization));
        return authenticatePort;
    }

    public WOKMWSAuthenticate getWOKMWSAuthenticatePort(final String SID) {
        WOKMWSAuthenticate authenticatePort =  super.getWOKMWSAuthenticatePort();
        String cookie = String.format("SID=\"%s\"", SID);

        setHeaderOnPort((BindingProvider) authenticatePort, "Cookie", Collections.singletonList(cookie));
        return authenticatePort;
    }

    private String _encodeAuthorizationInfo(final String username, final String password) {
        String authorization    = String.format("%s:%s", username, password);
        authorization           = Base64.getEncoder()
                .encodeToString(authorization.getBytes());

        authorization           = String.format("Basic %s", authorization);
        return authorization;
    }
}
