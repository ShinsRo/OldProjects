package com.siotman.wos.yourpaper.domain.dto;

import com.siotman.wos.yourpaper.domain.entity.SourceInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SourceInfoDto {
    private String sourceTitle;
    private String isbn;
    private String eissn;
    private String issn;
    private String volume;
    private String pages;
    private String issue;
    private String publishedYear;
    private String publishedDate;

    @Builder
    public SourceInfoDto(
            String sourceTitle, String isbn,
            String eissn, String issn,
            String volume, String pages,
            String issue, String publishedYear,
            String publishedDate
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

    public static SourceInfoDto buildWithEntity(SourceInfo sourceInfo) {
        return SourceInfoDto.builder()
                .sourceTitle(sourceInfo.getSourceTitle())
                .isbn(sourceInfo.getIsbn())
                .eissn(sourceInfo.getEissn())
                .issn(sourceInfo.getIssn())
                .volume(sourceInfo.getVolume())
                .pages(sourceInfo.getIssue())
                .publishedDate(sourceInfo.getPublishedYear())
                .publishedDate(sourceInfo.getPublishedDate())
                .build();
    }
}
