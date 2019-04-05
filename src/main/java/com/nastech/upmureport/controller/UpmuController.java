package com.nastech.upmureport.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.domain.dto.PfileReqDto;
import com.nastech.upmureport.domain.dto.PfileResDto;
import com.nastech.upmureport.service.PfileService;

import lombok.extern.java.Log;

@RestController
@Log
public class UpmuController {
	
	PfileService upmuService;
	
	public UpmuController(PfileService upmuService) {
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
	public PfileResDto addUpmu(@RequestBody PfileReqDto pfileReqDto) {
		log.info("==========================/upmu");
		log.info(pfileReqDto.toString());
		
		PfileResDto upmuResDto = upmuService.addUpmuContents(pfileReqDto);
				
		return upmuResDto;
	}
	
	@GetMapping(value="/upmu/{dirId}")
	public List<PfileResDto> getUpmu(@PathVariable long dirId){
		log.info("=========get=============/upmu");
		log.info("dir id = " + dirId);
		return upmuService.getUpmu(BigInteger.valueOf(dirId));		
	}
	
	@PutMapping(value= "/upmu")
	public List<PfileResDto> updateUpmu(@RequestBody PfileReqDto upmuReqDto) {
		log.info("===========update========/upmu");
		log.info(upmuReqDto.toString());
		
		upmuService.updateUpmucontents(upmuReqDto);
		return upmuService.getUpmu(BigInteger.valueOf((upmuReqDto.getDirId()).longValue())); 
	}
	
	@DeleteMapping(value= "/upmu/{upmuId}")
	public List<PfileResDto> deleteUpmu(@PathVariable String upmuId) {
		log.info("===========delete========/upmu/" + upmuId);
		return upmuService.deleteUpmu(upmuId);
	}
	
	
//	@GetMapping(value="/upmu")
//	public List<>
		
}
