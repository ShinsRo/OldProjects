package com.nastech.upmureport.db;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nastech.upmureport.TestData;
import com.nastech.upmureport.domain.dto.MemberDto;
import com.nastech.upmureport.domain.entity.AuthInfo;
import com.nastech.upmureport.domain.entity.Career;
import com.nastech.upmureport.domain.entity.Member;
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
public class MemberDBTest {
	
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
	
	@Before
	public void setUp() {
		
		userRoleRepository.deleteAll();
		td.deleteAllMemberData();
		//TestData td = new TestData(memberRepository, memberSystemRepository, authinfoRepository, careerRepository);
		td.setMemberTestData();
		
		assertTrue(memberRepository.count() == 4);
		assertTrue(authinfoRepository.count() == 4);
		assertTrue(careerRepository.count() == 4);
		assertTrue(memberSystemRepository.count() == 3);
		
		
	}

	@Test
	public void test01() {
		MemberDto admin = MemberDto.builder()
				.eid("0000")
				.name("관리자")
				.birth("00000000")
				.phoneNum("01011111111")
				.joinDate(LocalDate.now())
				.retireDate(LocalDate.now())
				.build();
		AuthInfo temp = AuthInfo.builder()
				.username("admin").password("1111")
				.build();
		AuthInfo check = authinfoRepository.findAll().get(0);
		Career newCar = Career.builder()
				.dept("연구소").posi("연구팀장")
				.build();
		System.out.println(authinfoRepository.findAll().get(0).getMember());
		System.out.println("auth로 멤버를 찾아라"+authinfoRepository.findOneByUsername("admin"));
		
		//System.out.println("찾아라 ㅁㄴ이ㅏ러ㅣㅏㅁ넒ㄴ"+memberService.searchMemberByName(admin));
		Member a= memberService.searchMemberByEid(admin).toEntity();	
		System.out.println("주니어드르을");
		System.out.println("ㅁㄴ"+memberService.findMyJuniors(a.toDto()));
		System.out.println(as.userLogin(temp));
		cs.careerModify(a, newCar);
		memberService.retireMember(a.toDto());
		
//		UserRole ur = UserRole.builder()
//				.role(Role.ROLE_ADMIN).username("admin")
//				.build();
//		userRoleRepository.save(ur);
//		List<UserRole> url= new ArrayList<UserRole>();
//		url.add(ur);
//		System.out.println("유저롤 "+url);
//		CustomUserDetails u = new CustomUserDetails(check, url);
//		System.out.println("aaa"+u.isAccountNonExpired());
//		
//		securityService.loadUserByUsername("admin");
		
		//Member m1 = memberRepository.findOneByName("마규석");
		//memberSystemService.deleteMemberSystem(a, m1);
		//System.out.println(memberSystemRepository.findAll());
		//memberSystemService.addMemberSystem(a, m1);
		//System.out.println(memberSystemRepository.findAll());
		
	}
	
	@After
	public void clearAll() {
		//TestData td = new TestData(memberRepository, memberSystemRepository, authinfoRepository, careerRepository);
		
//		td.deleteAllMemberData();
//		
//		assertTrue(memberRepository.count() == 0);
//		assertTrue(authinfoRepository.count() == 0);
//		assertTrue(careerRepository.count() == 0);
//		assertTrue(memberSystemRepository.count() == 0);
	}

}
