package com.siotman.wos.yourpaper.domain.dto;

import com.siotman.wos.yourpaper.domain.entity.PaperUrls;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaperUrlsDto {
    private String sourceUrl;
    private String citingArticlesUrl;
    private String relatedRecordsUrl;

    @Builder
    public PaperUrlsDto(String sourceUrl, String citingArticlesUrl, String relatedRecordsUrl) {
        this.sourceUrl          = sourceUrl;
        this.citingArticlesUrl  = citingArticlesUrl;
        this.relatedRecordsUrl  = relatedRecordsUrl;
    }

    public static PaperUrlsDto buildWithEntity(PaperUrls paperUrls) {
        return PaperUrlsDto.builder()
                .sourceUrl(paperUrls.getSourceUrl())
                .citingArticlesUrl(paperUrls.getCitingArticlesUrl())
                .relatedRecordsUrl(paperUrls.getRelatedRecordsUrl())
                .build();
    }
}

