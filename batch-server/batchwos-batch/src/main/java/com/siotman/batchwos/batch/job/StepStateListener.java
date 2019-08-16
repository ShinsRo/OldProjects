package com.siotman.batchwos.batch.job;

import com.siotman.batchwos.batch.job.JobStateHolder;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class StepStateListener implements StepExecutionListener {

    private JobStateHolder  jobStateHolder;
    private String          stepName;

    public StepStateListener(JobStateHolder jobStateHolder, String stepName) {
        this.jobStateHolder = jobStateHolder;
        this.stepName       = stepName;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        jobStateHolder.setStepState(stepName, "running");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        jobStateHolder.setStepState(stepName, "done");
        return ExitStatus.COMPLETED;
    }
}
