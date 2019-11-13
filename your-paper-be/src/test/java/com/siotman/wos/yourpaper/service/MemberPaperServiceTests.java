package com.siotman.wos.yourpaper.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siotman.wos.yourpaper.domain.dto.MemberDto;
import com.siotman.wos.yourpaper.domain.dto.PaperDto;
import com.siotman.wos.yourpaper.domain.dto.UidDto;
import com.siotman.wos.yourpaper.domain.dto.UidsDto;
import com.siotman.wos.yourpaper.domain.entity.MemberPaper;
import com.siotman.wos.yourpaper.exception.MemberIsAlreadyPresentException;
import com.siotman.wos.yourpaper.exception.NoSuchMemberException;
import com.siotman.wos.yourpaper.util.SearchTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
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
public class MemberPaperServiceTests {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MemberPaperService memberPaperService;
    @Autowired
    private MemberService memberService;

    private MemberDto targetDto;
    @Before
    public void init() throws IOException {
        targetDto = objectMapper.readValue("{" +
                "\"username\":\"test01\"," +
                "\"password\":\"test01\"," +
                "\"memberInfoDto\":{" +
                        "\"name\":\"신동\"," +
                        "\"authorNameList\":[\"shin\",\"kim\"]," +
                        "\"organizationList\":[\"Sejong Univ\"]" +
                    "}" +
                "}", MemberDto.class);
        try {
            memberService.register(targetDto);
        } catch (MemberIsAlreadyPresentException e) { /* 이미 존재하는 경우 */ }
    }

    @Test
    @Order(1)
    public void 내_논문목록에_새로운_논문을_추가할_수_있다() throws IOException, NoSuchMemberException {
        Map responseBodyMap = SearchTestUtil.search(
                "AU=(KIM)",
                "2019-03-02", "2019-05-01",
                1, 50);

        String sid          = (String) responseBodyMap.get("sid");
        String queryId      = (String) responseBodyMap.get("queryId");
        List<Map> records   = (List<Map>) responseBodyMap.get("records");


        List<UidDto> uids       = new LinkedList<>();
        List<String> stringUids = new LinkedList<>();
        for (Map record : records) {
            UidDto uidDto = UidDto.builder()
                    .uid((String) record.get("uid"))
                    .isReprint(new Random().nextBoolean())
                    .build();
            uids.add(uidDto);
            stringUids.add((String) record.get("uid"));
        }
        List<Map> lamrRecords = SearchTestUtil.getLamrData(stringUids);

        UidsDto uidsDto = UidsDto.builder()
                .username("test01")
                .uids(uids)
                .build();
        memberPaperService.add(uidsDto);
        List<PaperDto> list = memberPaperService.list(targetDto);

        Assert.isTrue(list.size() == uids.size(),
                "내 논문 리스트 사이즈와 추가한 논문 수가 일치하지 않습니다.");
    }

    @Test
    public void 유저_정보를_이용해_자동으로_검색하여_추가할_수_있다() throws NoSuchMemberException, IOException {
        MemberDto member = memberService.findById("test01");
        List<MemberPaper> memberPapers = memberPaperService.searchAndAddByMember(member);

        Assert.isTrue(memberPapers.size() == 50);
    }
}
