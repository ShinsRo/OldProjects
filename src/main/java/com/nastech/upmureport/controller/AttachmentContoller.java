package com.nastech.upmureport.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nastech.upmureport.service.AttachmentService;

import lombok.extern.java.Log;

@RestController 
@Log
public class AttachmentContoller {
	
	AttachmentService attachmentService;
	
	public AttachmentContoller(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	
	@PostMapping("/attachment")
	public String addAttachment(@RequestParam("file") MultipartFile file) {
		String fileName = attachmentService.storeFile(file);
		
		return fileName;		
	}
	
}
