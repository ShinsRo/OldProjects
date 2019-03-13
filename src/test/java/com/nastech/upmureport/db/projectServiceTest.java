package com.nastech.upmureport.db;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	
	private User tempUserRegister () {
		return userRepository.save(User.builder()
			.userId("1234")
			.userName("김승신")
			.userPass("nas1234!")
			.build());
	}
	
	@Test
	public void 프로젝트_등록_테스트 () {
		//임시 회원 가입 유저
		User loginedUserEntity = tempUserRegister();
		
		//로그인한 유저
		UserDto userDto = new UserDto();
		userDto.setUserId(loginedUserEntity.getUserId());
		
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
		UserProject userProject = projectService.register(projectDto, userDto, "");
		assertNotNull(userProject);
		
		List<UserProject> userProjList = userProjectRepository.findAllByUser(loginedUserEntity);
		
		//project에 관련한 유저가 tempUser와 같은가.
		assertThat(userProjList.get(0).getUser(), is(loginedUserEntity));
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
				.projName("프로젝트 1").projCaleGubun("주기성").projDesc("")
				.projProgress(0).projSubject("이러저런업무임").createdDate(LocalDateTime.now())
				.projStartDate(LocalDateTime.of(2017, 3, 2, 0, 0))
				.projEndDate(LocalDateTime.of(2017, 3, 2, 0, 0))
				.build());
		tempProj.add(Project.builder()
				.projName("프로젝트 2").projCaleGubun("비주기성").projDesc("무슨무슨프로젝트")
				.projProgress(0).projSubject("이러저런업무임").createdDate(LocalDateTime.now())
				.projStartDate(LocalDateTime.of(2017, 3, 12, 0, 0))
				.projEndDate(LocalDateTime.of(2017, 6, 25, 0, 0))
				.build());
		tempProj.add(Project.builder()
				.projName("프로젝트 3").projCaleGubun("비주기성").projDesc("")
				.projProgress(20).projSubject("이러저런업무임").createdDate(LocalDateTime.now())
				.projStartDate(LocalDateTime.of(2018, 1, 31, 0, 0))
				.projEndDate(LocalDateTime.of(2019, 12, 31, 0, 0))
				.build());
		
		List<UserProject> upList = new ArrayList<UserProject>();
		for (Project project : tempProj) {
			upList.add(projectService.register(project.toDto(), creator, "대기"));
		}
		
		return upList;
	}
	
	@Test
	public void 프로젝트_수정_테스트 () {
//		setTempUsers();
//		setTempProjs();
	}
	
	@Test
	public void 프로젝트_삭제_테스트 () {
		
	}
}
