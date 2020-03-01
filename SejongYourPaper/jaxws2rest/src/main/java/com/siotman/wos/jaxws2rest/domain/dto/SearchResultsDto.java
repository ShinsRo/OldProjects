package com.siotman.wos.jaxws2rest.domain.dto;

import com.thomsonreuters.wokmws.v3.woksearchlite.SearchResults;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultsDto {
    private String SID;
    private String queryId;
    private int recordsFound;
    private long recordsSearched;

    private List<LiteRecordDto> records;

    public SearchResultsDto(String SID, SearchResults results) {
        this.SID                = SID;
        this.queryId            = results.getQueryId();
        this.recordsFound       = results.getRecordsFound();
        this.recordsSearched    = results.getRecordsSearched();

        this.records            = results.getRecords()
                .stream()
                .map(LiteRecordDto::new)
                .collect(Collectors.toList());
    }
}
