package com.siotman.wos.yourpaper.controller;

import com.siotman.wos.yourpaper.domain.dto.MemberDto;
import com.siotman.wos.yourpaper.domain.dto.PaperDto;
import com.siotman.wos.yourpaper.domain.dto.UidsDto;
import com.siotman.wos.yourpaper.exception.NoSuchMemberException;
import com.siotman.wos.yourpaper.service.MemberPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/myPaper")
public class MemberPaperController {
    @Autowired
    MemberPaperService memberPaperService;

    @PostMapping(value = "/list")
    public ResponseEntity<?> list(@RequestBody MemberDto dto) {
        List<PaperDto> list = memberPaperService.list(dto);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> add(@RequestBody UidsDto dto) throws NoSuchMemberException {
        Boolean add = memberPaperService.add(dto);
        return ResponseEntity.ok().body(add);

    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> delete(@RequestBody UidsDto dto) throws NoSuchMemberException {
        Boolean delete = memberPaperService.delete(dto);
        return ResponseEntity.ok().body(delete);

    }

}
