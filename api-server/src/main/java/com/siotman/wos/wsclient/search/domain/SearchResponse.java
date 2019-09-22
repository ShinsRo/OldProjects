package com.siotman.wos.wsclient.search.domain;

import lombok.Data;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

@Data
public class SearchResponse {
    String queryId;
    Integer recordsFound;
    Integer recordsSearched;

    public SearchResponse(SOAPMessage soapResponse) throws SOAPException {
        SOAPBody soapBody = soapResponse.getSOAPBody();
        this.queryId = soapBody.getElementsByTagName("queryId").item(0).getFirstChild().getNodeValue();
        this.recordsFound = Integer.valueOf(
                soapBody.getElementsByTagName("recordsFound").item(0).getFirstChild().getNodeValue()
        );
        this.recordsSearched = Integer.valueOf(
                soapBody.getElementsByTagName("recordsSearched").item(0).getFirstChild().getNodeValue()
        );
    }
}
