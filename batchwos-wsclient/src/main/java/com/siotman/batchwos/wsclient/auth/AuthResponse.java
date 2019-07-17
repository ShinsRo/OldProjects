package com.siotman.batchwos.wsclient.auth;

import lombok.Getter;
import org.w3c.dom.Node;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

@Getter
public class AuthResponse {
    private String SID;

//    public AuthResponse(String SID) {
//        this.SID = SID;
//    }

    public AuthResponse(SOAPMessage soapResponse) throws SOAPException {
        SOAPBody soapBody = soapResponse.getSOAPBody();
        Node returnNode = soapBody.getElementsByTagName("return").item(0);

        this.SID = returnNode.getFirstChild().getNodeValue();
    }
}
