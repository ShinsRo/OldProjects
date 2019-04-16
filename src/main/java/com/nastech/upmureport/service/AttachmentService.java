package com.nastech.upmureport.service;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nastech.upmureport.domain.entity.Attachment;
import com.nastech.upmureport.domain.entity.Pdir;
import com.nastech.upmureport.domain.repository.AttachmentRepository;
import com.nastech.upmureport.domain.repository.PdirRepository;

@Service
public class AttachmentService {

	private final String ATTACH_PATH = "C:\\\\Users\\\\NASTECH\\\\Desktop\\\\attachment";
	private static final Log LOG = LogFactory.getLog(AttachmentService.class);
	
	AttachmentRepository attachmentRepository;
	PdirRepository pdirRepository;
	
	public AttachmentService(AttachmentRepository attachmentRepository, PdirRepository pdirRepository) {
		this.attachmentRepository = attachmentRepository;
		this.pdirRepository = pdirRepository;
	}
	
	public void storeAttachment(MultipartFile file) {		
		
		String fileName = "";
		File destinationFile;
		try {
			fileName = file.getOriginalFilename();
			destinationFile = new File(ATTACH_PATH + File.separator + fileName);
			file.transferTo(destinationFile);		
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Attachment attachment = Attachment.builder().
		//LOG.info(fileName);
	}
	
	public void getAttachment(BigInteger pdirId) {
		Pdir pdir = pdirRepository.findById(pdirId).get();
		List<Attachment> attachments = attachmentRepository.findByDid(pdir);
		//LOG.info(attachments.get(0).getPath()());
	}
}