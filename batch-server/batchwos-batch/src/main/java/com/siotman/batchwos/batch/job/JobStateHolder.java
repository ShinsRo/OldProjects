package com.siotman.batchwos.batch.job;

import com.siotman.batchwos.batch.domain.JobState;
import com.siotman.batchwos.batch.domain.StepState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class JobStateHolder {
    private Logger logger;

    private JobState jobState;

    private String jobName;
    private String[] steps;

    public JobStateHolder(String jobName) {
        this.jobName = jobName;
        this.logger = LoggerFactory.getLogger(jobName + JobStateHolder.class);

        if (jobName.equals("add")) {
            this.steps = new String[]{"search", "retrieve", "convert"};
        } else if (jobName.equals("update")) {
            this.steps = new String[]{"fetchAndUpdate"};
        }

    }

    public JobState describe() {
        return null;
    }

    public String describeStepResultAsString(String stepName) {
        StepState state = jobState.getStepStates().get(stepName);
        Map<String, Object> result = state.getResult();

        StringBuilder sb = new StringBuilder();
        for (String resultKey : result.keySet()) {
            sb.append(resultKey)
                    .append(": ")
                    .append(result.get(resultKey))
                    .append(",");
        }

        return sb.toString();
    }

    public void init() {
        String LOG_MSG = String.format(
                "[%s] %sJobStateHolder init.",
                "3010", jobName
        );
        logger.info(LOG_MSG);

        jobState = new JobState();
        jobState.setCurrentStep("NONE");

        Map<String, StepState> stepStates = new HashMap<>();

        for (String step : steps) {
            StepState ssd = new StepState();
            ssd.setStepName(step);
            ssd.setState("waiting");
            ssd.setResult(new HashMap<>());

            stepStates.put(step, ssd);
        }

        jobState.setStepStates(stepStates);
    }

    public void setStepState(String stepName, String state) {
        String LOG_MSG = String.format(
                "[%s] %sJobStateHolder setStepState/ NAME: %s, STATE: %s",
                "3010", jobName, stepName, state
        );
        logger.info(LOG_MSG);

        jobState.setCurrentStep(stepName);
        jobState.getStepStates()
                .get(stepName)
                .setState(state);
    }

    public void setStepResult(String stepName, Map<String, Object> result) {
        jobState.setCurrentStep(stepName);
        StepState stepState = jobState.getStepStates().get(stepName);
        Map stepResult = stepState.getResult();

        if (stepResult == null)     stepState.setResult(result);
        else                        stepResult.putAll(result);

        String LOG_MSG = String.format(
                "[%s] %sJobStateHolder setStepResult/ NAME: %s, RESULT: %s",
                "3020", jobName, stepName, describeStepResultAsString(stepName)
        );
        logger.info(LOG_MSG);
    }

    public void flush() {
//        if (this.jobState != null) this.jobState = null;
    }

    public Map<String, Object> getStepResult(String stepName) {
        StepState stepState = jobState.getStepStates().get(stepName);

        return stepState.getResult();
    }
}
