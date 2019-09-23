package com.siotman.wos.domain.json;

import com.siotman.wos.domain.jpa.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PaperJson {
    private String uid;
    private String title;

    private List<String> doctype;
    private String volume;
    private String issue;
    private String pages;
    private String publishedDate;
    private String publishedYear;
    private String sourceTitle;

    private String doi;
    private String ids;
    private String eissn;
    private String issn;
    private String isbn;

    private Map<String, Integer> tcData;

    private List<String> authors;
    private List<String> keywords;

    private String sourceURL;
    private String citingArticlesURL;
    private String relatedRecordsURL;

    private String pmid;
    private Integer timesCited;

    private List<String> grades;

    private String recordState;
    private LocalDateTime lastUpdate;

    public PaperJson(Paper paper) {
        this.uid = paper.getUid();
        this.title = paper.getTitle();

        this.doctype = new ArrayList<>();
        for (Doctype doctype: paper.getDoctype()) {
            this.doctype.add(doctype.getValue());
        }

        this.volume = paper.getVolume();
        this.issn = paper.getIssn();
        this.pages = paper.getPages();
        this.publishedDate = paper.getPublishedDate();
        this.publishedYear = paper.getPublishedYear();
        this.sourceTitle = paper.getSourceTitle();

        this.doi = paper.getDoi();
        this.ids = paper.getIds();
        this.eissn = paper.getEissn();
        this.issn = paper.getIssn();
        this.isbn = paper.getIsbn();

        this.tcData = paper.getTcData();

        this.authors = new ArrayList<>();
        for (Author author: paper.getAuthors()) {
            this.authors.add(author.getName() + "(" + author.getFullName() + ")");
        }

        this.keywords = paper.getKeywords();


        this.sourceURL = paper.getSourceUrls().getSourceURL();
        this.citingArticlesURL = paper.getSourceUrls().getCitingArticlesURL();
        this.relatedRecordsURL = paper.getSourceUrls().getRelatedRecordsURL();

        this.pmid = paper.getPmid();
        this.timesCited = paper.getTimesCited();

        this.grades = new ArrayList<>();

        if (paper.getGrades() != null) {
            for (String caped: paper.getGrades().getCaped()) {
                this.grades.add(caped);
            }
        }

        if (paper.getGrades() == null) {
            System.out.println("asdasd");
        }


        recordState = paper.getRecordState().toString();
        this.lastUpdate = paper.getLastUpdate();
    }
}
