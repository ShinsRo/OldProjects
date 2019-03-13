package com.nastech.upmureport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nastech.upmureport.domain.dto.UpmuReqDto;
import com.nastech.upmureport.domain.entity.UpmuContents;
import com.nastech.upmureport.service.UpmuService;

import lombok.extern.java.Log;

@Controller
@Log
public class UpmuController {
	
	UpmuService upmuService;
	
	public UpmuController(UpmuService upmuService) {
		// TODO Auto-generated constructor stub
		this.upmuService = upmuService;
	}
	
	@GetMapping(value= "/test")
    //@RequestMapping(value="/test")
    public String goAddUpmu() {
    	log.info("==========================/test");
    	return "addUpmu";
    }
	
	@PostMapping(value= "/upmu")
	@ResponseBody
	public String addUpmu(UpmuReqDto upmuReqDto) {
		log.info("==========================/upmu");
		
		UpmuContents upmuContents = upmuService.addUpmuContents(upmuReqDto);
		
		if(upmuContents == null) {
			return "error";
		} else {
			return upmuContents.getName();
		}		
	}
	
	
}
