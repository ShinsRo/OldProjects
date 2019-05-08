package com.nastech.upmureport.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.domain.dto.LogDto;
import com.nastech.upmureport.service.PLogService;

@RestController
@RequestMapping("/plog")
public class PLogController {
	
	private static final Log LOG = LogFactory.getLog(PLogController.class);
	
	private PLogService pfileLogService;
	
	
	public PLogController(PLogService pfileLogService) {
		
		this.pfileLogService = pfileLogService;
	}
	
	@GetMapping(value = "/{pdirId}")
	public LogDto.PLogDto getPfileLog(@PathVariable String pdirId){
	
		LOG.info("----------get log ---  " + pdirId);
		
		return pfileLogService.getPLogs(pdirId);		
	}
}