//package service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import com.nastech.upmureport.config.PersistenceJPAConfig;
//import com.nastech.upmureport.config.WebConfig;
//import com.nastech.upmureport.domain.dto.DirDto;
//import com.nastech.upmureport.domain.dto.ProjectDto;
//import com.nastech.upmureport.domain.dto.UserDto;
//import com.nastech.upmureport.domain.entity.Project;
//import com.nastech.upmureport.domain.entity.User;
//import com.nastech.upmureport.domain.entity.MemberProject;
//import com.nastech.upmureport.domain.repository.ProjectRepository;
//import com.nastech.upmureport.domain.repository.UserProjectRepository;
//import com.nastech.upmureport.domain.repository.UserRepository;
//import com.nastech.upmureport.service.ProjectService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = {WebConfig.class, PersistenceJPAConfig.class}, loader=AnnotationConfigWebContextLoader.class)
//public class projectServiceTest {
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
//	ProjectService projectService;
//	
//	@Before
//	public void setUp() {
//		List<User> userList = new ArrayList<User>();
//		List<Project> projList = new ArrayList<Project>();
//		
//		userList.add(
//			User.builder()
//				.userId("1111")
//				.userPass("nas")
//				.userName("김승신")
//				.deleteFlag(false)
//				.build()
//		);
//		userList.add(
//			User.builder()
//				.userId("1112")
//				.userPass("nas")
//				.userName("마규석")
//				.deleteFlag(false)
//				.build()
//		);
//		userList.add(
//			User.builder()
//				.userId("1113")
//				.userPass("nas")
//				.userName("김윤상")
//				.deleteFlag(false)
//				.build()
//		);
//		
//		
//		projList.add(
//			Project.builder()
//				.projId(300)
//				.projProgress(20)
//				.projName("일일업무 보고 프로젝트")
//				.projSubject("일일업무보고 체계 도입")
//				.projDesc("도입을 통한 생산성 향상")
//				.projCaleGubun("주기성")
//				.deleteFlag(false)
//				.build()
//		);
//		projList.add(
//			Project.builder()
//				.projId(400)
//				.projProgress(100)
//				.projName("업무리포트 웹 버전 프로젝트")
//				.projSubject("업무리포트 웹 버전 이전")
//				.projDesc("버전 이전을  통한 생산성 향상")
//				.projCaleGubun("주기성")
//				.deleteFlag(false)
//				.build()
//		);
//		projList.add(
//			Project.builder()
//				.projId(500)
//				.projProgress(60)
//				.projName("프로젝트 알파")
//				.projSubject("코딩계 알파고 빌드")
//				.projDesc("코딩 머신~")
//				.projCaleGubun("비주기성")
//				.deleteFlag(false)
//				.build()
//		);
//		
////		userRepository.saveAll(userList);
////		projectRepository.saveAll(projList);
//		
//		
//		
//		
//	}
//	
//	
//}
