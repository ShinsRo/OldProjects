package com.nastech.upmureport.db;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import com.nastech.upmureport.domain.entity.AuthInfo;
import com.nastech.upmureport.domain.entity.Career;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.entity.MemberSystem;
import com.nastech.upmureport.domain.entity.Role;
import com.nastech.upmureport.domain.repository.AuthInfoRepository;
import com.nastech.upmureport.domain.repository.CareerRepository;
import com.nastech.upmureport.domain.repository.MemberProjectRepository;
import com.nastech.upmureport.domain.repository.MemberRepository;
import com.nastech.upmureport.domain.repository.MemberSystemRepository;
import com.nastech.upmureport.domain.repository.PdirRepository;
import com.nastech.upmureport.domain.repository.ProjectRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, PersistenceJPAConfig.class}, loader=AnnotationConfigWebContextLoader.class)
public class ProjectAndPdirDBTest {
	
	// Member repositories
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	MemberSystemRepository memberSystemRepository;
	@Autowired
	AuthInfoRepository authinfoRepository;
	@Autowired
	CareerRepository careerRepository;
	
	//Project and pdir repositories
	@Autowired
	MemberProjectRepository memberProjectRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	PdirRepository pdirRepository;
	
	@Autowired
	TestData td;

	@Before
	public void setUp() {
//		TestData td = new TestData(
//				memberRepository, memberSystemRepository, 
//				authinfoRepository, careerRepository,
//				memberProjectRepository, projectRepository, 
//				pdirRepository);
		
		td.setMemberTestData();
		
		assertTrue(memberRepository.count() == 4);
		assertTrue(authinfoRepository.count() == 4);
		assertTrue(careerRepository.count() == 4);
		assertTrue(memberSystemRepository.count() == 3);
		
		td.setProjectTestData();
		
		assertTrue(projectRepository.count() == 3);
		assertTrue(memberProjectRepository.count() > 1);
		
		td.setPdirTestData();
		
		assertTrue(pdirRepository.count() > 1);
	}
	
	@Test
	public void test01() {}
	
	@After
	public void clearAll() {
//		TestData td = new TestData(
//				memberRepository, memberSystemRepository, 
//				authinfoRepository, careerRepository,
//				memberProjectRepository, projectRepository, 
//				pdirRepository);
		
		td.deleteAllPdirData();
		
		assertTrue(pdirRepository.count() == 0);
		
		td.deleteAllProjectData();
		
		assertTrue(projectRepository.count() == 0);
		assertTrue(memberProjectRepository.count() == 0);
		
		td.deleteAllMemberData();
		
		assertTrue(memberRepository.count() == 0);
		assertTrue(authinfoRepository.count() == 0);
		assertTrue(careerRepository.count() == 0);
		assertTrue(memberSystemRepository.count() == 0);
	}

}
