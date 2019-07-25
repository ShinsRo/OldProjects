package com.siotman.batchwos.wsclient.search;

import com.siotman.batchwos.wsclient.WsUtil;
import com.siotman.batchwos.wsclient.auth.AuthClient;
import com.siotman.batchwos.wsclient.search.domain.*;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SearchClient {
    private Logger logger = LoggerFactory.getLogger(SearchClient.class);
    private final String WS_WOS_URL = "http://search.webofknowledge.com/esti/wokmws/ws/WokSearchLite";

    private SearchMessageGen searchMessageGen;
    private SOAPConnection soapConnection;

    private AuthClient authClient;
    private String SID;
    private SearchResponse currentSearchResponse;

    public SearchClient() throws SOAPException { }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (SID != null) this.close();
    }

    public SearchResponse getCurrentSearchResponse() {
        return currentSearchResponse;
    }

    public String connect() throws SOAPException {
        authClient = new AuthClient();
        SID = authClient.authenticate().getSID();

        soapConnection = SOAPConnectionFactory.newInstance().createConnection();
        searchMessageGen = SearchMessageGen.getInstance();

        return this.SID;
    }

    public void close() throws SOAPException {
        authClient.closeSession(this.SID);
        this.SID = null;
        this.authClient = null;
        this.soapConnection = null;
    }

    public SearchResponse search(
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
        Assert.notNull(this.SID, "SID를 초기화하지 않고 요청할 수 없습니다.");

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

        SOAPMessage soapResponse = soapConnection.call(soapMessage, WS_WOS_URL);
//        WsUtil.printSOAPMessage(soapResponse, "Operation, search received :", System.out);

        SearchResponse response = new SearchResponse(soapResponse);
        this.currentSearchResponse = response;
        return response;
    }

    public SOAPMessage retrieve(
            String queryId,
            Long firstRecord, Long count,
            String sortFieldName, String sortFieldSort,
            String optionRecordIDs
    ) throws SOAPException {
        Assert.notNull(this.SID, "SID를 초기화하지 않고 요청할 수 없습니다.");

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
//        WsUtil.printSOAPMessage(soapMessage, "Operation, retrieve sending :", System.out);

        SOAPMessage soapResponse = soapConnection.call(soapMessage, WS_WOS_URL);
//        WsUtil.printSOAPMessage(soapResponse, "Operation, retrieve received :", System.out);

        return soapResponse;
    }

    public void retrieveById() {
        Assert.notNull(this.SID, "SID를 초기화하지 않고 요청할 수 없습니다.");

    }

    public String getSID() {
        return this.SID;
    }

}