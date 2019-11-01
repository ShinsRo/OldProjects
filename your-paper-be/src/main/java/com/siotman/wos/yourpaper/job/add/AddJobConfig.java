package com.siotman.wos.yourpaper.job.add;

import com.siotman.wos.yourpaper.domain.dto.UidDto;
import com.siotman.wos.yourpaper.domain.dto.UidsDto;
import com.siotman.wos.yourpaper.exception.NoSuchMemberException;
import com.siotman.wos.yourpaper.service.MemberPaperService;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Configuration
public class AddJobConfig {
    private Logger logger = LoggerFactory.getLogger(AddJobConfig.class);
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Map<String, Object> searchResult = new HashMap<>();

    @Autowired
    WokSearchService wokSearchService;
    @Autowired
    MemberPaperService memberPaperService;

    @Autowired
    private JobBuilderFactory    jobBuilderFactory;
    @Autowired
    private StepBuilderFactory   stepBuilderFactory;

    @Bean
    public Job addJob() {
        return this.jobBuilderFactory.get("addJob")
                .incrementer(new RunIdIncrementer())
                .start( searchStep(null, null))
                .next(  retrieveStep())
                .build();
    }


    @Bean
    @JobScope
    public Step searchStep(@Value("#{jobParameters[organization]}") String organization,
                           @Value("#{jobParameters[launchDate]}")   String launchDate) {
        LocalDate endDate, beginDate;
        endDate     = LocalDate.parse(launchDate, dtf);
        beginDate   = endDate.minusDays(6);

        return this.stepBuilderFactory.get("searchStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    logger.info("[0100] SearchStep Started");

                    logger.info("[0101] Searching...");
                    new Date();
                    searchResult = wokSearchService.search(
                            String.format("AD=(%s)", organization),
                            dtf.format(beginDate),
                            dtf.format(endDate),
                            null,
                            1, 0);

                    searchResult.put("count", 50);
                    searchResult.put("firstRecord", 1);
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    @JobScope
    public Step retrieveStep() {
        return this.stepBuilderFactory.get("retrieveStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    String sid          = (String) searchResult.get("sid");
                    String queryId      = (String) searchResult.get("queryId");
                    Integer count       = (Integer) searchResult.get("count");
                    Integer firstRecord = (Integer) searchResult.get("firstRecord");
                    Integer recordsFound = (Integer) searchResult.get("recordsFound");

                    Map retrieve = wokSearchService.retrieve(sid, queryId, firstRecord, count);
                    List<Map> records   = (List<Map>) retrieve.get("records");

                    List<UidDto> uids       = new LinkedList<>();
                    List<String> stringUids = new LinkedList<>();
                    for (Map record : records) {
                        UidDto uidDto = UidDto.builder()
                                .uid((String) record.get("uid"))
                                .isReprint(false)
                                .build();
                        uids.add(uidDto);
                        stringUids.add((String) record.get("uid"));
                    }
                    List<Map> lamrRecords = wokSearchService.getLamrData(stringUids);

                    UidsDto uidsDto = UidsDto.builder()
                            .username("admin")
                            .uids(uids)
                            .build();
                    try {
                        Boolean validity    = memberPaperService.add(uidsDto);
                    } catch (NoSuchMemberException e) {
                        e.printStackTrace();
                    }
                    if (firstRecord + count > recordsFound) return RepeatStatus.FINISHED;

                    searchResult.put("firstRecord", firstRecord + count);
                    return RepeatStatus.CONTINUABLE;
                })).build();
    }
}
