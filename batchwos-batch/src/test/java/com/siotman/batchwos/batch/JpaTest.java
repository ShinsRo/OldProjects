package com.siotman.batchwos.batch;

import com.siotman.batchwos.batch.domain.jpa.Paper;
import com.siotman.batchwos.batch.repo.PaperRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JpaTest {
    @Autowired
    PaperRepository paperRepository;

    @Test
    public void saveTest() {
        Paper p = paperRepository.findById("WOS:000475354700109").get();

        System.out.println(p);
    }
}
