package com.siotman.wos.yourpaper.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaperUrls {
    @Id
    private Long id;
    private String sourceUrl;
    private String citingArticlesUrl;
    private String relatedRecordsUrl;

    @OneToOne(mappedBy = "paperUrls")
    private Paper paper;
}
