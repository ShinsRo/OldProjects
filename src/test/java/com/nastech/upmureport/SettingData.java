package com.nastech.upmureport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nastech.upmureport.feature.user.domain.security.UserService;
import com.nastech.upmureport.feature.user.repo.AuthInfoRepository;
import com.nastech.upmureport.feature.user.repo.CareerRepository;
import com.nastech.upmureport.feature.user.repo.MemberRepository;
import com.nastech.upmureport.feature.user.repo.MemberSystemRepository;
import com.nastech.upmureport.feature.user.repo.UserRoleRepository;
import com.nastech.upmureport.feature.user.service.AuthInfoService;
import com.nastech.upmureport.feature.user.service.CareerService;
import com.nastech.upmureport.feature.user.service.MemberService;
import com.nastech.upmureport.feature.user.service.MemberSystemService;

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


