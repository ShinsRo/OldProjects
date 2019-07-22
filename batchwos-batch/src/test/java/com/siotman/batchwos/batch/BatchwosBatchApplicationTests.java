package com.siotman.batchwos.batch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BatchwosBatchApplicationTests {

    @Autowired
    ResourceLoader resourceLoader;

    @Test
    public void contextLoads() {
        Resource resource = resourceLoader.getResource("file:./target/temp/fetched_1.xml");
        System.out.println(resource.isReadable());
        try {
            BufferedReader br = new BufferedReader(new FileReader(resource.getFile()));


            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
