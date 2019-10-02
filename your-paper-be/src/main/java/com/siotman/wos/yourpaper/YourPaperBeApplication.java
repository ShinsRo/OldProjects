package com.siotman.wos.yourpaper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.siotman.wos.yourpaper.domain.entity"})
public class YourPaperBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(YourPaperBeApplication.class, args);
    }

}
