package com.kss.wosbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class WosBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(WosBatchApplication.class, args);
    }

}
