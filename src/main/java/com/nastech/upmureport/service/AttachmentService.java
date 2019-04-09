package com.nastech.upmureport.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nastech.upmureport.domain.repository.AttachmentRepository;

import lombok.extern.java.Log;

@Service
@Log
public class AttachmentService {
	
	AttachmentRepository attachmentRepository;
	
	public AttachmentService(AttachmentRepository attachmentRepository) {
		this.attachmentRepository = attachmentRepository;
	}
	
	public String storeFile(MultipartFile file) {
		
		
		try {
			// Normalize file name
	        //String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			String fileName = file.getOriginalFilename();
			File destinationFile = new File("C:\\Users\\NASTECH\\Desktop" + File.separator + fileName);
			file.transferTo(destinationFile);			
			
			log.info(fileName);
			
			return fileName;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}         
        
        
		return "";
	}
}
