package com.siotman.batchwos.batch.domain.jpa;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Builder
@Entity
public class Doctype {
    @Id
    @GeneratedValue
    Long id;
    String value;
}
