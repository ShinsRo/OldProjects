package com.siotman.wos.yourpaper.domain.dto;

import com.siotman.wos.yourpaper.domain.entity.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PaperDto {
    private String uid;
    private String doi;
    private String title;
    private AuthorType authorType;
    private Integer timesCited;
    private List<String> authorList;
    private RecordType recordType;
    private RecordState recordState;
    private LocalDateTime lastUpdates;
    private SourceInfoDto sourceInfoDto;
    private ParsedDataDto parsedDataDto;
    private PaperUrlsDto paperUrlsDto;

    @Builder
    public PaperDto(
            String uid, String doi,
            String title, AuthorType authorType,
            Integer timesCited, List<String> authorList,
            RecordType recordType, RecordState recordState,
            LocalDateTime lastUpdates, SourceInfoDto sourceInfoDto,
            ParsedDataDto parsedDataDto, PaperUrlsDto paperUrlsDto
    ) {
        this.uid = uid;
        this.doi = doi;
        this.title  = title;
        this.authorType = authorType;
        this.timesCited = timesCited;
        this.authorList = authorList;
        this.recordType = recordType;
        this.recordState = recordState;
        this.lastUpdates = lastUpdates;
        this.sourceInfoDto = sourceInfoDto;
        this.parsedDataDto = parsedDataDto;
        this.paperUrlsDto = paperUrlsDto;
    }

    public static PaperDto buildWithMemberPaper(MemberPaper memberPaper) {
        Paper paper = memberPaper.getPaper();
        Integer timesCited = 0;
        if (paper.getTimesCited() != null) timesCited = Integer.valueOf(paper.getTimesCited());

        return builder()
                .uid(paper.getUid())
                .doi(paper.getDoi())
                .title(paper.getTitle())
                .authorType(memberPaper.getAuthorType())
                .timesCited(timesCited)
                .authorList(paper.getAuthorListJson())
                .recordType(paper.getRecordType())
                .recordState(paper.getRecordState())
                .lastUpdates(paper.getLastUpdates())
                .sourceInfoDto(SourceInfoDto.buildWithEntity(paper.getSourceInfo()))
                .parsedDataDto(ParsedDataDto.buildWithEntity(paper.getParsedData()))
                .paperUrlsDto(PaperUrlsDto.buildWithEntity(paper.getPaperUrls()))
                .build();
    }
}
