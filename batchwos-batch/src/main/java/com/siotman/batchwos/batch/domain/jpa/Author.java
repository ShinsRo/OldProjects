package com.siotman.batchwos.batch.domain.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String fullName;
    private String address;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Paper> paperList;
}
