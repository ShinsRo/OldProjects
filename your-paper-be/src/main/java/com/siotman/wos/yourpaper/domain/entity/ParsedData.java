package com.siotman.wos.yourpaper.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.siotman.wos.yourpaper.domain.converter.*;
import com.siotman.wos.yourpaper.domain.dto.ParsedDataDto;
import com.siotman.wos.yourpaper.domain.json.CitingPaperJson;
import com.siotman.wos.yourpaper.domain.json.JournalImpactJson;
import com.siotman.wos.yourpaper.domain.json.ParsedAuthorJson;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "parsed_data")
@ToString(exclude = {"paper"})
@Getter
@NoArgsConstructor
public class ParsedData {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "parsedData")
    @JsonIgnore
    private Paper paper;

    private Integer timesCited;

    private String reprint;

    @Convert(converter = JsonListConverter.class)
    private List<String> grades;

    @Lob
    @Convert(converter = JsonMapConverter.class)
    private Map<String, Map<String, Integer>> tcDataJson;

    @Lob
    @Convert(converter = JsonParsedAuthorListConverter.class)
    private List<ParsedAuthorJson> parsedAuthorJsonList;

    @Lob
    @Convert(converter = JsonJournalImpactConverter.class)
    private JournalImpactJson journalImpactJson;

    @Lob
    @Convert(converter = JsonCitingPaperListConverter.class)
    private List<CitingPaperJson> citingPaperJsonList;

    @Builder
    public ParsedData(
            Paper paper,
            Integer timesCited, String reprint,
            List<String> grades, Map<String, Map<String, Integer>> tcDataJson,
            List<ParsedAuthorJson> parsedAuthorJsonList,
            JournalImpactJson journalImpactJson,
            List<CitingPaperJson> citingPaperJsonList
    ) {
        this.paper       = paper;

        this.timesCited = timesCited;
        this.reprint    = reprint;
        this.grades     = grades;
        this.tcDataJson = tcDataJson;
        this.parsedAuthorJsonList   = parsedAuthorJsonList;
        this.journalImpactJson      = journalImpactJson;
        this.citingPaperJsonList    = citingPaperJsonList;
    }
}
