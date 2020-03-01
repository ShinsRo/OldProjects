package com.siotman.wos.jaxws2rest.redis;

import com.siotman.wos.jaxws2rest.component.AuthSidContainer;
import com.siotman.wos.jaxws2rest.component.LamrServiceWrapper;
import com.siotman.wos.jaxws2rest.component.SearchServiceWrapper;
import com.siotman.wos.jaxws2rest.domain.dto.LamrResultsDto;
import com.siotman.wos.jaxws2rest.domain.dto.LiteRecordDto;
import com.siotman.wos.jaxws2rest.domain.dto.SearchResultsDto;
import com.siotman.wos.jaxws2rest.exception.SidContainerException;
import com.thomsonreuters.wokmws.v3.woksearchlite.QueryParameters;
import com.thomsonreuters.wokmws.v3.woksearchlite.RetrieveParameters;
import com.thomsonreuters.wokmws.v3.woksearchlite.SearchResults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@IfProfileValue(name = "redis-test", value = "true")
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTests {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    AuthSidContainer sidContainer;
    @Autowired
    SearchServiceWrapper searchServiceWrapper;
    @Autowired
    LamrServiceWrapper lamrServiceWrapper;


    @Test
    public void allSearchedResultsShouldBeStored() throws SidContainerException, Exception {
        String SID = sidContainer.getSessionId(
                System.getenv("WS_WOS_USERNAME"),
                System.getenv("WS_WOS_PASSWORD")
        );

        QueryParameters queryParameters = QueryParameters.builder()
                .databaseId("WOS")
                .userQuery("AD=(Sejong Univ)")
                .symbolicTimeSpan("1week")
                .queryLanguage("en")
                .build();

        RetrieveParameters retrieveParameters = RetrieveParameters.builder()
                .count(50)
                .firstRecord(1)
                .build();

        SearchResults results = searchServiceWrapper.search(SID, queryParameters, retrieveParameters);
        SearchResultsDto searchResultsDto = new SearchResultsDto(SID, results);
        List<LiteRecordDto> records = searchResultsDto.getRecords();

        records.forEach(record -> {
            redisTemplate.opsForValue().set("SEARCH::" + record.getUid(), record, Duration.ofMinutes(5));

            LiteRecordDto saved = (LiteRecordDto) redisTemplate.opsForValue().get(record.getUid());
            Assert.isTrue(saved.getTitle().equals(record.getTitle()), "SEARCH:: 레디스에 저장된 값과 다릅니다.");
        });

        List<LamrResultsDto> resultsDtos = lamrServiceWrapper.requestCoreCollectionData(records.stream()
                .map(LiteRecordDto::getUid)
                .collect(Collectors.toList())
        );

        resultsDtos.forEach(record -> {
            redisTemplate.opsForValue().set("LAMR::" + record.getUid(), record, Duration.ofMinutes(5));

            LamrResultsDto saved = (LamrResultsDto) redisTemplate.opsForValue().get("LAMR::" + record.getUid());
            Assert.isTrue(saved.getSourceURL().equals(record.getSourceURL()), "LAMR:: 레디스에 저장된 값과 다릅니다.");
        });

    }
}
