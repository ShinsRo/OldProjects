package com.nastech.upmureport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.domain.dto.ProjectInfoDto;
import com.nastech.upmureport.domain.dto.ProjectQueryDto;
import com.nastech.upmureport.service.ProjectInfoService;

@RestController
@RequestMapping(value = "/api/projectInfo")
public class ProjectInfoController {
	@Autowired
	ProjectInfoService pis;
	
	@GetMapping(value = "/fetch")
	ResponseEntity<?> fetch(@RequestBody ProjectQueryDto pqDto) {
		List<ProjectInfoDto> returnBody = null;
		ResponseEntity<?> response = null;
		
		returnBody = pis.getProjectInfosByOps(pqDto);
		response = ResponseEntity.ok().body(returnBody);
		
		return response;
	}
}
