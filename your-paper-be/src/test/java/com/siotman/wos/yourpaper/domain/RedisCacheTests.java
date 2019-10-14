package com.siotman.wos.yourpaper.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RedisCacheTests {
    private URL searchUrl   = new URL("localhost:9400/WokService/search");
    private URL retrieveUrl = new URL("localhost:9400/WokService/retrieve");
    private URL lamrUrl     = new URL("localhost:9400/LamrService");
    @Autowired
    private RedisTemplate redisTemplate;

    public RedisCacheTests() throws MalformedURLException { }

    @Test
    public void 검색된_논문들을_캐시로부터_받아볼_수_있어야한다() {
//        HttpURLConnection searchConn =
    }
}
