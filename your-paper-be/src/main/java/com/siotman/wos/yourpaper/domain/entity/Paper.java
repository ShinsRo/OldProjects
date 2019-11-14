package com.siotman.wos.yourpaper.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.siotman.wos.jaxws2rest.domain.dto.LamrResultsDto;
import com.siotman.wos.jaxws2rest.domain.dto.LiteRecordDto;
import com.siotman.wos.yourpaper.domain.converter.JsonListConverter;
import com.siotman.wos.yourpaper.domain.dto.ParsedDataDto;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "paper")
@ToString(exclude = {"users"})
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"uid"})
public class Paper {
    @Id
    @Column(length = 128)
    private String uid;

    @Column(length = 128)
    private String doi;

    @Lob
    private String title;

    private String timesCited;

    @OneToMany(mappedBy = "paper", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<MemberPaper> users;

    @Lob
    @Convert(converter = JsonListConverter.class)
    private List<String> authorListJson;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private RecordType recordType;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private RecordState recordState;

    @UpdateTimestamp
    private LocalDateTime lastUpdates;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "src_info_id", referencedColumnName = "id")
    private SourceInfo sourceInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parsed_data_id", referencedColumnName = "id")
    private ParsedData parsedData;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paper_urls_id", referencedColumnName = "id")
    private PaperUrls paperUrls;

    @Builder
    public Paper(
            String uid, String doi,
            String title, String timesCited,
            List<String> authorListJson,
            RecordType recordType,
            RecordState recordState,
            SourceInfo sourceInfo,
            ParsedData parsedData,
            PaperUrls paperUrls
    ) {
        this.uid    = uid;
        this.doi    = doi;
        this.title  = title;
        this.timesCited     = timesCited;
        this.authorListJson = authorListJson;
        this.recordType     = recordType;
        this.recordState    = recordState;
        this.sourceInfo     = sourceInfo;
        this.parsedData     = parsedData;
        this.paperUrls      = paperUrls;
    }

    public static Paper buildWithWokResponse(LiteRecordDto liteRecordDto, LamrResultsDto lamrResultsDto) {
        SourceInfo sourceInfo   = SourceInfo.buildWithCacheData(liteRecordDto);
        PaperUrls paperUrls     = PaperUrls.buildWithCacheData(lamrResultsDto);

        String timesCited       = "0";
        if (lamrResultsDto != null && lamrResultsDto.getTimesCited() != null)
            timesCited = lamrResultsDto.getTimesCited();

        return Paper.builder()
                .uid(liteRecordDto.getUid())
                .doi(liteRecordDto.getDoi())
                .title(liteRecordDto.getTitle())
                .timesCited(timesCited)
                .authorListJson(liteRecordDto.getAuthors())
                .recordType(RecordType.WOS)
                .recordState(RecordState.SEARCHED)
                .sourceInfo(sourceInfo)
                .parsedData(null)
                .paperUrls(paperUrls)
                .build();
    }

    // DTO 개발 완료 시 변경 필요.
    public void updatePaperUrls(PaperUrls paperUrls) {
        this.paperUrls = paperUrls;
    }

    public void updateParsedData(ParsedData parsedData) {
        this.parsedData = parsedData;
    }

    public void updatePaperData(ParsedDataDto parsedDataDto) {
        if (this.parsedData == null) this.parsedData = ParsedData.builder().build();

        ParsedData parsedData = ParsedData.builder()
                .paper(this)
                .timesCited(            parsedDataDto.getTimesCited())
                .reprint(               parsedDataDto.getReprint())
                .grades(                parsedDataDto.getGrades())
                .tcDataJson(            this.parsedData.getTcDataJson())
                .parsedAuthorJsonList(  parsedDataDto.getParsedAuthorList())
                .journalImpactJson(     parsedDataDto.getJournalImpact())
                .citingPaperJsonList(   this.parsedData.getCitingPaperJsonList())
                .build();

        this.parsedData     = parsedData;    }

    public void updateTcData(ParsedDataDto parsedDataDto) {
        if (this.parsedData == null) this.parsedData = ParsedData.builder().build();

        ParsedData parsedData = ParsedData.builder()
                .paper(this)
                .timesCited(            this.parsedData.getTimesCited())
                .reprint(               this.parsedData.getReprint())
                .grades(                this.parsedData.getGrades())
                .tcDataJson(            parsedDataDto.getTcData())
                .parsedAuthorJsonList(  this.parsedData.getParsedAuthorJsonList())
                .journalImpactJson(     this.parsedData.getJournalImpactJson())
                .citingPaperJsonList(   parsedDataDto.getCitingPaperJsonList())
                .build();

        this.parsedData = parsedData;
    }

    public void setRecordState(RecordState state) {
        this.recordState = state;
    }

    public void setTimesCited(String timesCited) {
        this.timesCited = timesCited;
    }
}
