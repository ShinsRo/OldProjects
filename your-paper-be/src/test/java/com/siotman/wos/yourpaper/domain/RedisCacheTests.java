package com.siotman.wos.yourpaper.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siotman.wos.jaxws2rest.domain.dto.LamrResultsDto;
import com.siotman.wos.yourpaper.config.RedisConfig;
import com.siotman.wos.jaxws2rest.domain.dto.LiteRecordDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@JsonTest
@ContextConfiguration(classes = {
        RedisConfig.class,
        RedisAutoConfiguration.class
})
public class RedisCacheTests {
    private final String REDIS_KEY_SEARCH_PREFIX = "SEARCH::";
    private final String REDIS_KEY_LAMR_PREFIX   = "LAMR::";

    private URL searchUrl   = new URL("http://localhost:9400/WokSearchService/search");
    private URL retrieveUrl = new URL("http://localhost:9400/WokSearchService/retrieve");
    private URL lamrUrl     = new URL("http://localhost:9400/LamrService/lamrCorCollectionData");

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RedisTemplate redisTemplate;

    public RedisCacheTests() throws MalformedURLException { }

    @Test
    @Order(1)
    public void 검색된_논문들을_캐시로부터_받아볼_수_있어야한다() throws IOException {
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
        queryParameters.put("userQuery", "AU=(KIM)");

        timeSpan.put("begin", "2019-01-02");
        timeSpan.put("end", "2019-02-01");
        queryParameters.put("timeSpan", timeSpan);
        queryParameters.put("queryLanguage", "en");

        retrieveParameters.put("firstRecord", 1);
        retrieveParameters.put("count", 10);

        requestBodyMap.put("queryParameters", queryParameters);
        requestBodyMap.put("retrieveParameters", retrieveParameters);

        String responseBody = _postRequest(searchUrl, requestBodyMap);
        Map responseBodyMap = objectMapper.readValue(responseBody, Map.class);

        String sid          = (String) responseBodyMap.get("SID");
        String queryId      = (String) responseBodyMap.get("queryId");
        List<Map> records   = (List<Map>) responseBodyMap.get("records");

        List<String> uids   = new LinkedList<>();
        records.stream().forEach(record -> {
            LiteRecordDto recordDto =
                    (LiteRecordDto) redisTemplate.opsForValue().get(REDIS_KEY_SEARCH_PREFIX + record.get("uid"));

            uids.add((String) record.get("uid"));
            Assert.notNull(recordDto, "레코드 [" + record.get("uid") + "]가 NULL 입니다.");

        });

        Map<String, Object> lamrRequestBodyMap = new HashMap<>();

        lamrRequestBodyMap.put("uids", uids);
        String lamrResponseBody = _postRequest(lamrUrl, lamrRequestBodyMap);

        List<Map> lamrRecords = objectMapper.readValue(lamrResponseBody, List.class);

        List<String> lamrUids   = new LinkedList<>();
        lamrRecords.stream().forEach(record -> {
            LamrResultsDto recordDto =
                    (LamrResultsDto) redisTemplate.opsForValue().get(REDIS_KEY_LAMR_PREFIX +record.get("uid"));

            lamrUids.add((String) record.get("uid"));
            Assert.notNull(recordDto, "LAMR 레코드 [" + record.get("uid") + "]가 NULL 입니다.");
        });

        Assert.isTrue(records.size() == lamrRecords.size(),
                "요청 레코드와 lamr 레코드 사이즈가 다릅니다.");
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
