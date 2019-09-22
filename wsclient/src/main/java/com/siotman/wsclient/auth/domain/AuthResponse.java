package com.siotman.wsclient.auth.domain;

import lombok.Getter;
import org.w3c.dom.Node;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

@Getter
public class AuthResponse {
    private String SID;

    public AuthResponse(SOAPMessage soapResponse) throws SOAPException {
        SOAPBody soapBody = soapResponse.getSOAPBody();
        Node returnNode = soapBody.getElementsByTagName("return").item(0);

        this.SID = returnNode.getFirstChild().getNodeValue();
    }
}
