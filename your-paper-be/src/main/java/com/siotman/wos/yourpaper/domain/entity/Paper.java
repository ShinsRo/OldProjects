package com.siotman.wos.yourpaper.domain.entity;

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

    private Integer joinCount;

    @Column(length = 128)
    private String doi;

    private String title;

    private String timesCited;

    @OneToMany(mappedBy = "paper", fetch = FetchType.LAZY)
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

    public static Paper buildWithCacheData(LiteRecordDto liteRecordDto, LamrResultsDto lamrResultsDto) {
        SourceInfo sourceInfo   = SourceInfo.buildWithCacheData(liteRecordDto);
        PaperUrls paperUrls     = PaperUrls.buildWithCacheData(lamrResultsDto);

        return Paper.builder()
                .uid(liteRecordDto.getUid())
                .doi(liteRecordDto.getDoi())
                .title(liteRecordDto.getTitle())
                .timesCited((lamrResultsDto.getTimesCited() != null)? lamrResultsDto.getTimesCited(): "0")
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
        this.parsedData = ParsedData.builder()
                .paper(this)
                .timesCited     (parsedDataDto.getTimesCited())
                .reprint        (parsedDataDto.getReprint())
                .grades         (parsedDataDto.getGrades())
                .tcDataJson             ((this.parsedData != null)? this.parsedData.getTcDataJson(): null)
                .parsedAuthorJsonList   (parsedDataDto.getParsedAuthorList())
                .journalImpactJson      (parsedDataDto.getJournalImpact())
                .citingPaperJsonList    ((this.parsedData != null)? this.parsedData.getCitingPaperJsonList(): null)
                .build();
    }

    public void updateTcData(ParsedDataDto parsedDataDto) {
        this.parsedData = ParsedData.builder()
                .paper(this)
                .timesCited     ((this.parsedData != null)? this.parsedData.getTimesCited(): null)
                .reprint        ((this.parsedData != null)? this.parsedData.getReprint(): null)
                .grades         ((this.parsedData != null)? this.parsedData.getGrades(): null)
                .tcDataJson     (parsedDataDto.getTcData())
                .parsedAuthorJsonList   ((this.parsedData != null)? this.parsedData.getParsedAuthorJsonList(): null)
                .journalImpactJson      ((this.parsedData != null)? this.parsedData.getJournalImpactJson(): null)
                .citingPaperJsonList    (parsedDataDto.getCitingPaperJsonList())
                .build();
    }

    public void setRecordState(RecordState state) {
        this.recordState = state;
    }

    public void setTimesCited(String timesCited) {
        this.timesCited = timesCited;
    }
}
