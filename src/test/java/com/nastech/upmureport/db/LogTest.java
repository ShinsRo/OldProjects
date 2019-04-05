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
import com.nastech.upmureport.domain.dto.PfileReqDto;
import com.nastech.upmureport.domain.repository.PfileRepository;
import com.nastech.upmureport.domain.repository.PfileLogRepository;
import com.nastech.upmureport.service.PfileService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, PersistenceJPAConfig.class}, loader=AnnotationConfigWebContextLoader.class)
public class LogTest {
	
	@Autowired
	PfileRepository upmuContentRepository;
	
	@Autowired
	PfileLogRepository upmuLogRepository;
	
	@Autowired
	PfileService upmuservice;
	
	@Test
	public void logTest() {
				
		PfileReqDto upmuReqDto = PfileReqDto.builder()
				.name("logTest")
				.contents("log test content")
				.localPath("fdfdf")
				.build();
		
		upmuservice.addUpmuContents(upmuReqDto);		
	}
	
	

}
