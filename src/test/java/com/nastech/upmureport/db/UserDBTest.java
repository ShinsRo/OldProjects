package com.nastech.upmureport.db;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.nastech.upmureport.config.PersistenceJPAConfig;
import com.nastech.upmureport.config.WebConfig;
import com.nastech.upmureport.domain.entity.EmployeeSystem;
import com.nastech.upmureport.domain.entity.User;
import com.nastech.upmureport.domain.repository.EmployeeSystemRepository;
import com.nastech.upmureport.domain.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, PersistenceJPAConfig.class}, loader=AnnotationConfigWebContextLoader.class)
public class UserDBTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmployeeSystemRepository employeeSystemRepository;
	
	/**
	 * 1. 유저 "마규석" 가입
	 * 2. 유저 "부하" 가입
	 * 3. 유저 "부하"는 "마규석"의 산하로 표기
	 */
	@Test
	public void test() {
		User mk = User.builder()
				.userId("111138")
				.userName("마규석")
				.userPass("1q2w3e4r")
				.build();
		
		User aa = User.builder()
				.userId("111123")
				.userPass("4r3e2w1q")
				.userName("마구석")
				.build();
		
		userRepository.save(mk);
		userRepository.save(aa);
		
		employeeSystemRepository.save(
				EmployeeSystem.builder()
				.senior(mk)
				.junior(aa)
				.build()
		);
		//System.out.println(employeeSystemRepository.findAllByJunior(aa).get(0));
		//assertThat(employeeSystemRepository.findAllByJunior(aa).get(0).getSenior(), is(mk));
	}
}
