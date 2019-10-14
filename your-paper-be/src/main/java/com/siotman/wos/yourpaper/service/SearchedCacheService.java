package com.siotman.wos.yourpaper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SearchedCacheService {
    @Autowired
    RedisTemplate redisTemplate;
}
