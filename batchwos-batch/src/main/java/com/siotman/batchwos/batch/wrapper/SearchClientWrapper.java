package com.siotman.batchwos.batch.wrapper;

import com.siotman.batchwos.wsclient.WsUtil;
import com.siotman.batchwos.wsclient.search.SearchClient;
import com.siotman.batchwos.wsclient.search.domain.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static com.siotman.batchwos.batch.common.CONSTANTS.FILE_SEP;
import static com.siotman.batchwos.batch.common.CONSTANTS.RESOURCE_DIR;

@Component
public class SearchClientWrapper extends SearchClient {
    private Logger logger = LoggerFactory.getLogger(SearchClientWrapper.class);

    private final Integer WS_SEARCH_CHUNK = 100;

    private boolean next = false;
    private Integer recordCursor;

    public SearchClientWrapper() throws SOAPException { super(); }

    public boolean hasNext()            { return next; }
    public Integer getRecordCursor()    { return recordCursor; }


    public SearchResponse search(String query, String week) throws SOAPException {
        this.search(
                "WOS", query,
                null, null,
                week,
                null, null,
                "en",
                1, 0,
                null, null, null
        );
        SearchResponse response = getCurrentSearchResponse();

        this.next = (Integer.valueOf(response.getRecordsFound()) > 0);
        this.recordCursor = 1;
        return getCurrentSearchResponse();
    }

    public SearchResponse search(String query, String from, String to) throws SOAPException {
        this.search(
                "WOS", query,
                null, null,
                null,
                from, to,
                "en",
                1, 0,
                null, null, null
        );

        SearchResponse response = getCurrentSearchResponse();

        this.next = (Integer.valueOf(response.getRecordsFound()) > 0);
        this.recordCursor = 1;
        return getCurrentSearchResponse();
    }

    public boolean retrieveCurrent() throws SOAPException, IOException {
        SearchResponse searchResponse = this.getCurrentSearchResponse();
        String queryId = searchResponse.getQueryId();

        SOAPMessage response = super.retrieve(
                queryId, recordCursor, WS_SEARCH_CHUNK,
                null, null, null
        );

        String filePath = String.format("%s/fetched_%d.xml", RESOURCE_DIR, recordCursor);
        filePath = filePath.replaceAll("/", FILE_SEP);
        File responseFile = new File(filePath);

        try {
            responseFile.createNewFile();
        } catch (IOException ioe) {
            new File(RESOURCE_DIR).mkdirs();
            responseFile.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(responseFile);
        WsUtil.printSOAPBody(response, new PrintStream(fos));

        recordCursor += WS_SEARCH_CHUNK;
        this.next =  getCurrentSearchResponse().getRecordsFound() >= recordCursor;
        return hasNext();
    }

    public void emptyResource() {
        File dir = new File(RESOURCE_DIR);
        int deleted = 0;
        for (File file : dir.listFiles()) { file.delete(); deleted++; }
        logger.info(String.format("%d개 파일 삭제", deleted));
    }
}
