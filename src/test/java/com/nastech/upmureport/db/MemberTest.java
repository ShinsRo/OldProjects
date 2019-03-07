package com.nastech.upmureport.db;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

import com.nastech.upmureport.config.PersistenceJPAConfig;
import com.nastech.upmureport.config.WebConfig;
import com.nastech.upmureport.jpa.Member;
import com.nastech.upmureport.jpa.MemberRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, PersistenceJPAConfig.class}, loader=AnnotationConfigWebContextLoader.class)
public class MemberTest {

	@Autowired
	MemberRepository memberRepository;
//	
//	@Autowired
//	PlatformTransactionManager platformTransactionManager;
	
	@Test
	public void save() {
		System.out.println(memberRepository);
		memberRepository.save(new Member(1, "test", 32));
//		System.out.println(platformTransactionManager);
		
		assertThat(memberRepository.findAll().get(0).getName(), is("test"));
	}
}
