package com.nastech.upmureport.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.domain.dto.UpmuReqDto;
import com.nastech.upmureport.domain.entity.Dir;
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
	public UpmuContent addUpmu(@RequestBody UpmuReqDto upmuReqDto) {
		log.info("==========================/upmu");
		log.info(upmuReqDto.toString());
		
		UpmuContent upmuContents = upmuService.addUpmuContents(upmuReqDto);
		
		
		return upmuContents;	
	}
	
	@GetMapping(value="/upmu/{dirId}")
	public List<UpmuReqDto> getUpmu(@PathVariable String dirId){
		log.info("=========get=============/upmu");
		
		return upmuService.getUpmu(Integer.valueOf(dirId));		
	}
	
//	@GetMapping(value="/upmu")
//	public List<>
		
}
