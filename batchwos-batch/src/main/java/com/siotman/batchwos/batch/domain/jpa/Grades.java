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
public class Grades {
    @Id
    @GeneratedValue
    private String gid;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Paper paper;

    @Builder.Default
    @ElementCollection
    List<String> grades = new ArrayList<>();
    @Builder.Default
    @ElementCollection
    List<String> caped = new ArrayList<>();
}
