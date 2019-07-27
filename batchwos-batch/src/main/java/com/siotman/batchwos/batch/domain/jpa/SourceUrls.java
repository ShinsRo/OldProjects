package com.siotman.batchwos.batch.domain.jpa;

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
    @GeneratedValue
    private Long id;
    @OneToOne
    @PrimaryKeyJoinColumn
    private Paper paper;
    private String sourceURL;
    private String citingArticlesURL;
    private String relatedRecordsURL;
}
