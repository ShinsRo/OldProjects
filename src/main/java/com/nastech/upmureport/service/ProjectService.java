package com.nastech.upmureport.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nastech.upmureport.domain.dto.ProjectDto;
import com.nastech.upmureport.domain.dto.UserDto;
import com.nastech.upmureport.domain.entity.ProjStat;
import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.domain.entity.User;
import com.nastech.upmureport.domain.entity.UserProject;
import com.nastech.upmureport.domain.repository.ProjectRepository;
import com.nastech.upmureport.domain.repository.UserProjectRepository;
import com.nastech.upmureport.domain.repository.UserRepository;

@Service
public class ProjectService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	UserProjectRepository userProjectRepository;
	/**
	 * 넘겨받은 프로젝트 정보에 의거해 프로젝트를 등록합니다. 이 때 요청한 유저는 자동적으로 프로젝트에 소속합니다.
	 * @param projectDto 프로젝트 등록할 정보
	 * @param userDto 등록을 요청한 사용자
	 * @param projStat 프로젝트 상태 
	 * @return 등록자와 프로젝트 연결 객체, 해당 유저가 존재하지 않을 경우 NULL
	 */
	@Transactional
	public UserProject register(ProjectDto projectDto, UserDto userDto, String projStat) {
		User user = null;
		try {
			user = userRepository.findById(userDto.getUserId()).get();
		} catch (NoSuchElementException nsee) {
			return null;
		}
		
		Project project = projectDto.toEntity();
		project.setCreatedDate(LocalDateTime.now());
		project = projectRepository.save(project);
		
		ProjStat pj;
		try {
			pj = ProjStat.valueOf(projStat);
		} catch (IllegalArgumentException iae) {
			pj = ProjStat.대기;
		}
		
		UserProject userProject = UserProject.builder()
				.user(user)
				.project(project)
				.projStat(pj)
				.build();
		
		return userProjectRepository.save(userProject);
	}
	
}
