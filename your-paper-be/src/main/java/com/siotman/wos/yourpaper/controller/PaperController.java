package com.siotman.wos.yourpaper.controller;

import com.siotman.wos.yourpaper.domain.dto.UidsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/myPaper")
public class PaperController {

    @PostMapping(value = "/add")
    public ResponseEntity<?> add(@RequestBody String username, @RequestBody UidsDto dto) {
        return ResponseEntity.ok().build();

    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> delete(@RequestBody UidsDto dto) {

        return ResponseEntity.ok().build();

    }

}
