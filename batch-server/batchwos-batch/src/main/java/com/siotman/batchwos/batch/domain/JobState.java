package com.siotman.batchwos.batch.domain;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class JobState {
    private Long id;
    private LocalDate createdDate;

    private String currentStep;
    private Map<String, StepState> stepStates;

    public JobState() {
        this.createdDate    = LocalDate.now();
    }
}
