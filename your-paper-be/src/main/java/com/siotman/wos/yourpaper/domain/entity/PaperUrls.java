package com.siotman.wos.yourpaper.domain.entity;

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
    private Paper paper;

    @Builder
    public PaperUrls(String sourceUrl, String citingArticlesUrl, String relatedRecordsUrl) {
        this.sourceUrl = sourceUrl;
        this.citingArticlesUrl = citingArticlesUrl;
        this.relatedRecordsUrl = relatedRecordsUrl;
    }
}
