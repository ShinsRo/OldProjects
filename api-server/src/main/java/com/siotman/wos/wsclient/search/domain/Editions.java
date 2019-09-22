package com.siotman.wos.wsclient.search.domain;


import lombok.Builder;
import lombok.Data;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

@Data
@Builder
public class Editions implements Parameters {
    private String collection;
    private String edition;

    @Override
    public SOAPElement insertElementIn(SOAPElement parent) throws SOAPException {
        if (collection == null && edition == null) return null;

        SOAPElement editions = parent.addChildElement("editions");
        if (collection != null)     editions.addChildElement("collection").setValue(collection);
        if (edition != null)        editions.addChildElement("edition").setValue(edition);
        return editions;
    }
}
