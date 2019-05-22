/** 
 * 프로젝트 CRUD 관련 비동기 통신 URL 매핑 및 제어 정의
 * 
 * 2019.05.22.
 * @author 김승신
 */
package com.nastech.upmureport.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	Log Logger = LogFactory.getLog(ProjectController.class);
	
	@Autowired
	private ProjectService ps;
	
	@GetMapping(value = "/list")
	ResponseEntity<?> list(@RequestParam(value = "mid", required=true) String mid) {
		List<ProjectDto> returnBody = null;
		ResponseEntity<?> response = null;
		
		try {			
			returnBody = ps.listByMid(mid);
			response = ResponseEntity.ok().body(returnBody);
		}
		catch (IllegalArgumentException iae) 	{ response = ResponseEntity.badRequest().body("입력 형식이 올바르지 않습니다."); } 
		catch (NoSuchElementException nee) 		{ response = ResponseEntity.badRequest().body("알 수 없는 유저 정보입니다."); }
		catch (Exception e) 					{ response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e); }
		
		return response;
	}
	
	@PostMapping(value = "/register")
	ResponseEntity<?> register(@RequestBody ProjectDto pDto) {
		ProjectDto returnBody = null;
		ResponseEntity<?> response = null;
		
		try {			
			returnBody = ps.register(pDto);
			response = ResponseEntity.ok().body(returnBody);
		}
		catch (IllegalArgumentException iae) 	{ response = ResponseEntity.badRequest().body("입력 형식이 올바르지 않습니다."); } 
		catch (NoSuchElementException nee) 		{ response = ResponseEntity.badRequest().body("알 수 없는 유저 정보입니다."); }
		catch (Exception e) 					{ response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e); }
		
		return response;
	}
	
	@PutMapping(value = "/correct")
	ResponseEntity<?> correct(@RequestBody ProjectDto pDto) {
		ProjectDto returnBody = null;
		ResponseEntity<?> response = null;
		
		try {			
			returnBody = ps.correct(pDto);
			response = ResponseEntity.ok().body(returnBody);
		}
		catch (IllegalArgumentException iae) 	{ response = ResponseEntity.badRequest().body("입력 형식이 올바르지 않습니다."); } 
		catch (NoSuchElementException nee) 		{ response = ResponseEntity.badRequest().body("알 수 없는 프로젝트 정보이거나 유저 정보입니다."); }
		catch (Exception e) 					{ response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e); }
		
		return response;
	}
	
	@PutMapping(value = "/disable")
	ResponseEntity<?> disable(@RequestBody ProjectDto pDto)  {
		ProjectDto returnBody = null;
		ResponseEntity<?> response = null;
		
		try {			
			returnBody = ps.disable(pDto);
			response = ResponseEntity.ok().body(returnBody);
		}
		catch (IllegalArgumentException iae) 	{ response = ResponseEntity.badRequest().body("입력 형식이 올바르지 않습니다."); } 
		catch (NoSuchElementException nee) 		{ response = ResponseEntity.badRequest().body("알 수 없는 프로젝트 정보이거나 유저 정보입니다."); }
		catch (Exception e) 					{ response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e); }
		
		return response;
	}
}
