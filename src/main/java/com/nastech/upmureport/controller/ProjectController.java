package com.nastech.upmureport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.service.ProjectService;

@RestController
@RequestMapping(value = "/api/projects")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	//API TEST
	@CrossOrigin
	@GetMapping(value = "/getAll")
//	ResponseEntity<List<Project>> getAll(@RequestHeader String authorization) {
//		if (authorization == null) authorization = "null";
//		String userInfo = new String(Base64.getDecoder().decode(authorization));
//		System.out.println(userInfo);
	ResponseEntity<List<Project>> getAll() {
		List<Project> projects = projectService.findAll();
		
		return new ResponseEntity<List<Project>>(projects, HttpStatus.OK);
	}
}
