package com.siotman.wos.jaxws2rest.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;

@IfProfileValue(name = "control-test", value = "true")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WokSearchControllerTests {
    @LocalServerPort
    private int port;
    private final String BASE_URL = "http://localhost";


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void searchShouldAcceptParams() { }
}
