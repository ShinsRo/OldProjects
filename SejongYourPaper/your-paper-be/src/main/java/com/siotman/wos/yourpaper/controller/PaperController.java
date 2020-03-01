package com.siotman.wos.yourpaper.controller;

import com.siotman.wos.yourpaper.domain.dto.PaperSearchParameter;
import com.siotman.wos.yourpaper.domain.entity.Paper;
import com.siotman.wos.yourpaper.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/paper")
public class PaperController {
    private PaperService paperService;

    @PostMapping(value = "/search")
    public ResponseEntity<?> search(@RequestBody PaperSearchParameter paperSearchParameter) {
        ResponseEntity response;

        Page<Paper> pages = paperService.search(paperSearchParameter);
        response = ResponseEntity.ok(pages);
        return response;
    }

    @Autowired
    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }
}
