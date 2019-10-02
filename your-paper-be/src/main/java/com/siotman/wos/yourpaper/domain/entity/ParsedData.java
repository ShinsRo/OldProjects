package com.siotman.wos.yourpaper.domain.entity;

import com.siotman.wos.yourpaper.domain.converter.JsonJournalImpactConverter;
import com.siotman.wos.yourpaper.domain.converter.JsonListConverter;
import com.siotman.wos.yourpaper.domain.converter.JsonMapConverter;
import com.siotman.wos.yourpaper.domain.converter.JsonParsedAuthorListConverter;
import com.siotman.wos.yourpaper.domain.json.JournalImpactJson;
import com.siotman.wos.yourpaper.domain.json.ParsedAuthorJson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParsedData {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "parsedData")
    private Paper paper;

    private String timesCited;

    private String reprint;

    @Convert(converter = JsonListConverter.class)
    private List<String> grades;

    @Convert(converter = JsonMapConverter.class)
    private Map<String, Integer> tcDataJson;

    @Lob
    @Convert(converter = JsonParsedAuthorListConverter.class)
    private List<ParsedAuthorJson> parsedAuthorJsonList;

    @Lob
    @Convert(converter = JsonJournalImpactConverter.class)
    private JournalImpactJson journalImpactJson;

}
