package com.siotman.wos.wsclient.search.domain;

import lombok.Builder;
import lombok.Data;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

@Data @Builder
public class RetrieveParameters implements Parameters {
    private Integer firstRecord;
    private Integer count;
    private SortField sortField;
    private Option option;

    @Override
    public SOAPElement insertElementIn(SOAPElement parent) throws SOAPException {

        SOAPElement retrieveParameters = parent.addChildElement("retrieveParameters");
        if (firstRecord != null)    retrieveParameters.addChildElement("firstRecord").setValue(String.valueOf(firstRecord));
        if (count != null)          retrieveParameters.addChildElement("count").setValue(String.valueOf(count));
        if (sortField != null)      sortField.insertElementIn(retrieveParameters);
        if (option != null)         option.insertElementIn(retrieveParameters);

        return retrieveParameters;
    }
}
