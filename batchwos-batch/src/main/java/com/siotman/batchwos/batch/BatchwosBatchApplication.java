package com.siotman.batchwos.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EnableBatchProcessing
@SpringBootApplication
@EntityScan(basePackages = {"com.siotman.batchwos.batch.domain.jpa"})
public class BatchwosBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchwosBatchApplication.class, args);
    }

}
