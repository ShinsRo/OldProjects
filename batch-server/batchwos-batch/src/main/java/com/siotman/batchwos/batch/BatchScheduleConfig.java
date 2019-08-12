package com.siotman.batchwos.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
@EnableScheduling
public class BatchScheduleConfig {
    private Logger logger = LoggerFactory.getLogger(BatchScheduleConfig.class);

    private AtomicBoolean addJobEnabled = new AtomicBoolean(true);
    private AtomicBoolean updateJobEnabled  = new AtomicBoolean(true);

    @Autowired JobLauncher  jobLauncher;
    @Autowired Job          addJob;
    @Autowired Job          updateJob;

    @Scheduled(cron = "0 30 0 ? * SUN")
    public void launchAddJob() throws Exception {
        if (addJobEnabled.get()) {
            logger.info("Launching addJob.");
            Date date = new Date();

            JobExecution jobExecution = jobLauncher
                    .run(updateJob, new JobParametersBuilder()
                            .addDate("launchDate", date)
                            .toJobParameters());
            addJobEnabled.set(false);
            do {
                Thread.sleep(1000 * 3600);
                logger.info("AddJob is still running.");
            } while (jobExecution.isRunning());
        }
        addJobEnabled.set(true);
    }

    @Scheduled(cron = "0 0 1 ? * SUN")
    public void launchUpdateJob() throws Exception {
        if (updateJobEnabled.get()) {
            Date date = new Date();
            JobExecution jobExecution = jobLauncher
                    .run(addJob, new JobParametersBuilder()
                            .addDate("launchDate", date)
                            .toJobParameters());
            updateJobEnabled.set(false);
            do {
                Thread.sleep(1000 * 3600);
                logger.info("updateJob is still running.");
            } while (jobExecution.isRunning());
        }
        updateJobEnabled.set(true);
    }
}
