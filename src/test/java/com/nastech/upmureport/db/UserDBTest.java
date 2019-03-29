package com.nastech.upmureport.db;


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
import com.nastech.upmureport.domain.dto.UserDto;
import com.nastech.upmureport.domain.entity.EmployeeSystem;
import com.nastech.upmureport.domain.entity.User;
import com.nastech.upmureport.domain.repository.EmployeeSystemRepository;
import com.nastech.upmureport.domain.repository.UserRepository;
import com.nastech.upmureport.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, PersistenceJPAConfig.class}, loader=AnnotationConfigWebContextLoader.class)
public class UserDBTest {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService; 

	@Autowired
	EmployeeSystemRepository employeeSystemRepository;
	
	/**
	 * 1. 유저 "마규석" 가입
	 * 2. 유저 "부하" 가입
	 * 3. 유저 "부하"는 "마규석"의 산하로 표기
	 */
	@Test
	public void test() {
		User ktj = User.builder()
				.userId("111138")
				.userName("김팀장")
				.userPass("1q2w3e4r")
				.dept("연구소")
				.posi("팀장")
				.build();
		
		User aa = User.builder()
				.userId("111123")
				.userPass("4r3e2w1q")
				.userName("김사원")
				.dept("연구소")
				.posi("사원")
				.build();
		
		userRepository.save(ktj);
		userRepository.save(aa);
		
		employeeSystemRepository.save(
				EmployeeSystem.builder()
				.senior(ktj)
				.junior(aa)
				.build()
		);
		
		List<UserDto> a=userService.findMyJuniors(ktj.toDto());
		/*/////// senior 기준 밑의 junior들 전부 출력
		Queue<UserDto> q = new LinkedList();
		List<UserDto> a = new LinkedList();
		q.add(ktj.toDto());
		System.out.println(employeeSystemRepository.findAllBySenior(ktj));
		List<EmployeeSystem> temp = employeeSystemRepository.findAllBySenior(ktj);
		while( !(q.isEmpty()) ) {
			temp=employeeSystemRepository.findAllBySenior(q.poll().toEntity());
			for (EmployeeSystem employeeSystem : temp) {
				System.out.println("주니어들");
				System.out.println(employeeSystem.getJunior());
				q.add(employeeSystem.getJunior().toDto());
				a.add(employeeSystem.getJunior().toDto());
			}
		}*/
		System.out.println(a);
		
		
		//System.out.println(employeeSystemRepository.findAllByJunior(aa).get(0));
		//assertThat(employeeSystemRepository.findAllByJunior(aa).get(0).getSenior(), is(mk));
	}
}
