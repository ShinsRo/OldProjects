package com.siotman.batchwos.batch.config;

import com.siotman.batchwos.wsclient.lamr.LAMRClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class AppConfig {

    @Bean
    public LAMRClient lamrClient() throws IOException {
        LAMRClient lc = new LAMRClient();
        return lc;
    }
}
