package com.siotman.wos.yourpaper.controller;
import com.siotman.wos.yourpaper.domain.dto.MemberDto;
import com.siotman.wos.yourpaper.domain.entity.MemberPaper;
import com.siotman.wos.yourpaper.exception.MemberIsAlreadyPresentException;
import com.siotman.wos.yourpaper.exception.NoSuchMemberException;
import com.siotman.wos.yourpaper.service.MemberPaperService;
import com.siotman.wos.yourpaper.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin
public class SessionController {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberPaperService memberPaperService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody MemberDto dto) throws NoSuchMemberException {
        MemberDto logined = memberService.findById(dto.getUsername());
        return ResponseEntity.ok().body(logined);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestBody MemberDto dto) {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody MemberDto dto) throws MemberIsAlreadyPresentException {
        ResponseEntity<?> response;

        MemberDto savedMemberDto = memberService.register(dto);

        response = ResponseEntity.ok().body(savedMemberDto);
        return response;
    }

    @PostMapping(value = "/autoSearchAndAdd")
    public ResponseEntity<?> autoSearchAndAdd(MemberDto dto) throws IOException {
        ResponseEntity<?> response;

        List<MemberPaper> memberPapers = memberPaperService.searchAndAddByMember(dto);

        response = ResponseEntity.ok(memberPapers);
        return response;
    }

    @PostMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody MemberDto dto) throws NoSuchMemberException {
        ResponseEntity<?> response;

        MemberDto savedMemberDto = memberService.update(dto);

        response = ResponseEntity.ok().body(savedMemberDto);
        return response;
    }

    @PostMapping(value = "availableCheck")
    public ResponseEntity<?> availableCheck(@RequestBody MemberDto dto) {
        ResponseEntity<?> response;

        Boolean result = memberService.availableCheck(dto.getUsername());
        response = ResponseEntity.ok().body(result);
        return response;
    }
}
