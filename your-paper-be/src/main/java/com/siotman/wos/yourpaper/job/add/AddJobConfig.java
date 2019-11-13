package com.siotman.wos.yourpaper.job.add;

import com.siotman.wos.jaxws2rest.domain.dto.LamrResultsDto;
import com.siotman.wos.jaxws2rest.domain.dto.LiteRecordDto;
import com.siotman.wos.jaxws2rest.domain.dto.SearchResultsDto;
import com.siotman.wos.yourpaper.domain.dto.PaperDto;
import com.siotman.wos.yourpaper.domain.dto.UidDto;
import com.siotman.wos.yourpaper.domain.entity.MemberPaper;
import com.siotman.wos.yourpaper.domain.entity.Paper;
import com.siotman.wos.yourpaper.domain.entity.RecordState;
import com.siotman.wos.yourpaper.repo.PaperRepository;
import com.siotman.wos.yourpaper.service.AsyncParsingTriggerService;
import com.siotman.wos.yourpaper.service.WokSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Configuration
public class AddJobConfig {
    private Logger logger = LoggerFactory.getLogger(AddJobConfig.class);
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private SearchResultsDto searchResult;
    private int count = 50;
    private int firstRecord;

    @Autowired
    WokSearchService wokSearchService;
    @Autowired
    AsyncParsingTriggerService parsingTriggerService;
    @Autowired
    PaperRepository paperRepository;

    @Autowired
    private JobBuilderFactory    jobBuilderFactory;
    @Autowired
    private StepBuilderFactory   stepBuilderFactory;

    @Bean
    public Job addJob() {
        return this.jobBuilderFactory.get("addJob")
                .incrementer(new RunIdIncrementer())
                .start( searchStep(null, null, null, null))
                .next(  retrieveStep())
                .build();
    }


    @Bean
    @JobScope
    public Step searchStep(@Value("#{jobParameters[organization]}") String organization,
                           @Value("#{jobParameters[begin]}")        String begin,
                           @Value("#{jobParameters[end]}")          String end,
                           @Value("#{jobParameters[symbolic]}")     String symbolic) {

        final String symbolicDate;
        if (symbolic == null || symbolic.equals(""))    symbolicDate = null;
        else                                            symbolicDate = symbolic;

        return this.stepBuilderFactory.get("searchStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    logger.info(String.format("[0100] 검색 스텝, timeSpan %s ~ %s, AD=(%s).", begin, end, organization));

                    searchResult = wokSearchService.search(
                            String.format("AD=(%s)", organization),
                            begin,
                            end,
                            symbolicDate,
                            1, 0);

                    this.firstRecord = 1;
                    logger.info(String.format("[0101] 검색결과, recordsFound : %d.", searchResult.getRecordsFound()));
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    @JobScope
    public Step retrieveStep() {
        return this.stepBuilderFactory.get("retrieveStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    String sid              = searchResult.getSid();
                    String queryId          = searchResult.getQueryId();
                    int recordsFound        = searchResult.getRecordsFound();

                    logger.info(String.format("[0201] 리트리브 스텝, firstRecord : %d", firstRecord));

                    SearchResultsDto retrieve = wokSearchService.retrieve(sid, queryId, firstRecord, count);
                    List<LiteRecordDto> records   = retrieve.getRecords();

                    List<UidDto> uids       = new LinkedList<>();
                    List<String> stringUids = new LinkedList<>();
                    for (LiteRecordDto record : records) {
                        UidDto uidDto = UidDto.builder()
                                .uid(record.getUid())
                                .isReprint(false)
                                .build();
                        uids.add(uidDto);
                        stringUids.add(record.getUid());
                    }
                    List<LamrResultsDto> lamrRecords = wokSearchService.getLamrData(stringUids);
                    List<Paper> newEntities = new ArrayList<>();
                    List<PaperDto> shouldParse = new ArrayList<>();
                    for (int i = 0; i < records.size(); i++) {
                        LiteRecordDto record        = records.get(i);
                        LamrResultsDto lamrRecord   = lamrRecords.get(i);

                        Paper paper = Paper.buildWithWokResponse(record, lamrRecord);
                        newEntities.add(paper);

                        if (paper.getTimesCited().equals("0") || paper.getTimesCited().equals("")) {
                            paper.setRecordState(RecordState.COMPLETED);
                            continue;
                        }
                        shouldParse.add(
                                PaperDto.buildWithMemberPaper(
                                        MemberPaper.builder()
                                                .paper(paper)
                                                .build()
                                )
                        );
                    }
                    paperRepository.saveAll(newEntities);
                    parsingTriggerService.triggerAll(shouldParse);
                    if (firstRecord + count > recordsFound) return RepeatStatus.FINISHED;

                    firstRecord += count;
                    return RepeatStatus.CONTINUABLE;
                })).build();
    }
}
