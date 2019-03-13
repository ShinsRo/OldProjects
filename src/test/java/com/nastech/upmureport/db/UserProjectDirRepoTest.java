package com.nastech.upmureport.db;

import static org.junit.Assert.assertTrue;

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
import com.nastech.upmureport.domain.entity.Dir;
import com.nastech.upmureport.domain.entity.ProjStat;
import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.domain.entity.User;
import com.nastech.upmureport.domain.entity.UserProject;
import com.nastech.upmureport.domain.entity.UserProjectDir;
import com.nastech.upmureport.domain.repository.DirRepository;
import com.nastech.upmureport.domain.repository.ProjectRepository;
import com.nastech.upmureport.domain.repository.UserProjectDirRepository;
import com.nastech.upmureport.domain.repository.UserProjectRepository;
import com.nastech.upmureport.domain.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, PersistenceJPAConfig.class}, loader=AnnotationConfigWebContextLoader.class)
public class UserProjectDirRepoTest {
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserProjectRepository userProjectRepsitory;
	
	@Autowired
	UserProjectDirRepository userProjectDirRepository;
	
	@Autowired
	DirRepository dirRepository;
	/**
	 * User, Project Connection test
	 * 시나리오
	 *  1. 유저 마규석, 김승신, 김윤상을 등록한다.
	 *  2. 유저 마규석을 조회한다.
	 *  3. 유저 마규석이 프로젝트 1을 등록한다.
	 *  4. 프로젝트 1에 유저 김승신, 김윤상을 추가한다.
	 *  5. 유저 김승신이 보유한 프로젝트를 조회한다.
	 *  6. 이 프로젝트가 프로젝트 1인지 확인한다.
	 */
	
	@Test
	public void 유저프로젝트_연결테스트() {
		List<User> users = new ArrayList<User>();
		
		// 1. 유저 마규석, 김승신, 김윤상을 등록한다.
		users.add(User.builder()
			.userId("12347")
			.userName("마규석")
			.userPass("1234")
			.build()
		);
		users.add(User.builder()
			.userId("12346")
			.userName("김승신")
			.userPass("1234")
			.build()
		);
		users.add(User.builder()
			.userId("12345")
			.userName("김윤상")
			.userPass("1234")
			.build()
		);
		userRepository.saveAll(users);
		
		// 2. 유저 마규석을 조회한다.
		User ksm = userRepository.findOneByUserName("마규석");
		assertTrue(ksm.getUserName().equals("마규석"));
		
		// 3. 유저 마규석이 프로젝트 1을 등록한다.
		Project project1 = Project.builder()
				.projName("프로젝트 1")
				.projSubject("담당업무 제목")
				.projCaleGubun("주기성")
				.projProgress(0)
				.projStartDate(LocalDateTime.now())
				.projDesc("담당업무 설명")
				.build();

	
		project1 = projectRepository.save(project1);
		project1 = projectRepository.findById(project1.getProjId()).get();
		userProjectRepsitory.save(
				UserProject.builder()
					.project(project1)
					.projStat(ProjStat.대기)
					.user(ksm)
					.build());
		
		// 4. 프로젝트 1에 유저 김승신, 김윤상을 추가한다.
		User ssk = userRepository.findOneByUserName("김승신");
		User ysk = userRepository.findOneByUserId("12345");
		
		userProjectRepsitory.save(
				UserProject.builder()
					.project(project1)
					.user(ssk)
					.projStat(ProjStat.보류)
					.build());
		
		userProjectRepsitory.save(
				UserProject.builder()
					.project(project1)
					.user(ysk)
					.projStat(ProjStat.접수)
					.build());

		//  6. 유저 김승신이 보유한 프로젝트를 조회한다.
		List<UserProject> projConnsOfssk = userProjectRepsitory.findAllByUser(ssk);
		List<Project> projsOfssk = new ArrayList<Project>();
		for (UserProject userProject : projConnsOfssk) {
			projsOfssk.add(userProject.getProject());
		}
		
		//  7. 이 프로젝트가 프로젝트 1인지 확인한다.
		assertTrue(projsOfssk.get(0).getProjName().equals("프로젝트 1"));
	}
	
	@Test
	public void 유저프로젝트디렉토리_연결테스트() {
		/**
		 * 유저
		 *  유저 1 : 마규석 / 12347
		 *  유저 2 : 김승신 / 12346
		 *  유저 3 : 김윤상 / 12345
		 * 
		 * 프로젝트
		 *  프로젝트 1
		 */
		유저프로젝트_연결테스트();
		
		User ksm = userRepository.findOneByUserName("마규석");
		
		
		List<UserProject> ksmProjects = userProjectRepsitory.findAllByUser(ksm);
		
		UserProject ksmProject1 = null;
		// 사용자가 "프로젝트 1"을 선택하는 것을 시뮬레이션하는 반복문
		for (UserProject userProject : ksmProjects) {
			if (userProject.getProject().getProjName().equals("프로젝트 1")) {
				ksmProject1 = userProject;
			}
		}
		assertTrue(ksmProject1 != null);
		
		Dir project1Dir = Dir.builder()
				.dirName("proj1_dir1")
				.createDate(LocalDateTime.now())
				.build();
		project1Dir = dirRepository.save(project1Dir);
		
		userProjectDirRepository.save(UserProjectDir.builder()
				.project(ksmProject1.getProject())
				.user(ksmProject1.getUser())
				.dir(project1Dir)
				.build());
		
		List<UserProjectDir> ksmProjDirs = userProjectDirRepository.findAllByUser(ksm);
		
		assertTrue(ksmProjDirs.get(0).getDir().getDirName().equals("proj1_dir1"));	
	}
}
