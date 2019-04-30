package com.nastech.upmureport.controller;

import java.net.MalformedURLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nastech.upmureport.service.AttachmentService;
import com.nastech.upmureport.support.Utils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AttachmentControllerTest {

	@Autowired
	AttachmentService attachmentService;
	
	@Test
	public void attachmentTest() throws MalformedURLException {
		
		attachmentService.getAttachment(Utils.StrToBigInt("25"));
		
	}
}