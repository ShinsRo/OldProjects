package com.siotman.wos.yourpaper.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

    public WokSearchService() throws MalformedURLException {
    }

    public Map search(
            String userQuery,
            String begin, String end, String week,
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

        if (week != null) {
            queryParameters.put("symbolicTimeSpan", week);
        } else {
            timeSpan.put("begin", begin);
            timeSpan.put("end", end);
            queryParameters.put("timeSpan", timeSpan);
        }

        retrieveParameters.put("firstRecord", firstRecord);
        retrieveParameters.put("count", count);

        requestBodyMap.put("queryParameters", queryParameters);
        requestBodyMap.put("retrieveParameters", retrieveParameters);

        String responseBody = _postRequest(searchUrl, requestBodyMap);
        Map responseBodyMap = objectMapper.readValue(responseBody, Map.class);
        return responseBodyMap;
    }

    public Map retrieve(String sid, String queryId, Integer firstRecord, Integer count) throws IOException {
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
        Map responseBodyMap = objectMapper.readValue(responseBody, Map.class);
        return responseBodyMap;
    }

    public List<Map> getLamrData(List<String> uids) throws IOException {
        Map<String, Object> lamrRequestBodyMap = new HashMap<>();

        lamrRequestBodyMap.put("uids", uids);
        String lamrResponseBody = _postRequest(lamrUrl, lamrRequestBodyMap);

        List<Map> lamrRecords = objectMapper.readValue(lamrResponseBody, List.class);
        return lamrRecords;
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
