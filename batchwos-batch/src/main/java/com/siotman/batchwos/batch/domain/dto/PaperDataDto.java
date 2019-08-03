package com.siotman.batchwos.batch.domain.dto;

import com.siotman.batchwos.batch.domain.jpa.RecordState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaperDataDto {
    private String uid;
    private Integer timesCited;
    private List<AuthorDto> authors;
    private AuthorDto firstAuthor;
    private AuthorDto reprint;
    private Map<String, ?> jcr;
    private List<GradeDto> grades;

    private String recordState;
}