package com.siotman.wos.service;

import com.siotman.wos.domain.dto.api.PaperApiDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaperServiceTest {
    @Autowired
    PaperService paperService;

    @Test
    public void searchPaperByTitle() {
        List<PaperApiDto> paperApiDtos = paperService.searchPaperByTitle(
                "Electro"
        );

        System.out.println(paperApiDtos);

        paperApiDtos = paperService.searchPaperByAuthorName("kim h");

        System.out.println(paperApiDtos);
    }
}
