package com.siotman.batchwos.wsclient.search;

import com.siotman.batchwos.wsclient.WsUtil;
import com.siotman.batchwos.wsclient.search.domain.*;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public class SearchClient {
    private final String WS_WOS_URL = "http://search.webofknowledge.com/esti/wokmws/ws/WokSearchLite";
    private final SearchMessageGen searchMessageGen;
    private final SOAPConnection soapConnection;

    public SearchClient() throws SOAPException {
        this.soapConnection = SOAPConnectionFactory.newInstance().createConnection();
        this.searchMessageGen = SearchMessageGen.getInstance();
    }

    public void retrieve(
            String SID, String queryId,
            Long firstRecord, Long count,
            String sortFieldName, String sortFieldSort,
            String optionRecordIDs
    ) throws SOAPException {
        RetrieveParameters retrieveParameters =
                RetrieveParameters.builder()
                        .firstRecord(firstRecord)
                        .count(count)
                        .sortField(SortField.builder()
                                .name(sortFieldName)
                                .sort(sortFieldSort)
                                .build())
                        .option(Option.builder()
                                .recordIDs(optionRecordIDs)
                                .build())
                        .build();

        SOAPMessage soapMessage = searchMessageGen.retrieve(SID, queryId, retrieveParameters);
        WsUtil.printSOAPMessage(soapMessage, "Operation, retrieve sending :", System.out);

        SOAPMessage soapResponse = soapConnection.call(soapMessage, WS_WOS_URL);
        WsUtil.printSOAPMessage(soapResponse, "Operation, retrieve received :", System.out);
    }

    public void retrieveById() {

    }

    public void search(
            String SID,
            String databaseId, String userQuery,
            String editionsCollection,
            String editionsEdition,
            String symbolicTimeSpan,
            String timeSpanBegin,
            String timeSpanEnd,
            String queryLanguage,
            Long firstRecord, Long count,
            String sortFieldName, String sortFieldSort,
            String optionRecordIDs
    ) throws SOAPException {
        SearchQueryParameters searchQueryParameters =
                SearchQueryParameters.builder()
                        .databaseId(databaseId)
                        .userQuery(userQuery)
                        .editions(Editions.builder()
                                .collection(editionsCollection)
                                .edition(editionsEdition)
                                .build())
                        .symbolicTimeSpan(symbolicTimeSpan)
                        .timeSpan(TimeSpan.builder()
                                .begin(timeSpanBegin)
                                .end(timeSpanEnd)
                                .build())
                        .queryLanguage(queryLanguage)
                        .build();

        RetrieveParameters retrieveParameters =
                RetrieveParameters.builder()
                        .firstRecord(firstRecord)
                        .count(count)
                        .sortField(SortField.builder()
                                .name(sortFieldName)
                                .sort(sortFieldSort)
                                .build())
                        .option(Option.builder()
                                .recordIDs(optionRecordIDs)
                                .build())
                        .build();

        SOAPMessage soapMessage = searchMessageGen.search(SID, searchQueryParameters, retrieveParameters);
        WsUtil.printSOAPMessage(soapMessage, "Operation, search sending :", System.out);

        SOAPMessage soapResponse = soapConnection.call(soapMessage, WS_WOS_URL);
        WsUtil.printSOAPMessage(soapResponse, "Operation, search received :", System.out);
    }
}