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
import com.nastech.upmureport.domain.entity.Dept;
import com.nastech.upmureport.domain.entity.Position;
import com.nastech.upmureport.domain.entity.User;
import com.nastech.upmureport.domain.repository.DeptRepository;
import com.nastech.upmureport.domain.repository.PositionRepository;
import com.nastech.upmureport.domain.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, PersistenceJPAConfig.class}, loader=AnnotationConfigWebContextLoader.class)
public class UserDBTest {

	@Autowired
	DeptRepository deptRepository;
	@Autowired
	PositionRepository positionRepository;
	@Autowired
	UserRepository userRepository;
//	
//	@Autowired
//	PlatformTransactionManager platformTransactionManager;
	@Test
	public void save() {
		System.out.println(deptRepository);
		deptRepository.save(Dept.builder().dept_name("deptTest").build());
//		System.out.println(platformTransactionManager);
		assertThat(deptRepository.findAll().get(0).getDept_name(), is("deptTest"));
	}
	
	@Test
	public void save3() {
		System.out.println(positionRepository);
		positionRepository.save(Position.builder().posi_name("posi test").roll_level(1).build());
		assertThat(positionRepository.findAll().get(0).getPosi_name(), is("posi test"));
	}
	@Test
	public void save4() {
		System.out.println(userRepository);
		userRepository.save(User.builder().user_name("User test").build());
		assertThat(userRepository.findAll().get(0).getUser_name(), is("User test"));
	}
	
	
}
