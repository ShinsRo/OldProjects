package com.midas2018.root.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midas2018.root.support.Constant;

@RestController(Constant.API_URI)
public class IndexController {

    @GetMapping(value = "/")
    public String greeting(){
        return "MIDAS CHALLENGE 2018";
    };
}
