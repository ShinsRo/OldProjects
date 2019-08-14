package com.siotman.batchwos.batch.job;

import com.siotman.batchwos.batch.domain.dto.JobStateDto;
import com.siotman.batchwos.batch.domain.dto.StepStateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class JobStateHolder {
    private Logger logger;

    private JobStateDto jobStateDto;

    private String jobName;
    private String[] steps;

    public JobStateHolder(String jobName) {
        this.jobName = jobName;
        this.logger = LoggerFactory.getLogger(jobName + JobStateHolder.class);

        if (jobName.equals("add")) {
            this.steps = new String[]{"fetch", "retrieve", "convert"};
        } else if (jobName.equals("update")) {
            this.steps = new String[]{"fetchAndUpdate"};
        }

    }

    public JobStateDto describe() {
        return null;
    }

    public String describeStepResultAsString(String stepName) {
        StepStateDto state = jobStateDto.getStepStates().get(stepName);
        Map<String, String> result = state.getResult();

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

        jobStateDto = new JobStateDto();
        jobStateDto.setCurrentStep("NONE");

        Map<String, StepStateDto> stepStates = new HashMap<>();

        for (String step : steps) {
            StepStateDto ssd = new StepStateDto();
            ssd.setStepName(step);
            ssd.setState("waiting");
            ssd.setResult(new HashMap<>());
        }

        jobStateDto.setStepStates(stepStates);

    }

    public void setStepState(String stepName, String state) {
        String LOG_MSG = String.format(
                "[%s] %sJobStateHolder setStepState/ NAME: %s, STATE: %s",
                "3010", jobName, stepName, state
        );
        logger.info(LOG_MSG);

        jobStateDto.setCurrentStep(stepName);
        jobStateDto.getStepStates()
                .get(stepName)
                .setState(state);
    }

    public void setStepResult(String stepName, Map<String, String> result) {
        String LOG_MSG = String.format(
                "[%s] %sJobStateHolder setStepResult/ NAME: %s, RESULT: %s",
                "3020", jobName, stepName, describeStepResultAsString(stepName)
        );
        logger.info(LOG_MSG);

        jobStateDto.setCurrentStep(stepName);
        jobStateDto.getStepStates()
                .get(stepName)
                .setResult(result);
    }

    public void flush() {
        if (this.jobStateDto != null) this.jobStateDto = null;
    }
}
