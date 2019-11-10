package com.siotman.wos.yourpaper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siotman.wos.yourpaper.domain.dto.MemberDto;
import com.siotman.wos.yourpaper.exception.MemberIsAlreadyPresentException;
import com.siotman.wos.yourpaper.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;


@CrossOrigin
@RestController
@RequestMapping(value = "/batch")
public class JobTriggerController {
    private Logger logger = LoggerFactory.getLogger(JobTriggerController.class);

    private final Integer CHECK_INTERVAL = 3000;
    private AtomicBoolean addJobEnabled     = new AtomicBoolean(true);
    private AtomicBoolean updateJobEnabled  = new AtomicBoolean(true);

    private MemberService memberService;
    private JobLauncher jobLauncher;
    private Job addJob;
    private Job updateJob;

    @Autowired
    public JobTriggerController(MemberService memberService, JobLauncher jobLauncher, Job addJob, Job updateJob) {
        this.memberService  = memberService;
        this.jobLauncher    = jobLauncher;
        this.addJob         = addJob;
        this.updateJob      = updateJob;
    }

    @GetMapping("/launchAddJob")
    public void launchAddJob(@RequestParam String begin, @RequestParam String end,
                             @RequestParam String organization, @RequestParam String symbolic) throws IOException, MemberIsAlreadyPresentException, JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, InterruptedException {
        setDefaultUserIfAbsent();

        if (addJobEnabled.get()) {
            logger.info("Launching addJob.");

            JobExecution jobExecution = jobLauncher
                    .run(addJob, new JobParametersBuilder()
                            .addString("organization", organization)
                            .addString("begin", begin)
                            .addString("end", end)
                            .addString("symbolic", symbolic)
                            .toJobParameters());
            addJobEnabled.set(false);
            do {
                Thread.sleep(CHECK_INTERVAL);
                logger.info("AddJob is still running.");
            } while (jobExecution.isRunning());
        }
        logger.info("AddJob done.");
        addJobEnabled.set(true);
    }

    @GetMapping("/launchUpdateJob")
    public void launchUpdateJob(@RequestParam String date) throws IOException, MemberIsAlreadyPresentException, JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, InterruptedException {
        setDefaultUserIfAbsent();

        if(updateJobEnabled.get()) {
            logger.info("Launching updateJob.");

            JobExecution jobExecution = jobLauncher
                    .run(updateJob, new JobParametersBuilder()
                            .addString("launchDate", date)
                            .toJobParameters());
            updateJobEnabled.set(false);
            do {
                Thread.sleep(CHECK_INTERVAL);
                logger.info("updateJob is still running.");
            } while (jobExecution.isRunning());
        }
        logger.info("updateJob done.");
        updateJobEnabled.set(true);
    }

    private void setDefaultUserIfAbsent() throws MemberIsAlreadyPresentException, IOException {
        if (!memberService.availableCheck("admin")) return;

        final MemberDto targetDto = new ObjectMapper().readValue("{" +
                "\"username\":\"admin\"," +
                "\"password\":\"admin\"," +
                "\"memberInfoDto\":{" +
                "\"name\":\"김승신\"," +
                "\"authorNameList\":[\"\"]," +
                "\"organizationList\":[\"\"]" +
                "}" +
                "}", MemberDto.class);

        memberService.register(targetDto);
    }
}
