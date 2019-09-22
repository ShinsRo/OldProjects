package com.siotman.wos.wsclient;

import com.siotman.wos.wsclient.search.SearchClient;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.xml.soap.SOAPException;
import java.util.LinkedList;
import java.util.List;

@Component
public class SearchClientPool {

    private Integer poolSize        = 5;
    private Integer nextPoolIdx     = 0;
    private List<SearchClient> pool   = new LinkedList<>();

    private void increasePoolIdx() {
        nextPoolIdx += (nextPoolIdx) % poolSize;
    }

    public SearchClient getSearchClient() throws SOAPException {
        SearchClient client;
        if (nextPoolIdx <= pool.size()) {
            client = new SearchClient();
            client.connect();

            pool.add(client);
        }

        if (pool.get(nextPoolIdx).getValidity() - System.nanoTime() < 0) {
            renewSID(nextPoolIdx);
        }

        if (pool.get(nextPoolIdx) == null) {
            Assert.isTrue(false, "SearchClient 풀에 NULL이 존재합니다.");
        }

        client = pool.get(nextPoolIdx);
        increasePoolIdx();

        return client;
    }

    public SearchClient renewSID(int expiredSidIdx) throws SOAPException {
        SearchClient newSearchClient;
        newSearchClient = new SearchClient();
        newSearchClient.connect();

        pool.set(expiredSidIdx, newSearchClient);
//        if (newSID.equals("")) {
//            throw new Exception("NewSID를 받지 못했습니다.");
//        }
        return newSearchClient;
    }

    public void closeAll() throws SOAPException {
        for (int i = 0; i < pool.size(); i++) {
            SearchClient searchClient = pool.get(i);
            searchClient.close();
        }
    }
}
