package com.nastech.upmureport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.nastech.upmureport.service.PfileService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LogTest {
	
	private static final Log LOG = LogFactory.getLog( LogTest.class );
	
	@Autowired
	PfileService pfileService;
	
	@Test
	public void logTest() {
		LOG.info("info log");
		//pfileService.apoTest();
	}	
}