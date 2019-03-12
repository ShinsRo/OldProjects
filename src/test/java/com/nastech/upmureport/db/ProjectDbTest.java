package com.nastech.upmureport.db;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.nastech.upmureport.config.PersistenceJPAConfig;
import com.nastech.upmureport.config.WebConfig;
import com.nastech.upmureport.domain.entity.ProjStat;
import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.domain.entity.User;
import com.nastech.upmureport.domain.entity.UserProject;
import com.nastech.upmureport.domain.repository.ProjectRepository;
import com.nastech.upmureport.domain.repository.UserProjectRepository;
import com.nastech.upmureport.domain.repository.UserRepository;

import lombok.Data;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, PersistenceJPAConfig.class}, loader=AnnotationConfigWebContextLoader.class)
public class ProjectDbTest {
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserProjectRepository userProjectRepsitory;
	
	/**
	 * User, Project Connection test
	 * 시나리오
	 *  1. 유저 마규석, 김승신, 김윤상을 등록한다.
	 *  2. 유저 마규석을 조회한다.
	 *  3. 유저 마규석이 프로젝트 1을 등록한다.
	 *  4. 프로젝트 1에 유저 김승신, 김윤상을 추가한다.
	 *  5. 유저 김승신을 조회한다.
	 *  6. 유저 김승신이 보유한 프로젝트를 조회한다.
	 *  7. 이 프로젝트가 프로젝트 1인지 확인한다.
	 */
	
	@Test
	public void userProject연결테스트() {
		List<User> users = new ArrayList<User>();
		
//		// 1. 유저 마규석, 김승신, 김윤상을 등록한다.
//		users.add(User.builder()
//			.userId(11138)
//			.userName("마규석")
//			.userPass("1234")
//			.build()
//		);
//		users.add(User.builder()
//			.userName("김승신")
//			.userPass("1234")
//			.build()
//		);
//		users.add(User.builder()
//			.userName("김윤상")
//			.userPass("1234")
//			.build()
//		);
//		userRepository.saveAll(users);
//		
//		// 2. 유저 마규석을 조회한다.
//		User ksm = userRepository.findOneByUserName("마규석");
//		assertTrue(ksm.getUserName().equals("마규석"));
//		
//		// 3. 유저 마규석이 프로젝트 1을 등록한다.
//		
//		Project project1 = Project.builder()
//				.projName("프로젝트 1")
//				.projSubject("담당업무 제목")
//				.projCaleGubun("주기성")
//				.projProgress(0)
//				.userProject(new ArrayList<UserProject>())
//				.projStatCode(ProjStat.대기)
//				.projStartDate(LocalDateTime.now())
//				.projDesc("담당업무 설명")
//				.build();
//		
//		project1.getUserProject()
//			.add(UserProject.builder()
//					.user(ksm)
//					.roll("담당자")
//					.project(project1)
//					.build());
//		
//		projectRepository.save(project1);
//		
//		// 4. 프로젝트 1에 유저 김승신, 김윤상을 추가한다.
//		
//		User ssk = userRepository.findOneByUserName("김승신");
//		User ysk = userRepository.findOneByUserName("김윤상");
//		
//		project1.getUserProject()
//			.add(UserProject.builder()
//					.user(ssk)
//					.roll("참여자")
//					.project(project1)
//					.build());
//		project1.getUserProject()
//			.add(UserProject.builder()
//					.user(ysk)
//					.roll("참여자")
//					.project(project1)
//					.build());
//		
//		//  5. 유저 김승신을 조회한다.
//		ssk = userRepository.findOneByUserName("김승신");
////		
////		//  6. 유저 김승신이 보유한 프로젝트를 조회한다.
////		//  7. 이 프로젝트가 프로젝트 1인지 확인한다.
////		assertTrue(ssk.getUserProject().get(0).getProject().getProjName().equals("프로젝트 1"));
		
		
	}
}
