package com.siotman.wos.jaxws2rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JaxwsApplication {
    public static final String WS_WOS_USERNAME = System.getenv("WS_WOS_USERNAME");
    public static final String WS_WOS_PASSWORD = System.getenv("WS_WOS_PASSWORD");

    public static void main(String[] args) {
        SpringApplication.run(JaxwsApplication.class, args);
    }
}
