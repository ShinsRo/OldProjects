package com.siotman.wos.yourpaper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siotman.wos.yourpaper.domain.dto.MemberDto;
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
    private AtomicBoolean addJobEnabled = new AtomicBoolean(true);

    @Autowired
    private MemberService memberService;

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job addJob;

    @GetMapping("/launchAddJob")
    public void launchAddJob(@RequestParam String date, @RequestParam String organization) throws Exception{
        if (addJobEnabled.get()) {
            logger.info("Launching addJob.");

            JobExecution jobExecution = jobLauncher
                    .run(addJob, new JobParametersBuilder()
                            .addString("organization", organization)
                            .addString("launchDate", date)
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
}
