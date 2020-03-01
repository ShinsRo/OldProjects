package com.nastech.upmureport.feature.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.feature.project.domain.dto.ProjectInfoDto;
import com.nastech.upmureport.feature.project.domain.dto.ProjectQueryDto;
import com.nastech.upmureport.feature.project.service.ProjectInfoService;

@RestController
@RequestMapping(value = "/api/projectInfo")
public class ProjectInfoController {
	@Autowired
	ProjectInfoService pis;
	
	@PostMapping(value = "/fetch")
	ResponseEntity<?> fetch(@RequestBody ProjectQueryDto pqDto) {
		Map<String, Object> returnBody 			= new HashMap<String, Object>();
		ResponseEntity<?> response 				= null;
		List<ProjectInfoDto> projectInfoDtos 	= null;
		
		projectInfoDtos = pis.getProjectInfosByOps(pqDto);
		
		returnBody.put("projectInfoDtos", projectInfoDtos);
		returnBody.put("queryOps", pqDto.getQueryOps());
		response = ResponseEntity.ok().body(returnBody);
		
		return response;
	}
	
	@PostMapping(value = "/deleteOne")
	ResponseEntity<?> deleteOne(@RequestBody Integer queryOps, @RequestBody String id) {
		ResponseEntity<?> response 	= null;
		
		pis.deleteOne(queryOps, id);
		response = ResponseEntity.ok().body(true);
		
		return response;
	}
}
