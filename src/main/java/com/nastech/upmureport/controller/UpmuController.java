package com.nastech.upmureport.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.domain.dto.UpmuReqDto;
import com.nastech.upmureport.domain.entity.UpmuContent;
import com.nastech.upmureport.service.UpmuService;

import lombok.extern.java.Log;

@RestController
@Log
public class UpmuController {
	
	UpmuService upmuService;
	
	public UpmuController(UpmuService upmuService) {
		// TODO Auto-generated constructor stub
		this.upmuService = upmuService;
	}
	/*
	@GetMapping(value= "/test")
    //@RequestMapping(value="/test")
    public String goAddUpmu() {
    	log.info("==========================/test");
    	return "addUpmu";
    }
	*/
	
	@PostMapping(value= "/upmu")
	public String addUpmu(@RequestBody UpmuReqDto upmuReqDto) {
		log.info("==========================/upmu");
		log.info(upmuReqDto.toString());
		
		UpmuContent upmuContents = upmuService.addUpmuContents(upmuReqDto);
		
		if(upmuContents == null) {
			return "error";
		} else {
			return upmuContents.getName();
		}		
	}
	
//	@GetMapping(value="/upmu")
//	public List<>
	
	
	
		
}
