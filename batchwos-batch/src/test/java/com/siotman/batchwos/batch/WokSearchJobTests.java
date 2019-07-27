package com.siotman.batchwos.batch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WokSearchJobTests {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void testJob() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
    }

    @Test
    public void testMashaller() {

    }

    @Test
    public void temp() throws IOException {
        File responseFile = new File(
                String.format("target/temp/lamr/lamr_res.xml"));
        System.out.println(responseFile.isFile());
        System.out.println(responseFile.canWrite());
        System.out.println(responseFile.exists());
        System.out.println(responseFile.getAbsolutePath());

        if(!responseFile.exists()) {
            responseFile.createNewFile();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(responseFile, true));
        bw.newLine();
        bw.write("TEST");
        bw.write("TEST");
    }

}
