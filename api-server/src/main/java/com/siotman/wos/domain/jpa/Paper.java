package com.siotman.wos.domain.jpa;

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
import java.util.Map;

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

    @ElementCollection
    private Map<String, Integer> tcData;

    @OneToMany(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    private List<Author> authors;

    @ElementCollection
    private List<String> keywords;

    @OneToOne(mappedBy = "paper", fetch = FetchType.LAZY,   cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private SourceUrls sourceUrls;

    private String pmid;
    private Integer timesCited;

    @OneToOne(mappedBy = "paper", fetch = FetchType.LAZY,   cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Grades grades;

    @Enumerated(EnumType.STRING)
    private RecordState recordState;

    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    public void setDoctype(List<String> doctype) {
        if (doctype == null) return;
        this.doctype = new ArrayList<>();
        Iterator<String> iter = doctype.iterator();
        while (iter.hasNext()) this.doctype.add(Doctype.builder().value(iter.next()).build());

    }

    public String info() {
        StringBuilder sb = new StringBuilder();

        sb  .append("UT: ").append(uid)
            .append(" LU: ").append(lastUpdate)
            .append(" RS: ").append(recordState);
        return sb.toString();
    }
}
