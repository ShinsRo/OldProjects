package com.siotman.batchwos.batch.job;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobStateListener implements JobExecutionListener {

    private JobStateHolder jobStateHolder;

    public JobStateListener(JobStateHolder jobStateHolder) {
        this.jobStateHolder = jobStateHolder;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        jobStateHolder.init();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        jobStateHolder.flush();
    }
}
