package com.siotman.batchwos.batch.domain;

import lombok.Data;

import java.util.Map;

@Data
public class StepState {
    private String stepName;
    private String state;
    private Map<String, Object> result;
}
