package com.nastech.upmureport.controller;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nastech.upmureport.service.AttachmentService;

import lombok.extern.java.Log;

@RestController 
@Log
@MultipartConfig(maxFileSize = 5120)
public class AttachmentContoller {
	
	AttachmentService attachmentService;
	
	public AttachmentContoller(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	
	@RequestMapping(value = "/attachment", method= RequestMethod.POST, 
	headers = ("content-type=multipart/*"))
	public String addAttachment(@RequestParam("file") MultipartFile file) {
		String fileName = attachmentService.storeFile(file);
		
		return fileName;
	}
	
	@GetMapping("/attachment/{pdirId}")
	public void getAttachment(@PathVariable String pdirId) {
		
		
	}
	
	
	@DeleteMapping("/attachment")
	public void deleteAttachment() {
		
		
	}
}