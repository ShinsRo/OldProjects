package com.siotman.wos.yourpaper.service;

import com.siotman.wos.jaxws2rest.domain.dto.LamrResultsDto;
import com.siotman.wos.jaxws2rest.domain.dto.LiteRecordDto;
import com.siotman.wos.yourpaper.domain.entity.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

@Service
public class SearchedCacheService {
    private final String REDIS_KEY_SEARCH_PREFIX = "SEARCH::";
    private final String REDIS_KEY_LAMR_PREFIX   = "LAMR::";

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    WokSearchService searchService;

    public Paper newPaperEntityFromCacheByUid(String uid) {
        LiteRecordDto liteRecordDto     = null;
        LamrResultsDto lamrResultsDto   = null;

        try {
            liteRecordDto       = getLiteRecordCacheByUid(uid);
            lamrResultsDto      = getLamrResultsCacheByUid(uid);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Paper newPaper = Paper.buildWithWokResponse(liteRecordDto, lamrResultsDto);
        return newPaper;
    }

    public LiteRecordDto getLiteRecordCacheByUid(String uid) throws IOException {
        LiteRecordDto liteRecord = (LiteRecordDto) redisTemplate.opsForValue().get(REDIS_KEY_SEARCH_PREFIX + uid);
        if (liteRecord == null) liteRecord = searchService.search("ut=(" + uid + ")", null, null, null, 1, 1).getRecords().get(0);
        return liteRecord;
    }

    public LamrResultsDto getLamrResultsCacheByUid(String uid) throws IOException {
        LamrResultsDto lamrResults = (LamrResultsDto) redisTemplate.opsForValue().get(REDIS_KEY_LAMR_PREFIX + uid);
        if (lamrResults == null) lamrResults = searchService.getLamrData(Collections.singletonList(uid)).get(0);
        return lamrResults;
    }


}
