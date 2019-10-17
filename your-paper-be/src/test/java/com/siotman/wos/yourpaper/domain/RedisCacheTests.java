package com.siotman.wos.yourpaper.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siotman.wos.jaxws2rest.domain.dto.LamrResultsDto;
import com.siotman.wos.yourpaper.config.RedisConfig;
import com.siotman.wos.jaxws2rest.domain.dto.LiteRecordDto;
import com.siotman.wos.yourpaper.util.SearchTestUtil;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@JsonTest
@ContextConfiguration(classes = {
        RedisConfig.class,
        RedisAutoConfiguration.class
})
public class RedisCacheTests {
    private final String REDIS_KEY_SEARCH_PREFIX = "SEARCH::";
    private final String REDIS_KEY_LAMR_PREFIX   = "LAMR::";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RedisTemplate redisTemplate;

    public RedisCacheTests() throws MalformedURLException { }

    @Test
    @Order(1)
    public void 검색된_논문들을_캐시로부터_받아볼_수_있어야한다() throws IOException {
        String userQuery    = "AU=(KIM)";
        String begin        = "2019-01-02";
        String end          = "2019-02-01";
        Integer firstRecord = 1;
        Integer count       = 10;
        Map responseBodyMap = SearchTestUtil.search(userQuery, begin, end, firstRecord, count);

        String sid          = (String) responseBodyMap.get("sid");
        String queryId      = (String) responseBodyMap.get("queryId");
        List<Map> records   = (List<Map>) responseBodyMap.get("records");

        List<String> uids   = new LinkedList<>();
        records.stream().forEach(record -> {
            LiteRecordDto recordDto =
                    (LiteRecordDto) redisTemplate.opsForValue().get(REDIS_KEY_SEARCH_PREFIX + record.get("uid"));

            uids.add((String) record.get("uid"));
            Assert.notNull(recordDto, "레코드 [" + record.get("uid") + "]가 NULL 입니다.");

        });

        List<Map> lamrRecords = SearchTestUtil.getLamrData(uids);
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


}
