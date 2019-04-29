package com.nastech.upmureport.db;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nastech.upmureport.TestData;
import com.nastech.upmureport.domain.repository.AuthInfoRepository;
import com.nastech.upmureport.domain.repository.CareerRepository;
import com.nastech.upmureport.domain.repository.MemberProjectRepository;
import com.nastech.upmureport.domain.repository.MemberRepository;
import com.nastech.upmureport.domain.repository.MemberSystemRepository;
import com.nastech.upmureport.domain.repository.PdirRepository;
import com.nastech.upmureport.domain.repository.ProjectRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
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
		td.deleteAllPdirData();
		td.deleteAllProjectData();
		td.deleteAllMemberData();
		
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
	
//	@After
	public void clearAll() {
		
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
