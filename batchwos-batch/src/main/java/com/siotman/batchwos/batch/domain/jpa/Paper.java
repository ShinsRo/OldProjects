package com.siotman.batchwos.batch.domain.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Paper {
    @Id
    private String uid;
    @Column(length = 1023)
    private String title;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Doctype> doctype;
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Author> authors;

    @Lob
    private String keywords;

    @OneToOne(fetch = FetchType.LAZY,   cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private SourceUrls sourceUrls;

    private String pmid;
    private Integer timesCited;

    @UpdateTimestamp
    private LocalDateTime lastUpdate;

//    public void setAuthors(List<String> authors) {
//        this.authors = new ArrayList<>();
//        Iterator<String> iter = authors.iterator();
//        while (iter.hasNext()) this.authors.add(Author.builder().names(iter.next()).build());
//    }

    public void setDoctype(List<String> doctype) {
        if (doctype == null) return;
        this.doctype = new ArrayList<>();
        Iterator<String> iter = doctype.iterator();
        while (iter.hasNext()) this.doctype.add(Doctype.builder().value(iter.next()).build());

    }
    public void setKeywords(List<String> keywords) {
        if (keywords == null) return;
        this.keywords = keywords.stream().reduce((re, word) -> re + "," + word).get();
    }
}
