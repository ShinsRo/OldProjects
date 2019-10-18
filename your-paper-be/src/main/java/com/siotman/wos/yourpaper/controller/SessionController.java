package com.siotman.wos.yourpaper.controller;
import com.siotman.wos.yourpaper.domain.dto.MemberDto;
import com.siotman.wos.yourpaper.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class SessionController {
    @Autowired
    MemberService memberService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestBody MemberDto dto) {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody MemberDto dto) {
        ResponseEntity<?> response;

        MemberDto savedMemberDto = memberService.register(dto);

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
