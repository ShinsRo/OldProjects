package com.siotman.wsclient.auth;

import javax.xml.soap.*;
import java.util.Base64;

public class AuthMessageGen {

    private final String NS_AUTH = "http://auth.cxf.wokmws.thomsonreuters.com";

    private AuthMessageGen() {}
    private static AuthMessageGen instance;

    public static synchronized AuthMessageGen getInstance() {
        if (instance == null) {
            instance = new AuthMessageGen();
        }
        return instance;
    }

    /**
     * 인증 서비스 SOAP 요청 메세지 제작
     *
     * Constructed SOAP Request Message:
     *  <SOAP-ENV:Envelope  xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
     *                      xmlns:auth="http://auth.cxf.wokmws.thomsonreuters.com">
     *      <SOAP-ENV:Header/>
     *      <SOAP-ENV:Body>
     *          <auth:authenticate/>
     *      </SOAP-ENV:Body>
     *  </SOAP-ENV:Envelope>
     *
     * @return 인증 요청 메세지
     * @throws SOAPException
     */
    public SOAPMessage authenticate(String username, String password) throws SOAPException {
        SOAPMessage soapMessage = newSOAPMessage();

        // SOAP Body
        SOAPBody soapBody = soapMessage.getSOAPPart().getEnvelope().getBody();
        soapBody.addChildElement("authenticate", "auth");

        // Headers
        MimeHeaders headers = soapMessage.getMimeHeaders();

        String authorization    = String.format("%s:%s", username, password);
        authorization           = Base64.getEncoder()
                                        .encodeToString(authorization.getBytes());
        authorization           = String.format("Basic %s", authorization);

        headers.addHeader("Authorization", authorization);

        soapMessage.saveChanges();

        return soapMessage;
    }

    /**
     * SID에 해당하는 세션 종료
     * <p>
     * Constructed SOAP Request Message:
     *  <SOAP-ENV:Envelope  xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
     *                      xmlns:auth="http://auth.cxf.wokmws.thomsonreuters.com">
     *      <SOAP-ENV:Header/>
     *      <SOAP-ENV:Body>
     *         <authenticate:closeSession/>
     *      </SOAP-ENV:Body>
     *  </SOAP-ENV:Envelope>
     *
     * @param SID
     * @return
     * @throws SOAPException
     */
    public SOAPMessage closeSession(String SID) throws SOAPException {
        SOAPMessage soapMessage = newSOAPMessage();

        // SOAP Body
        SOAPBody soapBody = soapMessage.getSOAPPart().getEnvelope().getBody();
        soapBody.addChildElement("closeSession", "auth");

        // Headers
        MimeHeaders headers = soapMessage.getMimeHeaders();

        String cookie = String.format("SID=%s", SID);
        headers.addHeader("Cookie", cookie);

        soapMessage.saveChanges();

        return soapMessage;
    }

    private SOAPMessage newSOAPMessage() throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("auth", NS_AUTH);

        return soapMessage;
    }
}
