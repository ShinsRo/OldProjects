package com.nastech.upmureport.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nastech.upmureport.domain.entity.UpmuContent;
import com.nastech.upmureport.domain.entity.Member;

import lombok.extern.java.Log;

@Controller
@Log
public class HomeController {
	
	@GetMapping(value = "/")
    public String index(HttpServletRequest req, HttpServletResponse res) {
        return "index";
    }

    @GetMapping(value = "/temp")
    public String temp() {
        return "_template";
    }
    
    @GetMapping(value= "/test")
    //@RequestMapping(value="/test")
    public String goAddUpmu() {
    	log.info("==========================/test");
    	return "addUpmu";
    }
    
//    @PostMapping(value="/upmu")
//    public String addUpmu(UpmuContents upmuContents ) {
//    	log.info("==========================/upmu");
//    	return upmuContents.getName();    	
//    }
}