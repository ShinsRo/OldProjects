package com.siotman.batchwos.batch.job;

import com.siotman.batchwos.batch.component.SocketSessionContainer;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.io.IOException;

public class JobStateListener implements JobExecutionListener {

    private JobStateHolder jobStateHolder;
    private SocketSessionContainer sessionContainer;

    public JobStateListener(JobStateHolder jobStateHolder, SocketSessionContainer sessionContainer) {
        this.jobStateHolder = jobStateHolder;
        this.sessionContainer = sessionContainer;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        jobStateHolder.init();
        try {
            sessionContainer.broadcast(jobStateHolder.describeAsJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        jobStateHolder.flush();
        try {
            sessionContainer.broadcast(jobStateHolder.describeAsJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
