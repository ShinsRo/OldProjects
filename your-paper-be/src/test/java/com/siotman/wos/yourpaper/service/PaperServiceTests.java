package com.siotman.wos.yourpaper.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siotman.wos.yourpaper.domain.dto.*;
import com.siotman.wos.yourpaper.domain.entity.MemberPaper;
import com.siotman.wos.yourpaper.domain.entity.Paper;
import com.siotman.wos.yourpaper.exception.MemberIsAlreadyPresentException;
import com.siotman.wos.yourpaper.exception.NoSuchMemberException;
import com.siotman.wos.yourpaper.repo.PaperRepository;
import com.siotman.wos.yourpaper.util.SearchTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PaperServiceTests {
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    PaperService paperService;
    @Autowired
    PaperRepository paperRepository;

    @Test
    public void 필드이름으로_저정된_논문을_조회할_수_있다() throws IOException {
        PaperSearchParameter paperSearchParameter = objectMapper.readValue(
                "{" +
                            "\"fieldName\":\"authorListJson\"," +
                            "\"value\":\"kim\"," +
                            "\"isAsc\":\"false\"," +
                            "\"sortBy\":\"timesCited\"," +
                            "\"firstRecord\":\"0\"," +
                            "\"count\":\"20\"" +
                        "}", PaperSearchParameter.class);
        Page<Paper> results = paperService.search(paperSearchParameter);

        System.out.println(results);
    }
}
