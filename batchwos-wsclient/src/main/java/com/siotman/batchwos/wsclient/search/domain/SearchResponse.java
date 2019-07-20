package com.siotman.batchwos.wsclient.search.domain;

import lombok.Data;
import org.w3c.dom.Node;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

@Data
public class SearchResponse {
    String queryId;
    String recordsFound;
    String recordsSearched;

    public SearchResponse(SOAPMessage soapResponse) throws SOAPException {
        SOAPBody soapBody = soapResponse.getSOAPBody();
        this.queryId = soapBody.getElementsByTagName("queryId").item(0).getFirstChild().getNodeValue();
        this.recordsFound = soapBody.getElementsByTagName("recordsFound").item(0).getFirstChild().getNodeValue();
        this.recordsSearched = soapBody.getElementsByTagName("recordsSearched").item(0).getFirstChild().getNodeValue();
    }
}
