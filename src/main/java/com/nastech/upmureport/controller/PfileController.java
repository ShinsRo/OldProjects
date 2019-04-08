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
public class PfileController {
	
	PfileService pfileService;
	
	public PfileController(PfileService pfileService) {
		// TODO Auto-generated constructor stub
		this.pfileService = pfileService;
	}
	/*
	@GetMapping(value= "/test")
    //@RequestMapping(value="/test")
    public String goAddUpmu() {
    	log.info("==========================/test");
    	return "addUpmu";
    }
	*/
	
	@PostMapping(value= "/pfile")
	public PfileResDto addPfile(@RequestBody PfileReqDto pfileReqDto) {
		log.info("==========================/pfile");
		log.info(pfileReqDto.toString());
		
		PfileResDto pfileResDto = pfileService.addPfile(pfileReqDto);
				
		return pfileResDto;
	}
	
	@GetMapping(value="/pfile/{pdirId}")
	public List<PfileResDto> getPfile(@PathVariable String pdirId){
		log.info("=========get=============/pfile");
		log.info("dir id = " + pdirId);
		return pfileService.getPfiles(BigInteger.valueOf(Long.parseLong(pdirId)));		
	}
	
	@PutMapping(value= "/pfile")
	public List<PfileResDto> updatePfile(@RequestBody PfileReqDto pfileReqDto) {
		log.info("===========update========/pfile");
		log.info(pfileReqDto.toString());
		
		pfileService.updatePfile(pfileReqDto);
		return pfileService.getPfiles(BigInteger.valueOf((pfileReqDto.getPdirId()).longValue())); 
	}
	
	@DeleteMapping(value= "/pfile/{pfileId}")
	public List<PfileResDto> deletePfile(@PathVariable String pfileId) {
		log.info("===========delete========/pfile/" + pfileId);
		return pfileService.deletePfile(pfileId);
	}
	
	
//	@GetMapping(value="/upmu")
//	public List<>
		
}
