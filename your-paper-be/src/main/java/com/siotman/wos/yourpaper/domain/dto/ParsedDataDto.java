package com.siotman.wos.yourpaper.domain.dto;

import com.siotman.wos.yourpaper.domain.entity.ParsedData;
import com.siotman.wos.yourpaper.domain.entity.RecordState;
import com.siotman.wos.yourpaper.domain.json.CitingPaperJson;
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
    private String uid;
    private RecordState recordState;

    private Integer timesCited;
    private String reprint;
    private List<String> grades;
    private List<ParsedAuthorJson> parsedAuthorList;
    private JournalImpactJson journalImpact;

    private List<CitingPaperJson> citingPaperJsonList;
    private Map<String, Map<String, Integer>> tcData;

    @Builder
    public ParsedDataDto(
            String uid, RecordState recordState,
            Integer timesCited, String reprint,
            List<String> grades,
            List<ParsedAuthorJson> parsedAuthorList,
            JournalImpactJson journalImpactJson,
            List<CitingPaperJson> citingPaperJsonList,
            Map<String, Map<String, Integer>> tcData
    ) {
        this.uid = uid;
        this.recordState = recordState;
        this.timesCited = timesCited;
        this.reprint = reprint;
        this.grades = grades;
        this.parsedAuthorList = parsedAuthorList;
        this.journalImpact = journalImpactJson;

        this.citingPaperJsonList = citingPaperJsonList;
        this.tcData = tcData;
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
