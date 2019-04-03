package com.nastech.upmureport.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.nastech.upmureport.config.PersistenceJPAConfig;
import com.nastech.upmureport.config.WebConfig;
import com.nastech.upmureport.domain.dto.UpmuReqDto;
import com.nastech.upmureport.domain.repository.UpmuContentRepository;
import com.nastech.upmureport.domain.repository.UpmuLogRepository;
import com.nastech.upmureport.service.UpmuService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, PersistenceJPAConfig.class}, loader=AnnotationConfigWebContextLoader.class)
public class LogTest {
	
	@Autowired
	UpmuContentRepository upmuContentRepository;
	
	@Autowired
	UpmuLogRepository upmuLogRepository;
	
	@Autowired
	UpmuService upmuservice;
	
	@Test
	public void logTest() {
				
		UpmuReqDto upmuReqDto = UpmuReqDto.builder()
				.name("logTest")
				.contents("log test content")
				.localPath("fdfdf")
				.build();
		
		upmuservice.addUpmuContents(upmuReqDto);		
	}
	
	

}
