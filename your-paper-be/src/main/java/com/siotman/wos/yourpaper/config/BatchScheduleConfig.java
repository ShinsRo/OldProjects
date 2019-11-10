package com.siotman.wos.yourpaper.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
@EnableScheduling
public class BatchScheduleConfig {
    private final Integer CHECK_INTERVAL = 3000;

    private Logger logger = LoggerFactory.getLogger(BatchScheduleConfig.class);

    private AtomicBoolean addJobEnabled     = new AtomicBoolean(true);
    private AtomicBoolean updateJobEnabled  = new AtomicBoolean(true);

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job addJob;
    @Autowired
    private Job updateJob;

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

        scheduler.setPoolSize(2);
        scheduler.setThreadNamePrefix("scheduled-task-");
        scheduler.setDaemon(true);

        return scheduler;
    }

    @Scheduled(cron = "0 30 0 ? * SUN")
    public void launchAddJob() throws Exception {
        if (addJobEnabled.get()) {
            logger.info("Launching addJob.");
            Date date = new Date();

            JobExecution jobExecution = jobLauncher
                    .run(addJob, new JobParametersBuilder()
                            .addString("organization", "Sejong Univ")
                            .addDate("launchDate", date)
                            .addString("symbolic", "1week")
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

    @Scheduled(cron = "0 0 1 ? * SUN")
    public void launchUpdateJob() throws Exception {
        if (updateJobEnabled.get()) {
            Date date = new Date();
            JobExecution jobExecution = jobLauncher
                    .run(updateJob, new JobParametersBuilder()
                            .addDate("launchDate", date)
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
}
