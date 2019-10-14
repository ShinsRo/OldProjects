package com.siotman.wos.yourpaper.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LamrResultsDto {
    private String uid;
    private String doi;
    private String pmid;
    private String timesCited;
    private String sourceURL;
    private String citingArticlesURL;
    private String relatedRecordsURL;
}
