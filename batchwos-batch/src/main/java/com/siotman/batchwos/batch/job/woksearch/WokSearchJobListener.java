package com.siotman.batchwos.batch.job.woksearch;

import com.siotman.batchwos.wsclient.search.SearchClient;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.soap.SOAPException;

public class WokSearchJobListener implements JobExecutionListener {
    @Autowired
    private SearchClient searchClient;

    @Override
    public void beforeJob(JobExecution jobExecution) {
//        try {
//            searchClient.connect();
//        } catch (SOAPException e) {
//            e.printStackTrace();
//        }

        System.out.println("BeforeJob");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
//        try {
//            searchClient.close();
//        } catch (SOAPException e) {
//            e.printStackTrace();
//        }

        System.out.println("AfterJob");
    }
}
