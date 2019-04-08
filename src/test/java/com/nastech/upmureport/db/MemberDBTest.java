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
import com.nastech.upmureport.domain.repository.MemberRepository;
import com.nastech.upmureport.domain.repository.MemberSystemRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, PersistenceJPAConfig.class}, loader=AnnotationConfigWebContextLoader.class)
public class MemberDBTest {
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	MemberSystemRepository memberSystemRepository;
	@Autowired
	AuthInfoRepository authinfoRepository;
	@Autowired
	CareerRepository careerRepository;
	
	@Before
	public void setUp() {
		List<Member> mList = new ArrayList<Member>();
		
		Member admin = Member.builder()
				.eid("0000")
				.mName("관리자")
				.birth("00000000")
				.phoneNum("01011111111")
				.joinDate(LocalDate.now())
				.retireDate(LocalDate.now())
				.build();
		mList.add(admin);
		
		Member m1 = Member.builder()
				.eid("1111")
				.mName("김승신")
				.birth("19940728")
				.phoneNum("01011111111")
				.joinDate(LocalDate.now())
				.retireDate(LocalDate.now())
				.build();
		mList.add(m1);
		
		Member m2 = Member.builder()
				.eid("1112")
				.mName("마규석")
				.birth("19951226")
				.phoneNum("01012345555")
				.joinDate(LocalDate.now())
				.retireDate(LocalDate.now())
				.build();
		mList.add(m2);
		
		Member m3 = Member.builder()
				.eid("1113")
				.mName("김윤상")
				.birth("19940729")
				.phoneNum("01011131111")
				.joinDate(LocalDate.now())
				.retireDate(LocalDate.now())
				.build();
		mList.add(m3);
		
		List<Career> cList = new ArrayList<Career>();
		
		Career c0 = Career.builder()
				.dept("관리").posi("관리").active(true).startDate(LocalDate.now()).member(m1)
				.build();
		Career c1 = Career.builder()
				.dept("인턴부").posi("인턴").active(true).startDate(LocalDate.now()).member(m1)
				.build();
		Career c2 = Career.builder()
				.dept("인턴부").posi("인턴").active(true).startDate(LocalDate.now()).member(m2)
				.build();
		Career c3 = Career.builder()
				.dept("인턴부").posi("인턴").active(true).startDate(LocalDate.now()).member(m3)
				.build();
		
		cList.add(c0);
		cList.add(c1);
		cList.add(c2);
		cList.add(c3);
		
		List<AuthInfo> aList = new ArrayList<AuthInfo>();
		
		AuthInfo a0 = AuthInfo.builder().member(admin).username("admin").password("1111").role(Role.ROLE_ADMIN).build();
		AuthInfo a1 = AuthInfo.builder().member(m1).username("m1111").password("1111").role(Role.ROLE_USER).build();
		AuthInfo a2 = AuthInfo.builder().member(m1).username("m1112").password("1111").role(Role.ROLE_USER).build();
		AuthInfo a3 = AuthInfo.builder().member(m1).username("m1113").password("1111").role(Role.ROLE_USER).build();
		
		aList.add(a0);
		aList.add(a1);
		aList.add(a2);
		aList.add(a3);
		
		List<MemberSystem> msList = new ArrayList<MemberSystem>();
		MemberSystem ms1 = MemberSystem.builder().senior(admin).junior(m1).build();
		MemberSystem ms2 = MemberSystem.builder().senior(admin).junior(m2).build();
		MemberSystem ms3 = MemberSystem.builder().senior(admin).junior(m3).build();
		
		msList.add(ms3);
		msList.add(ms2);
		msList.add(ms1);
		
		
		memberRepository.saveAll(mList);
		authinfoRepository.saveAll(aList);
		careerRepository.saveAll(cList);
		memberSystemRepository.saveAll(msList);

		assertTrue(memberRepository.count() == 4);
		assertTrue(authinfoRepository.count() == 4);
		assertTrue(careerRepository.count() == 4);
		assertTrue(memberSystemRepository.count() == 3);
	}
	
	@Test
	public void test() {}
	
	@After
	public void clear() {
		
	}

}
