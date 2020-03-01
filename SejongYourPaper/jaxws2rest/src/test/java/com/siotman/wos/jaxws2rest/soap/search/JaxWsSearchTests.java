package com.siotman.wos.jaxws2rest.soap.search;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JaxWsSearchTests {
    private final String USERNAME = System.getenv("WS_WOS_USERNAME");
    private final String PASSWORD = System.getenv("WS_WOS_PASSWORD");

    @Test
    public void contextLoads() {
    }

}
