package com.siotman.wos.yourpaper.domain.dto;

import com.siotman.wos.yourpaper.domain.entity.ParsedData;
import com.siotman.wos.yourpaper.domain.json.JournalImpactJson;
import com.siotman.wos.yourpaper.domain.json.ParsedAuthorJson;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ParsedDataDto {
    private String timesCited;
    private String reprint;
    private List<String> grades;
    private Map<String, Integer> tcData;
    private List<ParsedAuthorJson> parsedAuthorList;
    private JournalImpactJson journalImpact;

    @Builder
    public ParsedDataDto(
            String timesCited, String reprint,
            List<String> grades, Map<String, Integer> tcData,
            List<ParsedAuthorJson> parsedAuthorList,
            JournalImpactJson journalImpactJson
    ) {
        this.timesCited = timesCited;
        this.reprint = reprint;
        this.grades = grades;
        this.tcData = tcData;
        this.parsedAuthorList = parsedAuthorList;
        this.journalImpact = journalImpactJson;
    }

    public static ParsedDataDto buildWithEntity(ParsedData parsedData) {
        if (parsedData == null) return null;

        return ParsedDataDto.builder()
                .timesCited(parsedData.getTimesCited())
                .reprint(parsedData.getReprint())
                .grades(parsedData.getGrades())
                .tcData(parsedData.getTcDataJson())
                .parsedAuthorList(parsedData.getParsedAuthorJsonList())
                .journalImpactJson(parsedData.getJournalImpactJson())
                .build();
    }
}
