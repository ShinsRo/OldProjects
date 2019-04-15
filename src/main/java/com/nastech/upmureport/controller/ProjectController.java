package com.nastech.upmureport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.domain.dto.ProjectDto;
import com.nastech.upmureport.service.ProjectService;


@RestController
@RequestMapping(value = "/api/project")
public class ProjectController {
	@Autowired
	private ProjectService ps;
	
	@GetMapping(value = "/list")
	ResponseEntity<List<ProjectDto>> list(@RequestParam(value = "mid", required=true) String mid) {
		System.out.println(ps.listByMid(mid));
		return ResponseEntity.ok().body(ps.listByMid(mid));
	}
	
	@PostMapping(value = "/register")
	ResponseEntity<ProjectDto> register(@RequestBody ProjectDto pDto) {
		return ResponseEntity.ok().body(ps.register(pDto));
	}
	
	@PutMapping(value = "/correct")
	ResponseEntity<ProjectDto> correct(@RequestBody ProjectDto pDto) {
		return ResponseEntity.ok().body(ps.correct(pDto));
	}
	
	@PatchMapping(value = "/disable")
	ResponseEntity<ProjectDto> disable(@RequestBody ProjectDto pDto)  {
		return ResponseEntity.ok().body(ps.disable(pDto));
	}
}
