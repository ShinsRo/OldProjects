package com.nastech.upmureport.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.domain.dto.ProjectDto;
import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.service.ProjectService;

@CrossOrigin
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
	
	
	
	
	//API TEST
	@GetMapping(value = "/getAll")
	ResponseEntity<List<Project>> getAll() {
		List<Project> projects = projectService.findAll();
		
		return new ResponseEntity<List<Project>>(projects, HttpStatus.OK);
	}
}
