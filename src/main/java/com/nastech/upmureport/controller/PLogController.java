package com.nastech.upmureport.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.domain.dto.PLogDto;
import com.nastech.upmureport.service.PLogService;

@RestController
@RequestMapping("/plog")
public class PLogController {
	
	private static final Log LOG = LogFactory.getLog(PLogController.class);
	
	private final PLogService pLogService;
	
	
	public PLogController(PLogService pfileLogService) {
		
		this.pLogService = pfileLogService;
	}
	
	// 프로젝트 별 로그 리스트 조회
	@GetMapping(value = "/{projectId}")
	public List<PLogDto> getPfileLog(@PathVariable String projectId){
		
		return pLogService.getPLogs(projectId);
	}
}
