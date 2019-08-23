package com.siotman.batchwos.batch.job;

import com.siotman.batchwos.batch.component.SocketSessionContainer;
import com.siotman.batchwos.batch.job.JobStateHolder;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import java.io.IOException;

public class StepStateListener implements StepExecutionListener {

    private JobStateHolder  jobStateHolder;
    private SocketSessionContainer sessionContainer;
    private String          stepName;

    public StepStateListener(JobStateHolder jobStateHolder, SocketSessionContainer sessionContainer, String stepName) {
        this.jobStateHolder = jobStateHolder;
        this.sessionContainer = sessionContainer;
        this.stepName       = stepName;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        jobStateHolder.setStepState(stepName, "running");
        try {
            sessionContainer.broadcast(jobStateHolder.describeAsJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        jobStateHolder.setStepState(stepName, "done");
        try {
            sessionContainer.broadcast(jobStateHolder.describeAsJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ExitStatus.COMPLETED;
    }
}
