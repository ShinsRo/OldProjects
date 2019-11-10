package com.siotman.wos.jaxws2rest.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiteRecordDto {
    private String uid;
    private String doi;
    private String title;

    private Map<String, String> identifier;
    private Map<String, String> source;

    private Map<String, List<String>> other;

    private List<String> authors;
    private List<String> doctype;
    private List<String> keywords;
}
