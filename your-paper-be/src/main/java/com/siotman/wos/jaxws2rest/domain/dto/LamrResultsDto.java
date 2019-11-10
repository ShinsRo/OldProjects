package com.siotman.wos.jaxws2rest.domain.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
public class LamrResultsDto {
    private String uid;
    private String doi;
    private String pmid;
    private String timesCited;
    private String sourceURL;
    private String citingArticlesURL;
    private String relatedRecordsURL;

    @Builder
    public LamrResultsDto(String uid, String doi, String pmid, String timesCited, String sourceURL, String citingArticlesURL, String relatedRecordsURL) {
        this.uid = uid;
        this.doi = doi;
        this.pmid = pmid;
        this.timesCited = timesCited;
        this.sourceURL = sourceURL;
        this.citingArticlesURL = citingArticlesURL;
        this.relatedRecordsURL = relatedRecordsURL;
    }
}
