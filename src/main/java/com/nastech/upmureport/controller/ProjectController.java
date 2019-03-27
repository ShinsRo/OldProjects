package com.nastech.upmureport.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.domain.dto.DirDto;
import com.nastech.upmureport.domain.dto.ProjectDto;
import com.nastech.upmureport.domain.entity.ProjStat;
import com.nastech.upmureport.service.ProjectService;


@RestController
@RequestMapping(value = "/api/projects")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@GetMapping(value = "/list")
	ResponseEntity<List<ProjectDto>> list(HttpServletRequest request, @RequestParam(value = "userId") String userId) {
		List<ProjectDto> dtoList = projectService.findProjectsByUserId(userId);
		return new ResponseEntity<List<ProjectDto>>(dtoList, HttpStatus.OK);
	}
	
	@PostMapping(value ="/register",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<Boolean> register(@RequestBody Map<String, String> formData) {
		
//		SimpleDateFormat sdf = new SimpleDateFormat(formData.get("startDate"));
		
		ProjectDto projDto = ProjectDto.builder()
				.projName(formData.get("projId"))
				.projSubject(formData.get("projSubject"))
				.projDesc(formData.get("projDesc"))
				.projCaleGubun(formData.get("projCaleGubun"))
				.startDate(new Date(formData.get("startDate")))
				.endDate(new Date(formData.get("endDate")))
				.projStat(formData.get("projStat"))
				.userId(formData.get("userId"))
				.build();
		
		if (projectService.register(projDto).getProject() != null)
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		else 
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
	}
	
	@PostMapping(value = "/dirs")
	ResponseEntity<List<DirDto>> dirs(@RequestBody Map<String, String> formData) {
		List<DirDto> dtoList = projectService.findDirsByProjId(formData.get("projId"));
		return new ResponseEntity<List<DirDto>>(dtoList, HttpStatus.OK);
	}
	
//	@PostMapping(value = "/registerDir")
//	ResponseEntity<Object> registerDir(@RequestBody Dir dir) {
//		Object returnDto;
//		if ((returnDto = projectService.registerDir(dir)) != null) 
//			return new ResponseEntity<Object>(true, HttpStatus.OK);
//		else 
//			return new ResponseEntity<Object>(false, HttpStatus.OK);
//	}
}
