package com.siotman.wos.yourpaper.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siotman.wos.jaxws2rest.domain.dto.LamrResultsDto;
import com.siotman.wos.jaxws2rest.domain.dto.LiteRecordDto;
import com.siotman.wos.jaxws2rest.domain.dto.SearchResultsDto;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WokSearchService {
    private static URL searchUrl;
    private static URL retrieveUrl;
    private static URL lamrUrl;
    static {
        try {
            searchUrl   = new URL("http://localhost:9400/WokSearchService/search");
            retrieveUrl = new URL("http://localhost:9400/WokSearchService/retrieve");
            lamrUrl     = new URL("http://localhost:9400/LamrService/lamrCorCollectionData");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public static ObjectMapper objectMapper = new ObjectMapper();

    public SearchResultsDto search(String userQuery,
                                   String symbolicTimeSpan,
                                   Integer firstRecord, Integer count) throws IOException {
        return this.search(
                userQuery,
                null, null,
                symbolicTimeSpan,
                firstRecord, count
        );
    }

    public SearchResultsDto search(String userQuery,
                                   String begin, String end,
                                   String symbolicTimeSpan,
                                   Integer firstRecord, Integer count) throws IOException {
        /** WokService/search 예시
         * {
         * 	"queryParameters": {
         * 		"databaseId": "WOS",
         * 		"userQuery": "AU=(Kim)",
         * 		"timeSpan": {
         * 			"begin": "2019-01-02",
         * 			"end": "2019-02-01"
         *                },
         * 		"queryLanguage": "en"
         * 	},
         *
         * 	"retrieveParameters": {
         * 		"firstRecord": 1,
         * 		"count": 50
         * 	}
         * }
         */
        Map<String, Object> requestBodyMap   = new HashMap<>();
        Map<String, Object> queryParameters  = new HashMap<>();
        Map<String, Object> timeSpan         = new HashMap<>();

        Map<String, Object> retrieveParameters = new HashMap<>();

        queryParameters.put("databaseId", "WOS");
        queryParameters.put("userQuery", userQuery);
        queryParameters.put("queryLanguage", "en");

        if          (symbolicTimeSpan != null) {
            queryParameters.put("symbolicTimeSpan", symbolicTimeSpan);
        } else if   (begin != null && end != null){
            timeSpan.put("begin", begin);
            timeSpan.put("end", end);
            queryParameters.put("timeSpan", timeSpan);
        }

        retrieveParameters.put("firstRecord", firstRecord);
        retrieveParameters.put("count", count);

        requestBodyMap.put("queryParameters", queryParameters);
        requestBodyMap.put("retrieveParameters", retrieveParameters);

        String responseBody = _postRequest(searchUrl, requestBodyMap);
        SearchResultsDto resultsDto = objectMapper.readValue(responseBody, SearchResultsDto.class);
        return resultsDto;
    }

    public SearchResultsDto retrieve(String sid, String queryId, Integer firstRecord, Integer count) throws IOException {
        /**
         * {
         * 	"sid": "C5bXQpqPEi3SNYqNxHk",
         * 	"queryId": "1",
         * 	"retrieveParameters": {
         * 		"queryId": "1",
         * 		"firstRecord": 2,
         * 		"count": 20
         *        }
         * }
         */
        Map<String, Object> requestBodyMap   = new HashMap<>();
        Map<String, Object> retrieveParameters = new HashMap<>();

        retrieveParameters.put("queryId", queryId);
        retrieveParameters.put("firstRecord", firstRecord);
        retrieveParameters.put("count", count);

        requestBodyMap.put("sid", sid);
        requestBodyMap.put("queryId", queryId);
        requestBodyMap.put("retrieveParameters", retrieveParameters);

        String responseBody = _postRequest(retrieveUrl, requestBodyMap);
        SearchResultsDto resultsDto = objectMapper.readValue(responseBody, SearchResultsDto.class);
        return resultsDto;
    }

    public List<LamrResultsDto> getLamrData(List<String> uids) throws IOException {
        Map<String, Object> lamrRequestBodyMap = new HashMap<>();

        lamrRequestBodyMap.put("uids", uids);
        String lamrResponseBody = _postRequest(lamrUrl, lamrRequestBodyMap);

        List<LamrResultsDto> lamrRecords = objectMapper.readValue(lamrResponseBody, new TypeReference<List<LamrResultsDto>>(){});
        return lamrRecords;
    }

    public List<LamrResultsDto> getLamrData(SearchResultsDto searchResults) throws IOException {
        List<String> uids = searchResults.getRecords()
                .stream()
                .map(LiteRecordDto::getUid)
                .collect(Collectors.toList());

        return getLamrData(uids);
    }

    private String _postRequest(URL url, Map requestBodyMap) throws IOException {
        String responseBodyString;

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        try(DataOutputStream writer = new DataOutputStream(conn.getOutputStream())) {
            String requestBody = objectMapper.writeValueAsString(requestBodyMap);

            writer.write(requestBody.getBytes());
            writer.flush();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                responseBodyString = reader.lines().collect(Collectors.joining("\n"));
            }
        } finally {
            conn.disconnect();
        }
        Assert.isTrue(conn.getResponseCode() == HttpURLConnection.HTTP_OK,
                "검색 요청이 200 응답 코드를 받지 못했습니다.");
        Assert.notNull(responseBodyString == null || responseBodyString.equals(""),
                "응답 바디가 비어있습니다.");
        return responseBodyString;
    }
}
