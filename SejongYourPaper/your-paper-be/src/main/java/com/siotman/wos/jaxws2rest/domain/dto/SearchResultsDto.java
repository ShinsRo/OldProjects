package com.siotman.wos.jaxws2rest.domain.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchResultsDto {
    private String sid;
    private String queryId;
    private int recordsFound;
    private long recordsSearched;

    private List<LiteRecordDto> records;

    @Builder
    public SearchResultsDto(String sid, String queryId, int recordsFound, long recordsSearched, List<LiteRecordDto> records) {
        this.sid = sid;
        this.queryId = queryId;
        this.recordsFound = recordsFound;
        this.recordsSearched = recordsSearched;
        this.records = records;
    }
}
