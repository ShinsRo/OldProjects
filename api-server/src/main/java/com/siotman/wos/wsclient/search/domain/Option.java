package com.siotman.wos.wsclient.search.domain;

import lombok.Builder;
import lombok.Data;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

@Data
@Builder
public class Option implements Parameters {
    private String recordIDs;

    @Override
    public SOAPElement insertElementIn(SOAPElement parent) throws SOAPException {
        if (recordIDs == null) return null;

        SOAPElement option = parent.addChildElement("option");
        if (recordIDs != null) parent.addChildElement("recordIDs").setValue(recordIDs);
        return option;
    }
}