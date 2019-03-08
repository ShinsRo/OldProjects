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
import com.nastech.upmureport.domain.entity.File;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.repository.FileRepository;
import com.nastech.upmureport.domain.repository.MemberRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, PersistenceJPAConfig.class}, loader=AnnotationConfigWebContextLoader.class)
public class MemberTest {

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	FileRepository fileRepository;
//	
//	@Autowired
//	PlatformTransactionManager platformTransactionManager;
	
	@Test
	public void save() {
		System.out.println(memberRepository);
		memberRepository.save(Member.builder().name("test").id(1).age(32).build());
//		System.out.println(platformTransactionManager);
		
		assertThat(memberRepository.findAll().get(0).getName(), is("test"));
	}
	
	@Test
	public void fileSave() {
		File file = File.builder().name("testFile").contents("test file context").localPath("c/c/c/c/c").build();
		fileRepository.save(file);
		
		assertThat(fileRepository.findAll().get(0).getName(), is("testFile"));
	}
}
