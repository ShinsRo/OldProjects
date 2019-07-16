package com.siotman.wosbatch.feature.wsclient.auth;

import com.siotman.wosbatch.feature.wsclient.WsUtil;

import javax.xml.soap.*;

public class AuthClient {
    private final String WS_WOS_URL = "http://search.webofknowledge.com/esti/wokmws/ws/WOKMWSAuthenticate";
    private final AuthMessageGen authMessageGen;
    private final SOAPConnection soapConnection;

    public AuthClient() throws SOAPException {
        this.soapConnection = SOAPConnectionFactory.newInstance().createConnection();
        this.authMessageGen = new AuthMessageGen();
    }

    public AuthResponse authenticate() throws SOAPException {
        SOAPMessage soapMessage = authMessageGen.authenticate("","");
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
