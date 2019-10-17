package com.siotman.wos.yourpaper.service;

import com.siotman.wos.jaxws2rest.domain.dto.LamrResultsDto;
import com.siotman.wos.jaxws2rest.domain.dto.LiteRecordDto;
import com.siotman.wos.yourpaper.domain.entity.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SearchedCacheService {
    private final String REDIS_KEY_SEARCH_PREFIX = "SEARCH::";
    private final String REDIS_KEY_LAMR_PREFIX   = "LAMR::";

    @Autowired
    RedisTemplate redisTemplate;

    public Paper newPaperEntityFromCacheByUid(String uid) {
        LiteRecordDto liteRecordDto     = getLiteRecordCacheByUid(uid);
        LamrResultsDto lamrResultsDto   = getLamrResultsCacheByUid(uid);

        Paper newPaper = Paper.buildWithCacheData(liteRecordDto, lamrResultsDto);
        return newPaper;
    }

    public LiteRecordDto getLiteRecordCacheByUid(String uid) {
        LiteRecordDto liteRecord = (LiteRecordDto) redisTemplate.opsForValue().get(REDIS_KEY_SEARCH_PREFIX + uid);
        return liteRecord;
    }

    public LamrResultsDto getLamrResultsCacheByUid(String uid) {
        LamrResultsDto lamrResults = (LamrResultsDto) redisTemplate.opsForValue().get(REDIS_KEY_LAMR_PREFIX + uid);
        return lamrResults;
    }


}
