package com.siotman.wos.yourpaper;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@EnableBatchProcessing
@SpringBootApplication
@EntityScan(basePackages = {"com.siotman.wos.yourpaper.domain.entity"})
public class YourPaperBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(YourPaperBeApplication.class, args);
    }

}
