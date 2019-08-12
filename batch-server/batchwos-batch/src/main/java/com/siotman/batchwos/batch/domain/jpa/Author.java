package com.siotman.batchwos.batch.domain.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue
    private Long aid;

    private String name;
    private String fullName;

    private Boolean reprint;

    @Builder.Default
    @ElementCollection
    private List<String> addresses = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Paper paper;
}
