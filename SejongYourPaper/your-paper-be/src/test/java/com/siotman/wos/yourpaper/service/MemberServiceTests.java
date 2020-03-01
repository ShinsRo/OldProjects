package com.siotman.wos.yourpaper.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siotman.wos.yourpaper.domain.dto.MemberDto;
import com.siotman.wos.yourpaper.exception.MemberIsAlreadyPresentException;
import com.siotman.wos.yourpaper.exception.NoSuchMemberException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTests {
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder encoder;

    @Test
    public void 등록한_유저의_비밀번호는_암호화되어야한다() throws IOException, MemberIsAlreadyPresentException {
        final MemberDto targetDto = objectMapper.readValue("{" +
                "\"username\":\"user01\"," +
                "\"password\":\"password01!\"," +
                "\"memberInfoDto\":{" +
                        "\"name\":\"김승신\"," +
                        "\"authorNameList\":[\"KSS\",\"Seungshin kim\"]," +
                        "\"organizationList\":[\"Sejong Univ\", \"SK C&C\"]" +
                    "}" +
                "}", MemberDto.class);
        MemberDto member = memberService.register(targetDto);

        Assert.isTrue(encoder.matches(targetDto.getPassword(), member.getPassword()),
                "등록한 유저의 비밀번호가 암호화한 비밀번호와 일치하지 않습니다.");
    }

    @Test
    public void 유저는_비밀번호를_변경할_수_있다() throws IOException, NoSuchMemberException, MemberIsAlreadyPresentException {
        final MemberDto targetDto = objectMapper.readValue("{" +
                "\"username\":\"user01\"," +
                "\"password\":\"password01!\"," +
                "\"memberInfoDto\":{" +
                        "\"name\":\"김승신\"," +
                        "\"authorNameList\":[\"KSS\",\"Seungshin kim\"]," +
                        "\"organizationList\":[\"Sejong Univ\", \"SK C&C\"]" +
                    "}" +
                "}", MemberDto.class);
        final MemberDto updateDto = objectMapper.readValue("{" +
                "\"username\":\"user01\"," +
                "\"password\":\"password02!!\"," +
                "\"memberInfoDto\":{" +
                        "\"name\":\"김승신\"," +
                        "\"authorNameList\":[\"KSS\",\"Seungshin kim\"]," +
                        "\"organizationList\":[\"Sejong Univ\", \"SK C&C\"]" +
                    "}" +
                "}", MemberDto.class);

        memberService.register(targetDto);
        memberService.update(updateDto);

        MemberDto foundMember = memberService.findById(updateDto.getUsername());
        Assert.isTrue(encoder.matches(updateDto.getPassword(), foundMember.getPassword()),
                "업데이트한 유저의 패스워드가 변경할 패스워드와 일치하지 않습니다.");
    }

    @Test
    public void 회원가입_시_아이디_중복확인이_가능하다() throws IOException, MemberIsAlreadyPresentException {
        final MemberDto savedMemberDto = objectMapper.readValue("{" +
                "\"username\":\"user01\"," +
                "\"password\":\"password01!\"," +
                "\"memberInfoDto\":{" +
                        "\"name\":\"김승신\"," +
                        "\"authorNameList\":[\"KSS\",\"Seungshin kim\"]," +
                        "\"organizationList\":[\"Sejong Univ\", \"SK C&C\"]" +
                    "}" +
                "}", MemberDto.class);
        memberService.register(savedMemberDto);

        final MemberDto targetDto01 = objectMapper.readValue("{" +
                "\"username\":\"user01\"" +
                "}", MemberDto.class);

        Assert.isTrue(memberService.availableCheck(targetDto01.getUsername()),
                "존재하는 유저아이디에 대해 중복확인이 거짓을 반환하지 않았습니다.");

        final MemberDto targetDto02 = objectMapper.readValue("{" +
                "\"username\":\"user02\"" +
                "}", MemberDto.class);

        Assert.isTrue(!memberService.availableCheck(targetDto02.getUsername()),
                "존재하지 않는 유저아이디에 대해 중복확인이 참을 반환하지 않았습니다.");
    }
}
