package com.siotman.wos.domain.dto;

import com.siotman.wos.domain.jpa.Author;
import com.siotman.wos.domain.jpa.Grades;
import com.siotman.wos.domain.jpa.Paper;
import com.siotman.wos.domain.jpa.RecordState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaperDataDto {
    private String uid;

    @Builder.Default
    private Integer timesCited = 0;

    @Builder.Default
    private List<AuthorDto> authors = new ArrayList<>();
    private AuthorDto firstAuthor;
    private AuthorDto reprint;
    private Map<String, ?> jcr;
    @Builder.Default
    private List<GradeDto> grades = new ArrayList<>();

    private String recordState;

    public void updateEntityDetail(Paper old) {
        List<Author> authorEntities = new ArrayList<>();

        for (AuthorDto aDto : authors) {
            authorEntities.add(aDto.toEntity(old.getUid()));
        }

        List<String> fullGrades = new ArrayList<>();
        List<String> caped      = new ArrayList<>();
        for (GradeDto gDto : grades) {
            fullGrades.add(gDto.getFullGrade());
            caped.add(gDto.getCaped());
        }

        Grades gradesEntity = Grades.builder()
                .uid(uid)
                .grades(fullGrades)
                .caped(caped)
                .build();

        old.setGrades(gradesEntity);
        old.setAuthors(authorEntities);
        old.setRecordState(RecordState.valueOf(recordState));
    }
}