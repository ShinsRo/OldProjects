package com.siotman.batchwos.wsclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.Property;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class BatchwosWsclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchwosWsclientApplication.class, args);
    }
}
