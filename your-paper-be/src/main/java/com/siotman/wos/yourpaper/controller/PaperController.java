package com.siotman.wos.yourpaper.controller;

import com.siotman.wos.yourpaper.domain.dto.MemberDto;
import com.siotman.wos.yourpaper.domain.dto.PaperDto;
import com.siotman.wos.yourpaper.domain.dto.UidsDto;
import com.siotman.wos.yourpaper.exception.NoSuchMemberException;
import com.siotman.wos.yourpaper.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/myPaper")
public class PaperController {
    @Autowired
    PaperService paperService;

    @PostMapping(value = "/list")
    public ResponseEntity<?> list(@RequestBody MemberDto dto) {
        List<PaperDto> list = paperService.list(dto);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> add(@RequestBody UidsDto dto) throws NoSuchMemberException {
        Boolean add = paperService.add(dto);
        return ResponseEntity.ok().body(add);

    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> delete(@RequestBody UidsDto dto) throws NoSuchMemberException {
        Boolean delete = paperService.delete(dto);
        return ResponseEntity.ok().body(delete);

    }

}
