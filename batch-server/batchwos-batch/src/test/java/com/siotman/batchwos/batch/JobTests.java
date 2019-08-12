package com.siotman.batchwos.batch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JobTests {

    @Autowired private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void testJob() throws Exception {
        jobLauncherTestUtils.launchJob();
    }
}
