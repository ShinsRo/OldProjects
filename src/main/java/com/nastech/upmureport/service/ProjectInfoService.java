package com.nastech.upmureport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.dto.ProjectInfoDto;
import com.nastech.upmureport.domain.repository.ProjectInfoRepository;

@Service
public class ProjectInfoService {
	@Autowired
	ProjectInfoRepository pir;
	
	public List<ProjectInfoDto> getProjectInfosByOption(String ...args) {
		
		return null;
	}
}
