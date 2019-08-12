package com.siotman.batchwos.wsclient.auth;

import com.siotman.batchwos.wsclient.WsUtil;
import com.siotman.batchwos.wsclient.auth.domain.AuthResponse;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import static com.siotman.batchwos.wsclient.BatchwosWsclientApplication.WS_WOS_PASSWORD;
import static com.siotman.batchwos.wsclient.BatchwosWsclientApplication.WS_WOS_USERNAME;

public class AuthClient {
    private final String WS_WOS_URL = "http://search.webofknowledge.com/esti/wokmws/ws/WOKMWSAuthenticate";

    private final AuthMessageGen authMessageGen;
    private final SOAPConnection soapConnection;

    public AuthClient() throws SOAPException {
        this.soapConnection = SOAPConnectionFactory.newInstance().createConnection();
        this.authMessageGen = AuthMessageGen.getInstance();
    }

    public AuthResponse authenticate() throws SOAPException {
        SOAPMessage soapMessage = authMessageGen.authenticate(WS_WOS_USERNAME, WS_WOS_PASSWORD);

        SOAPMessage soapResponse = soapConnection.call(soapMessage, WS_WOS_URL);
        WsUtil.printSOAPMessage(soapResponse, "Operation, authenticate received :", System.out);

        return new AuthResponse(soapResponse);
    }

    public void closeSession(String SID) throws SOAPException {
        SOAPMessage soapMessage = authMessageGen.closeSession(SID);

        soapConnection.call(soapMessage, WS_WOS_URL);
        soapConnection.close();
    }
}
