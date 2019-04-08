package com.nastech.upmureport.service;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.nastech.upmureport.TestData;
import com.nastech.upmureport.config.PersistenceJPAConfig;
import com.nastech.upmureport.config.WebConfig;
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
public class ProjectServiceTest {
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
	
	@Before
	public void setUp() {
		TestData td = new TestData(
				memberRepository, memberSystemRepository, 
				authinfoRepository, careerRepository, 
				memberProjectRepository, projectRepository, 
				pdirRepository);
		
		td.setMemberTestData();
		td.setProjectTestData();
		td.setPdirTestData();
	}
	
	@Test
	@Transactional
	public void test01() {}
	
	@After
	public void clearAll() {
		TestData td = new TestData(
				memberRepository, memberSystemRepository, 
				authinfoRepository, careerRepository, 
				memberProjectRepository, projectRepository, 
				pdirRepository);
		
		td.deleteAllPdirData();
		td.deleteAllProjectData();
		td.deleteAllMemberData();
	}
}
