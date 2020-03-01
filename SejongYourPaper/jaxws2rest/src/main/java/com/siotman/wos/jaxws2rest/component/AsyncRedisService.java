package com.siotman.wos.jaxws2rest.component;

import com.siotman.wos.jaxws2rest.domain.dto.LamrResultsDto;
import com.siotman.wos.jaxws2rest.domain.dto.LiteRecordDto;
import com.siotman.wos.jaxws2rest.domain.dto.SearchResultsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class AsyncRedisService {
    private final Duration TTL = Duration.ofMinutes(10);

    @Autowired
    AsyncRunner asyncRunner;
    @Autowired
    RedisTemplate redisTemplate;

    public void asyncSaveSearchResults(SearchResultsDto searchResultsDto) {
        asyncRunner.run(() -> saveSearchResults(searchResultsDto));
    }

    public void saveSearchResults(SearchResultsDto searchResultsDto) {
        List<LiteRecordDto> records = searchResultsDto.getRecords();

        records.forEach(record ->
                redisTemplate
                        .opsForValue()
                        .setIfAbsent("SEARCH::" + record.getUid(), record, TTL)
        );
    }

    public void asyncSaveLamrResults(List<LamrResultsDto> lamrResultsDtos) {
        asyncRunner.run(() -> saveLamrResults(lamrResultsDtos));
    }

    public void saveLamrResults(List<LamrResultsDto> lamrResultsDtos) {
        lamrResultsDtos.forEach(record ->
                redisTemplate
                        .opsForValue()
                        .setIfAbsent("LAMR::" + record.getUid(), record, TTL)
        );
    }
}
