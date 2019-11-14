package com.siotman.wos.yourpaper.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.siotman.wos.jaxws2rest.domain.dto.LamrResultsDto;
import com.siotman.wos.jaxws2rest.domain.dto.LiteRecordDto;
import com.siotman.wos.yourpaper.domain.dto.SourceInfoDto;
import lombok.*;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "source_info")
@ToString(exclude = {"paper"})
@Getter
@NoArgsConstructor
public class SourceInfo {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "sourceInfo")
    @JsonIgnore
    private Paper paper;

    @Column(length = 128)
    private String sourceTitle;

    @Column(length = 16)
    private String isbn;

    @Column(length = 16)
    private String eissn;

    @Column(length = 16)
    private String issn;

    @Column(length = 16)
    private String volume;

    @Column(length = 16)
    private String pages;

    @Column(length = 16)
    private String issue;

    @Column(length = 8)
    private String publishedYear;

    @Column(length = 8)
    private String publishedDate;

    @Builder
    public SourceInfo(
            String sourceTitle, String isbn, String eissn,
            String issn, String volume, String pages,
            String issue, String publishedYear, String publishedDate
            ) {
        this.sourceTitle    = sourceTitle;
        this.isbn           = isbn;
        this.eissn          = eissn;
        this.issn           = issn;
        this.volume         = volume;
        this.pages          = pages;
        this.issue          = issue;
        this.publishedYear  = publishedYear;
        this.publishedDate  = publishedDate;
    }

    public static SourceInfo buildWithCacheData(LiteRecordDto liteRecordDto) {
        Map<String, String> source = liteRecordDto.getSource();

        return SourceInfo.builder()
                .sourceTitle(source.get("sourceTitle"))
                .eissn(source.get("eissn"))
                .issn(source.get("issn"))
                .isbn(source.get("isbn"))
                .volume(source.get("volume"))
                .pages(source.get("pages"))
                .issue(source.get("issue"))
                .publishedYear(source.get("publishedYear"))
                .publishedDate(source.get("publishedDate"))
                .build();
    }
}
