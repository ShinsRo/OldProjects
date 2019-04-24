package com.nastech.upmureport.controller;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

@RestController
public class PfileController {
	
	PfileService pfileService;
	private static final Log LOG = LogFactory.getLog(PfileController.class);
	
	public PfileController(PfileService pfileService) {
		// TODO Auto-generated constructor stub
		this.pfileService = pfileService;
	}
	
	// pfile 추가 
	@PostMapping(value= "/pfile")
	public PfileResDto addPfile(@RequestBody PfileReqDto pfileReqDto) {
		LOG.info("==========================/pfile");
		LOG.info(pfileReqDto.toString());
		
		PfileResDto pfileResDto = pfileService.addPfile(pfileReqDto);
				
		return pfileResDto;
	}
	
	// pdirId 별 pfile 조회 후 리턴
	@GetMapping(value="/pfile/{pdirId}")
	public List<PfileResDto> getPfile(@PathVariable String pdirId){
		LOG.info("=========get=============/pfile");
		LOG.info("dir id = " + pdirId);
		return pfileService.getPfiles(BigInteger.valueOf(Long.parseLong(pdirId)));		
	}


	// pfile 수정
	@PutMapping(value= "/pfile")
	public List<PfileResDto> updatePfile(@RequestBody PfileReqDto pfileReqDto) {
		LOG.info("===========update========/pfile");
		
		pfileService.updatePfile(pfileReqDto);
		
		return pfileService.getPfiles(BigInteger.valueOf((pfileReqDto.getPdirId()).longValue())); 
	}
	
	
	@DeleteMapping(value= "/pfile/{pfileId}")
	public List<PfileResDto> deletePfile(@PathVariable String pfileId) {
		LOG.info("===========delete========/pfile/" + pfileId);
		return pfileService.deletePfile(pfileId);
	}
	
	
//	@GetMapping(value="/upmu")
//	public List<>
		
}
