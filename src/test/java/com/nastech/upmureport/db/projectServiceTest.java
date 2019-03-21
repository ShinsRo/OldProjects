package com.nastech.upmureport.db;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.nastech.upmureport.config.PersistenceJPAConfig;
import com.nastech.upmureport.config.WebConfig;
import com.nastech.upmureport.domain.dto.ProjectDto;
import com.nastech.upmureport.domain.dto.UserDto;
import com.nastech.upmureport.domain.dto.UserProjectDto;
import com.nastech.upmureport.domain.entity.ProjStat;
import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.domain.entity.User;
import com.nastech.upmureport.domain.entity.UserProject;
import com.nastech.upmureport.domain.repository.ProjectRepository;
import com.nastech.upmureport.domain.repository.UserProjectRepository;
import com.nastech.upmureport.domain.repository.UserRepository;
import com.nastech.upmureport.service.ProjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, PersistenceJPAConfig.class}, loader=AnnotationConfigWebContextLoader.class)
public class projectServiceTest {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	UserProjectRepository userProjectRepository;
	
	@Autowired
	ProjectService projectService;
	
	@Before
	public void setUpTempData() {
		userProjectRepository.deleteAll();
		projectRepository.deleteAll();
		userRepository.deleteAll();
		setTempUsers();
		setTempProjs();
	}
	
	private List<User> setTempUsers() {
		List<User> tempUsers = new ArrayList<User>();
		tempUsers.add(User.builder()
				.userId("1111").userName("김승신")
				.userPass("1234").dept("인턴").build());
		tempUsers.add(User.builder()
				.userId("1112").userName("마규석")
				.userPass("1234").dept("인턴").build());
		tempUsers.add(User.builder()
				.userId("1113").userName("김윤상")
				.userPass("1234").dept("인턴").build());
		
		return userRepository.saveAll(tempUsers);
	}
	
	private List<UserProject> setTempProjs() {
		List<Project> tempProj = new ArrayList<Project>();
		UserDto creator = new UserDto();
		creator.setUserId("1111");
		
		tempProj.add(Project.builder()
				.projName("일일업무보고 프로젝트").projCaleGubun("주기성").projDesc("일일업무 보고체계를 보입하여 진행사항을 파악한다.")
				.projProgress(0).projSubject("사원 일일업무 보고체계 도입").createdDate(LocalDateTime.now())
				.projStartDate(LocalDateTime.of(2017, 3, 2, 0, 0))
				.projEndDate(LocalDateTime.of(2017, 3, 2, 0, 0))
				.build());
		tempProj.add(Project.builder()
				.projName("업무리포트 웹 버전 프로젝트").projCaleGubun("주기성").projDesc("설명 없음")
				.projProgress(80).projSubject("업무리포트 프로그램을 웹으로 이전한다.").createdDate(LocalDateTime.now())
				.projStartDate(LocalDateTime.of(2017, 3, 12, 0, 0))
				.projEndDate(LocalDateTime.of(2017, 6, 25, 0, 0))
				.build());
		tempProj.add(Project.builder()
				.projName("나스텍 프로젝트").projCaleGubun("비주기성").projDesc("설명 없음")
				.projProgress(20).projSubject("나스텍~").createdDate(LocalDateTime.now())
				.projStartDate(LocalDateTime.of(2018, 1, 31, 0, 0))
				.projEndDate(LocalDateTime.of(2019, 12, 31, 0, 0))
				.build());
		
		List<UserProject> upList = new ArrayList<UserProject>();
		for (Project project : tempProj) {
			upList.add(projectService.register(project.toDto(), creator.getUserId(), "대기"));
		}
		
		return upList;
	}
	
	private User tempUserRegister() {
		return userRepository.save(User.builder()
			.userId("1234")
			.userName("임시사용자")
			.userPass("nas1234!")
			.build());
	}
	
	@After
	public void clearAll() {
		userProjectRepository.deleteAll();
		projectRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@Test
	public void 리스트_불러오기() {
		List<ProjectDto> projs = projectService.findProjectsByUserId("1111");
		assertTrue(projs.size() == 3);
		
		System.out.println(projs);
	}
	
	@Test
	public void 프로젝트_등록_테스트() {
		//임시 회원 가입 유저
		User loginedUserEntity = tempUserRegister();
		
		//로그인한 유저
//		UserDto userDto = new UserDto();
		
		//넘겨받은 프로젝트
		ProjectDto projectDto = ProjectDto.builder()
				.projName("프로젝트 2")
				.projCaleGubun("주기성")
				.projDesc("업무보고 프로젝트")
				.projProgress(0)
				.startYear("2019").startMonth("3").startDay("4")
				.endYear("2019").endMonth("6").endDay("21")
				.build();
		
		//프로젝트 등록
		UserProject userProject = projectService.register(projectDto, loginedUserEntity.getUserId(), "");
		assertNotNull(userProject);
		
		List<UserProject> userProjList = userProjectRepository.findAllByUser(loginedUserEntity);
		
		//project에 관련한 유저가 tempUser와 같은가.
		assertTrue(Objects.equals(loginedUserEntity.getUserId(), userProjList.get(0).getUser().getUserId()));
	}
	
	@Test
	public void 프로젝트_수정_테스트() {
		UserDto targetUserDto = new UserDto();
		ProjectDto targetProjDto;
		targetUserDto.setUserId("1111");
		
		List<UserProject> userProjectsByUserId = projectService.findAllUserProjectByUserId(targetUserDto.getUserId());
		
		assertTrue(userProjectsByUserId.size() == 3);
		
		UserProject temp = userProjectsByUserId.get(0);
		Integer projId = temp.getProject().getProjId();
		String userId = temp.getUser().getUserId();
		
		UserProjectDto targetUserProjectDto = new UserProjectDto(userId, projId, ProjStat.폐기);
		targetProjDto = temp.getProject().toDto();
		
		targetProjDto.setProjName("수정한 프로젝트 이름");
		UserProject updateResult = projectService.update(targetProjDto, targetUserProjectDto);
		
		Project targetProj = projectService.findOneById(projId);
		
		assertTrue(Objects.equals(updateResult.getProject().getProjId(), targetProj.getProjId()));
		assertTrue(Objects.equals(targetProj.getProjName(), updateResult.getProject().getProjName()));
	}
	
	@Test
	public void 프로젝트_삭제_테스트() {
		String userId = "1111";
		Project willBeDeleted = Project.builder()
				.projName("삭제될 프로젝트입니다.").projCaleGubun("주기성").projDesc("")
				.projProgress(0).projSubject("이러저런업무임").createdDate(LocalDateTime.now())
				.projStartDate(LocalDateTime.of(2017, 3, 2, 0, 0))
				.projEndDate(LocalDateTime.of(2017, 3, 2, 0, 0))
				.build();
		
		UserProject userProj = projectService.register(willBeDeleted.toDto(), userId, "");
		Integer projId = userProj.getProject().getProjId();
		
		Project proj = projectService.disableProject(projId);
		assertTrue(projId == proj.getProjId());
		assertTrue(proj.getDeleteFlag());
		
		
		assertTrue(projectService.findAllUserProjectByProjId(projId).size() == 1);
		projectService.deleteProject(projId);
		
		Boolean noSuchEleExcepFlag = false;
		try {
			projectService.findOneById(projId);
		} catch (NoSuchElementException e) {
			noSuchEleExcepFlag = true;
		}
		
		assertTrue(noSuchEleExcepFlag);
		assertTrue(projectService.findAllUserProjectByProjId(projId).size() == 0);
	}
}
