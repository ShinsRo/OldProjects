package com.nastech.upmureport.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.domain.dto.LogDto;
import com.nastech.upmureport.service.PfileLogService;

@RestController
//@RequestMapping("/log")
public class PLogController {
	
	private PfileLogService pfileLogService;
	
	public PLogController(PfileLogService pfileLogService) {
		
		this.pfileLogService = pfileLogService;
	}
	
	@GetMapping(value = "/loggg/{pdirId}")
	public List<LogDto.PfileLogDto> getPfileLog(@PathVariable String pdirId){
		
		return pfileLogService.getPfileLogs(pdirId);
		
	}
	
	
}