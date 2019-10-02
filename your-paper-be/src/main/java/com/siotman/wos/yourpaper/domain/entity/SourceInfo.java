package com.siotman.wos.yourpaper.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class SourceInfo {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "sourceInfo")
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

}
