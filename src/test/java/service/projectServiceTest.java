package service;

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
import com.nastech.upmureport.domain.dto.DirDto;
import com.nastech.upmureport.domain.dto.ProjectDto;
import com.nastech.upmureport.domain.dto.UserDto;
import com.nastech.upmureport.domain.dto.UserProjectDto;
import com.nastech.upmureport.domain.entity.ProjStat;
import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.domain.entity.User;
import com.nastech.upmureport.domain.entity.UserProject;
import com.nastech.upmureport.domain.repository.DirRepository;
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
			ProjectDto projectDto = project.toDto();
			projectDto.setUserId("1111");
			upList.add(projectService.register(projectDto));
		}
		
		return upList;
	}
	
	@After
	public void clearAll() {
		userProjectRepository.deleteAll();
		projectRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@Test
	public void 디렉토리_리스트_테스트() {
		setTempUsers();
		setUpTempData();
		List<UserProject> upList = setTempProjs();
		projectService.registerDir(
				DirDto.builder()
				.dirName("디렉토리")
				.parentProjId("" + upList.get(0).getProject().getProjId())
				.userId("1111")
				.build()
				);
		
//		projectService.findDirsByProjId("" + upList.get(0).getProject().getProjId());
	}
}
