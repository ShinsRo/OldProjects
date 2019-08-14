package com.siotman.batchwos.batch.domain.dto;

import lombok.Data;

import java.util.Map;

@Data
public class StepStateDto {
    private String stepName;
    private String state;
    private Map<String, String> result;
}
