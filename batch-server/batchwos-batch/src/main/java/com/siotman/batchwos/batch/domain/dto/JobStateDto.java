package com.siotman.batchwos.batch.domain.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class JobStateDto {
    private String currentStep;
    private Map<String, StepStateDto> stepStates;
}
