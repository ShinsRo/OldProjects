//package com.nastech.upmureport.service;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.nastech.upmureport.domain.dto.DirDto;
//import com.nastech.upmureport.domain.dto.ProjectDto;
//import com.nastech.upmureport.domain.entity.Dir;
//import com.nastech.upmureport.domain.entity.Project;
//import com.nastech.upmureport.domain.entity.User;
//import com.nastech.upmureport.domain.entity.support.Pstat;
//import com.nastech.upmureport.domain.entity.MemberProject;
//import com.nastech.upmureport.domain.repository.DirRepository;
//import com.nastech.upmureport.domain.repository.ProjectRepository;
//import com.nastech.upmureport.domain.repository.UserProjectRepository;
//import com.nastech.upmureport.domain.repository.UserRepository;
//
//@Service
//public class ProjectService {
//	@Autowired
//	UserRepository userRepository;
//	
//	@Autowired
//	ProjectRepository projectRepository;
//	
//	@Autowired
//	UserProjectRepository userProjectRepository;
//	
//	@Autowired
//	DirRepository dirRepository;
//	
//	/**
//	 * @param projectDto 프로젝트 등록할 정보
//	 * 넘겨받은 프로젝트 정보에 의거해 프로젝트를 등록합니다. 이 때 요청한 유저는 자동적으로 프로젝트에 소속합니다.
//	 * @param userDto 등록을 요청한 사용자
//	 * @param projStat 프로젝트 상태 
//	 * @return 등록자와 프로젝트 연결 객체, 해당 유저가 존재하지 않을 경우 NULL
//	 */
//	@Transactional
//	public MemberProject register(ProjectDto projectDto) throws NoSuchElementException {
//		User user = null;
//		try {
//			user = userRepository.findById(projectDto.getUserId()).get();
//		} catch (NoSuchElementException nsee) {
//			throw new NoSuchElementException("userId에 해당하는 사용자가 없습니다.");
//		}
//		
//		Project project = projectDto.toEntity();
//		project.setCreatedDate(new Date(System.currentTimeMillis())); 
//		projectRepository.save(project);
//		
//		Pstat pj;
//		try {
//			pj = Pstat.valueOf(projectDto.getProjStat());
//		} catch (IllegalArgumentException iae) {
//			pj = Pstat.대기;
//		}
//		
//		MemberProject userProject = MemberProject.builder()
//				.user(user)
//				.project(project)
//				.projStat(pj)
//				.build();
//		
//		return userProjectRepository.save(userProject);
//	}
//	
//
//	/**
//	 * 프로젝트 DTO와 UserProjectDTO로부터 기존 내용과 다른 사항을 수정합니다.
//	 * @param projectDto
//	 * @return 변경한 UserProject 오브젝트
//	 */
//	@Transactional
//	public MemberProject update(ProjectDto projectDto) {
//		
//		//원본 유저, 프로젝트
//		MemberProject userProj = userProjectRepository.findOneByUserIdAndProjId(projectDto.getProjId(), projectDto.getUserId());
//		
//		Pstat pj;
//		try {
//			pj = Pstat.valueOf(projectDto.getProjStat());
//		} catch (IllegalArgumentException iae) {
//			pj = Pstat.대기;
//		}
//		
//		userProj.setProjStat(pj);
//		
//		Project proj = projectDto.toEntity();
//		projectRepository.save(proj);
//		userProjectRepository.save(userProj);
//		return userProj;
//	}
//	
//	public List<ProjectDto> findProjectsByUserId(String userId) {
//		List<MemberProject> userProjs = userProjectRepository.findAllByUser(userId);
//		List<ProjectDto> projects = new ArrayList<ProjectDto>(); 
//		for (MemberProject up : userProjs) {
//			ProjectDto project = new ProjectDto(up);
//			projects.add(project);
//		}
//		
//		return projects;
//	}
//
//	public List<Project> findAll() {
//		return projectRepository.findAll();
//	}	
//	
//	public Project findOneById(Integer projId) throws NoSuchElementException {
//		return projectRepository.findById(projId).get();
//	}
//
//
//	public Project disableProject(Integer projId) throws NoSuchElementException {
//		Project proj = findOneById(projId);
//		proj.setDeleteFlag(true);
//		return projectRepository.save(proj);
//	}
//
//	public void deleteProject(Integer projId) {
//		Project project = projectRepository.getOne(projId);
//		List<MemberProject> relatedUpList = userProjectRepository.findAllByProject(project);
//		
//		userProjectRepository.deleteAll(relatedUpList);
//		projectRepository.deleteById(projId);
//	}
//
//	public MemberProject findUserProjectByProjIdAndUserId(Integer projId, String userId) throws NoSuchElementException {
//		MemberProject up = userProjectRepository.findOneByUserIdAndProjId(projId, userId);
//		if (up == null) throw new NoSuchElementException();
//		return up;
//	}
//	
//	public void disableUserProject(Integer projId, String userId) throws NoSuchElementException {
//		MemberProject up = findUserProjectByProjIdAndUserId(projId, userId);
//		up.setDeleteFlag(true);
//		userProjectRepository.delete(up);		
//	}
//
//	
//	/**
//	 * 
//	 * @param userId
//	 * @return 유저 아이디에 연관한 모든 유저-프로젝트 리스트
//	 */
//	public List<MemberProject> findAllUserProjectByUserId(String userId) {
//		List<MemberProject> userProjs = new ArrayList<MemberProject>();
//		
//		userProjs = userProjectRepository.findAllByUser(userId);
//		return userProjs;
//	}
//	
//	/**
//	 * 
//	 * @param projId
//	 * @return
//	 */
//	public List<MemberProject> findAllUserProjectByProjId(Integer projId) {
//		List<MemberProject> userProjs = new ArrayList<MemberProject>();
//		
//		userProjs = userProjectRepository.findAllByProject(
//				Project.builder().projId(projId).build());
//		return userProjs;
//	}
//
//	
//	public List<DirDto> findDirsByProjId(String projId) {
//		List<Dir> dirs = dirRepository.findAllByParentProjId(Integer.parseInt(projId));
//		List<DirDto> dirDtos = new ArrayList<DirDto>();
//		for (Dir dir : dirs) {
//			DirDto temp = DirDto.builder()
//					.projId(projId)
//					.userId(dir.getUser().getUserId())
//					.userName(dir.getUser().getUserName())
//					.dirId(""+dir.getDirId())
//					.dirName(dir.getDirName())
//					.build();
//			
//			if (dir.getParentDir() == null) {
//				temp.setParentDirId("root");				
//			} else {
//				temp.setParentDirId(""+dir.getParentDir().getDirId());
//			}
//			dirDtos.add(temp);
//		}
//		return dirDtos;
//	}
//
//	public Dir registerDir(DirDto dirDto) {
//		String userId = dirDto.getUserId();
//		Integer projId = Integer.parseInt(dirDto.getProjId());
//		
//		User user = userRepository.findById(userId).get();
//		Project proj = projectRepository.findById(projId).get();
//		
//		String parentDirId = dirDto.getParentDirId();
//		Dir parentDir = null;
//		if (parentDirId != null) parentDir = dirRepository.getOne(Integer.valueOf(parentDirId));
//		
//		Dir dir = Dir.builder()
//				.dirName(dirDto.getDirName())
//				.user(user)
//				.project(proj)
//				.build();
//		
//		if(parentDir != null ) dir.setParentDir(parentDir);
//		return dirRepository.save(dir);
//	}
//
//
//	public void disableDir(DirDto dto) {
//		Dir target = dirRepository.getOne(Integer.valueOf(dto.getDirId()));
//		target.setDeleteFlag(true);
//		dirRepository.save(target);
//	}
//
//}