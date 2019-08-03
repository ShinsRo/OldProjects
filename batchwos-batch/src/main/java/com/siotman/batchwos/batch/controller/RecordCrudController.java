package com.siotman.batchwos.batch.controller;

import com.siotman.batchwos.batch.domain.dto.PaperDataDto;
import com.siotman.batchwos.batch.domain.jpa.Paper;
import com.siotman.batchwos.batch.repo.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RecordCrudController {
    @Autowired PaperRepository paperRepository;

    @GetMapping("/")
    public String test(String test) {
        System.out.println(test);
        return "OK";
    }

    @PostMapping(value = "/save")
    public void save(@RequestBody PaperDataDto paperDataDto) {
        System.out.println(paperDataDto);
        System.out.println(paperDataDto.getAuthors());

//        Paper target = paperRepository.getOne(uid);
    }
}
