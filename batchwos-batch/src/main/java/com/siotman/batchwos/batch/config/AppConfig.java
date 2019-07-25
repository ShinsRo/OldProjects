package com.siotman.batchwos.batch.config;

import com.siotman.batchwos.wsclient.lamr.LAMRClient;
import com.siotman.batchwos.wsclient.search.SearchClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import javax.xml.soap.SOAPException;
import java.io.IOException;

@Configuration
public class AppConfig {
    @Bean
    public SearchClient searchClient() throws SOAPException {
        SearchClient sc = new SearchClient();
        sc.connect();
        return sc;
    }

    @Bean
    public LAMRClient lamrClient() throws IOException {
        LAMRClient lc = new LAMRClient();
        return lc;
    }
}
