package com.siotman.wos.yourpaper.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.siotman.wos.jaxws2rest.domain.dto.LamrResultsDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "paper_urls")
@ToString(exclude = {"paper"})
@Getter
@NoArgsConstructor
public class PaperUrls {
    @Id
    @GeneratedValue
    private Long id;
    private String sourceUrl;
    private String citingArticlesUrl;
    private String relatedRecordsUrl;

    @OneToOne(mappedBy = "paperUrls")
    @JsonIgnore
    private Paper paper;

    @Builder
    public PaperUrls(String sourceUrl, String citingArticlesUrl, String relatedRecordsUrl) {
        this.sourceUrl = sourceUrl;
        this.citingArticlesUrl = citingArticlesUrl;
        this.relatedRecordsUrl = relatedRecordsUrl;
    }

    public static PaperUrls buildWithCacheData(LamrResultsDto lamrResultsDto) {
        if (lamrResultsDto == null) return null;

        return PaperUrls.builder()
                .sourceUrl(lamrResultsDto.getSourceURL())
                .citingArticlesUrl(lamrResultsDto.getCitingArticlesURL())
                .relatedRecordsUrl(lamrResultsDto.getRelatedRecordsURL())
                .build();
    }
}
