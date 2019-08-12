package com.siotman.batchwos.batch.domain.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Default;

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
    @Builder.Default
    private String uid = "";

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
