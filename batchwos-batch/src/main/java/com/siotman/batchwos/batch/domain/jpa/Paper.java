package com.siotman.batchwos.batch.domain.jpa;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Builder
@Entity
public class Paper {
    @Id
    String uid;
    String title;

    @ManyToMany(fetch = FetchType.LAZY)
    List<Doctype> doctype;
    String volume;
    String issue;
    String pages;
    String publishedDate;
    String publishedYear;
    String sourceTitle;

    String doi;
    String ids;
    String eissn;
    String issn;

    @ManyToMany(fetch = FetchType.LAZY)
    List<Author> authors;

    String keywords;

    public void setAuthors(List<String> authors) {
        this.authors = new ArrayList<>();
        Iterator<String> iter = authors.iterator();
        while (iter.hasNext()) this.authors.add(Author.builder().name(iter.next()).build());
    }
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
