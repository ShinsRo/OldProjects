package com.siotman.wsclient.search.domain;

import lombok.Builder;
import lombok.Data;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

@Data @Builder
public class SearchQueryParameters implements Parameters {
    private String databaseId;
    private String userQuery;
    private Editions editions;
    private String symbolicTimeSpan;
    private TimeSpan timeSpan;
    private String queryLanguage;

    @Override
    public SOAPElement insertElementIn(SOAPElement parent) throws SOAPException {

        SOAPElement queryParameters = parent.addChildElement("queryParameters");
        if (databaseId != null)         queryParameters.addChildElement("databaseId").setValue(databaseId);
        if (userQuery != null)          queryParameters.addChildElement("userQuery").setValue(userQuery);
        if (editions != null)           editions.insertElementIn(queryParameters);
        if (symbolicTimeSpan != null)   queryParameters.addChildElement("symbolicTimeSpan").setValue(symbolicTimeSpan);
        if (timeSpan != null)           timeSpan.insertElementIn(queryParameters);
        if (queryLanguage != null)      queryParameters.addChildElement("queryLanguage").setValue(queryLanguage);
        return queryParameters;
    }
}
