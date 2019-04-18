package com.nastech.upmureport.service;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nastech.upmureport.domain.dto.AttachmentDto;
import com.nastech.upmureport.domain.entity.Attachment;
import com.nastech.upmureport.domain.entity.Pdir;
import com.nastech.upmureport.domain.repository.AttachmentRepository;
import com.nastech.upmureport.domain.repository.PdirRepository;
import com.nastech.upmureport.support.Utils;

@Service
public class AttachmentService {

	private final String ATTACH_PATH = "C:\\\\Users\\\\NASTECH\\\\Desktop\\\\attachment";
	private final String PREFIX_URL = "/upload/";
	private static final Log LOG = LogFactory.getLog(AttachmentService.class);
	
	AttachmentRepository attachmentRepository;
	PdirRepository pdirRepository;
	
	public AttachmentService(AttachmentRepository attachmentRepository, PdirRepository pdirRepository) {
		this.attachmentRepository = attachmentRepository;
		this.pdirRepository = pdirRepository;
	}
	
	public void saveAttachment(MultipartFile file, String pid) {		
		
		File destinationFile = saveFile(file);	
		String fileName = file.getOriginalFilename();
		Pdir pdir = pdirRepository.findById(Utils.StrToBigInt(pid)).get();
		
		Attachment attachment = buildAttachment(destinationFile, fileName, pdir);
		
		LOG.info("file getSize() : " + file.getSize() ) ;
		LOG.info("destinationFile getLength() : " + destinationFile.length()) ;
		
		attachmentRepository.save(attachment);
		LOG.info("save file : " + attachment.getName());
	}
	
	public List<AttachmentDto.AttachmentResDto> getAttachment(BigInteger pdirId) throws MalformedURLException {
		Pdir pdir = pdirRepository.findById(pdirId).get();
		List<Attachment> attachments = attachmentRepository.findByDid(pdir);
		List<AttachmentDto.AttachmentResDto> attachmentResDtos = attachments2AttachmentResDtos(attachments);
		
		return attachmentResDtos;
	}
	
	private Attachment buildAttachment(File destinationFile, String fileName, Pdir pdir) {
		return Attachment.builder()
				.name(fileName)
				.url(PREFIX_URL + fileName)
				.localPath(destinationFile.getPath())
				.volume(destinationFile.length())
				.dId(pdir)
				.build();
	}
	
	private File saveFile(MultipartFile file) {
		String fileName = "";
		File destinationFile = null;
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
		return destinationFile;
	}
	
	private List<AttachmentDto.AttachmentResDto> attachments2AttachmentResDtos(List<Attachment> attachments){
		List<AttachmentDto.AttachmentResDto> attachmentResDtos = new ArrayList<AttachmentDto.AttachmentResDto>();
		
		attachments.forEach(attachment -> {
			AttachmentDto.AttachmentResDto attachmentResDto = AttachmentDto.AttachmentResDto.builder()
					.attachmentName(attachment.getName())
					.volume(attachment.getVolume())
					.build();
			
			attachmentResDtos.add(attachmentResDto);
		});
		
		return attachmentResDtos;
	}
	
	
}