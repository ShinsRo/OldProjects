package com.siotman.wos.jaxws2rest.component;

import com.thomsonreuters.wokmws.v3.woksearchlite.*;
import org.springframework.stereotype.Component;

import javax.xml.ws.BindingProvider;
import java.util.Collections;

import static com.siotman.wos.jaxws2rest.component.JaxWsUtils.setHeaderOnPort;

@Component
public class SearchServiceWrapper extends WokSearchLiteService {
    public WokSearchLite getWokSearchLitePort(String SID) {
        WokSearchLite searchPort = super.getWokSearchLitePort();
        String cookie = String.format("SID=\"%s\"", SID);

        setHeaderOnPort((BindingProvider) searchPort, "Cookie", Collections.singletonList(cookie));

        return searchPort;
    }

    public SearchResults search(String SID, QueryParameters queryParameters, RetrieveParameters retrieveParameters)
    throws Exception {
        WokSearchLite searchPort = getWokSearchLitePort(SID);
        SearchResults results = searchPort.search(queryParameters, retrieveParameters);

        return results;
    }

    public SearchResults retrieve(String SID, String queryId, RetrieveParameters retrieveParameters)
    throws Exception {
        WokSearchLite searchPort = getWokSearchLitePort(SID);
        SearchResults results = searchPort.retrieve(queryId, retrieveParameters);

        return results;
    }


}
