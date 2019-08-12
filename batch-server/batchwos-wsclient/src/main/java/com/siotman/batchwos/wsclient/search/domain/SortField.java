package com.siotman.batchwos.wsclient.search.domain;

import lombok.Builder;
import lombok.Data;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

@Data
@Builder
public class SortField implements Parameters {
    private String name;
    private String sort;

    @Override
    public SOAPElement insertElementIn(SOAPElement parent) throws SOAPException {
        if (name == null && sort == null) return null;

        SOAPElement sortField = parent.addChildElement("sortField");
        if (name != null) parent.addChildElement("name").setValue(name);
        if (sort != null) parent.addChildElement("sort").setValue(sort);
        return sortField;
    }
}
