package com.siotman.wos.domain.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SourceUrls {
    @Id
    @Builder.Default
    private String uid = "";

    @OneToOne
    @PrimaryKeyJoinColumn
    private Paper paper;

    private String sourceURL;
    private String citingArticlesURL;
    private String relatedRecordsURL;
}
