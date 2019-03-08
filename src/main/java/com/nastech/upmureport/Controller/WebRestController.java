package com.nastech.upmureport.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.domain.dto.DeptDto;
import com.nastech.upmureport.domain.repository.DeptRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class WebRestController {
	private DeptRepository deptRepository;
	
	@PostMapping("/posts")
	public void saveDept(@RequestBody DeptDto dto)
	{
		deptRepository.save(dto.toEntity());
	}
}
