package com.siotman.batchwos.batch.domain.jpa;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Builder
@Entity
public class Author {
    @Id
    String name;
    String fullName;
    String address;

    @ManyToMany(fetch = FetchType.LAZY)
    List<Paper> paperList;
}
