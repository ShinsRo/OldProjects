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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.domain.dto.PfileDto;
import com.nastech.upmureport.domain.dto.PfileDto.PfileResDto;
import com.nastech.upmureport.service.PfileService;

@RestController
@RequestMapping("/pfile")
public class PfileController {
	
	PfileService pfileService;
	private static final Log LOG = LogFactory.getLog(PfileController.class);
	
	public PfileController(PfileService pfileService) {
		// TODO Auto-generated constructor stub
		this.pfileService = pfileService;
	}
	
	// pfile 추가 
	@PostMapping()
	public PfileDto.PfileResDto addPfile(@RequestBody PfileDto.PfileReqDto pfileReqDto) {
		
		LOG.info(pfileReqDto.toString());
		
		PfileDto.PfileResDto pfileResDto = pfileService.addPfile(pfileReqDto);
				
		return pfileResDto;
	}
	
	// pdirId 별 pfile 조회 후 리턴
	@GetMapping(value="/{pdirId}")
	public List<PfileDto.PfileResDto> getPfile(@PathVariable String pdirId){
		
		return pfileService.getPfiles(BigInteger.valueOf(Long.parseLong(pdirId)));		
	}
	
	// pfile 수정
	@PutMapping()
	public List<PfileDto.PfileResDto> updatePfile(@RequestBody PfileDto.PfileReqDto pfileReqDto) {
		
		
		pfileService.updatePfile(pfileReqDto);
		
		return pfileService.getPfiles(BigInteger.valueOf((pfileReqDto.getPdirId()).longValue())); 
	}
		
	@DeleteMapping(value= "/{pfileId}")
	public List<PfileDto.PfileResDto> deletePfile(@PathVariable String pfileId) {
		
		return pfileService.deletePfile(pfileId);
	}
	
	@PutMapping("/move/{pfileId}/{pdirId}")
	public List<PfileDto.PfileResDto> movePfile(@PathVariable String pfileId, @PathVariable String pdirId){
		
		return pfileService.movePfile(pfileId, pdirId);		
	}
	
	@PutMapping("/copy/{pfileId}/{pdirId}")
	public PfileDto.PfileResDto copyPfile(@PathVariable String pfileId, @PathVariable String pdirId){
		
		return pfileService.copyPfile(pfileId, pdirId);		
	}
}
