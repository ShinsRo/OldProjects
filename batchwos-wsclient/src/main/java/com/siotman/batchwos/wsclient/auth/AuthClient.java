package com.siotman.batchwos.wsclient.auth;

import com.siotman.batchwos.wsclient.WsUtil;
import com.siotman.batchwos.wsclient.auth.domain.AuthResponse;

import javax.xml.soap.*;

public class AuthClient {
    private final String WS_WOS_URL = "http://search.webofknowledge.com/esti/wokmws/ws/WOKMWSAuthenticate";
    private final String WS_WOS_USERNAME = "";
    private final String WS_WOS_PASSWORD = "";

    private final AuthMessageGen authMessageGen;
    private final SOAPConnection soapConnection;

    public AuthClient() throws SOAPException {
        this.soapConnection = SOAPConnectionFactory.newInstance().createConnection();
        this.authMessageGen = AuthMessageGen.getInstance();
    }

    public AuthResponse authenticate() throws SOAPException {
        SOAPMessage soapMessage = authMessageGen.authenticate(WS_WOS_USERNAME, WS_WOS_PASSWORD);
        WsUtil.printSOAPMessage(soapMessage, "Operation, authenticate sending :", System.out);

        SOAPMessage soapResponse = soapConnection.call(soapMessage, WS_WOS_URL);
        WsUtil.printSOAPMessage(soapResponse, "Operation, authenticate received :", System.out);

        return new AuthResponse(soapResponse);
    }

    public void closeSession(String SID) throws SOAPException {
        SOAPMessage soapMessage = authMessageGen.closeSession(SID);
        WsUtil.printSOAPMessage(soapMessage, "Operation, closeSession sending :", System.out);

        SOAPMessage soapResponse = soapConnection.call(soapMessage, WS_WOS_URL);
        WsUtil.printSOAPMessage(soapResponse, "Operation, closeSession received :", System.out);
        soapConnection.close();
    }
}
