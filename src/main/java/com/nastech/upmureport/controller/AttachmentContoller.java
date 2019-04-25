package com.nastech.upmureport.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nastech.upmureport.domain.dto.AttachmentDto;
import com.nastech.upmureport.domain.entity.Attachment;
import com.nastech.upmureport.service.AttachmentService;
import com.nastech.upmureport.support.Utils;


@RestController 
@MultipartConfig(maxFileSize = 5120)
public class AttachmentContoller {
	
	private final AttachmentService attachmentService;
	
	private static final Log LOG = LogFactory.getLog(AttachmentContoller.class);
	
	public AttachmentContoller(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	
	@RequestMapping(value = "/attachment", method= RequestMethod.POST, 
	headers = ("content-type=multipart/*"))
	public AttachmentDto.AttachmentResDto addAttachment(@RequestParam("file") MultipartFile file, @RequestParam String json) throws Exception {
		//ObjectMapper objectMapper = new ObjectMapper();
		//AttachmentDto attachmentDto = objectMapper.readValue(json, AttachmentDto.class);
		LOG.info("======== json: " + json);
		return attachmentService.saveAttachment(file, json);
	}
	
	@GetMapping("/attachment/{pdirId}")
	public List<AttachmentDto.AttachmentResDto> getAttachment(@PathVariable String pdirId) throws MalformedURLException {
		
		return attachmentService.getAttachment(Utils.StrToBigInt(pdirId));
		
	}
	
	@GetMapping("/attachment/download/{attachmentId}")
	public List<String> downloadAttachment(@PathVariable String attachmentId,HttpServletResponse resp) throws Exception {
		
//		LOG.info(resp.getHeaderNames());
//		
//		List<String> resFile = attachmentService.downloadAttachment(attachmentId, resp);
//		
//		LOG.info(resFile);
		
        return attachmentService.downloadAttachment(attachmentId, resp);

		//return AttachmentDto.AttachmentDownDto.builder().file(resFile).build();
    }
	
	@DeleteMapping("/attachment/{attachmentId}")
	public List<AttachmentDto.AttachmentResDto> deleteAttachment(@PathVariable String attachmentId)  {
		
		return attachmentService.deleteAttachment(attachmentId);
		
	}
	
}