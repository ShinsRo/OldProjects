package com.nastech.upmureport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nastech.upmureport.domain.repository.AuthInfoRepository;
import com.nastech.upmureport.domain.repository.CareerRepository;
import com.nastech.upmureport.domain.repository.MemberRepository;
import com.nastech.upmureport.domain.repository.MemberSystemRepository;
import com.nastech.upmureport.domain.repository.UserRoleRepository;
import com.nastech.upmureport.domain.security.UserService;
import com.nastech.upmureport.service.AuthInfoService;
import com.nastech.upmureport.service.CareerService;
import com.nastech.upmureport.service.MemberService;
import com.nastech.upmureport.service.MemberSystemService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SettingData {
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	MemberSystemRepository memberSystemRepository;
	@Autowired
	AuthInfoRepository authinfoRepository;
	@Autowired
	CareerRepository careerRepository;
	@Autowired
	UserRoleRepository userRoleRepository;
	@Autowired
	TestData td;

	@Autowired
	MemberService memberService;
	@Autowired
	MemberSystemService memberSystemService;
	@Autowired
	AuthInfoService as;
	@Autowired
	CareerService cs;
	@Autowired
	UserService securityService;
	
	@Test
	public void setUp() {
		
//		td.deleteAllMemberData();
//		td.deleteAllPdirData();
//		td.deleteAllProjectData();
		//TestData td = new TestData(memberRepository, memberSystemRepository, authinfoRepository, careerRepository);
		td.setMemberTestData();
		td.setProjectTestData();
		td.setPdirTestData();
		td.setPfileTestData();		
	}
}


