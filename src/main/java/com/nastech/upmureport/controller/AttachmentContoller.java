package com.nastech.upmureport.controller;

import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nastech.upmureport.domain.dto.AttachmentDto;
import com.nastech.upmureport.service.AttachmentService;
import com.nastech.upmureport.support.Utils;


@RestController 
@MultipartConfig(maxFileSize = 5120)
@RequestMapping("/attachment")
public class AttachmentContoller {
	
	private final AttachmentService attachmentService;
	
	private static final Log LOG = LogFactory.getLog(AttachmentContoller.class);
	
	public AttachmentContoller(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	
	@RequestMapping(method= RequestMethod.POST, 
	headers = ("content-type=multipart/*"))
	public AttachmentDto.AttachmentResDto addAttachment(@RequestParam("file") MultipartFile file, @RequestParam String json) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		AttachmentDto.AttachmentReqDto attachmentReqDto = objectMapper.readValue(json, AttachmentDto.AttachmentReqDto.class);
		
		return attachmentService.saveAttachment(file, attachmentReqDto);
	}
	
	@GetMapping("/{pdirId}")
	public List<AttachmentDto.AttachmentResDto> getAttachment(@PathVariable String pdirId) throws MalformedURLException {
		
		return attachmentService.getAttachment(Utils.StrToBigInt(pdirId));
		
	}
	
	@GetMapping("/download/{attachmentId}")
	public List<String> downloadAttachment(@PathVariable String attachmentId,HttpServletResponse resp) throws Exception {
		
//		LOG.info(resp.getHeaderNames());
//		
//		List<String> resFile = attachmentService.downloadAttachment(attachmentId, resp);
//		
//		LOG.info(resFile);
		
        return attachmentService.downloadAttachment(attachmentId, resp);

		//return AttachmentDto.AttachmentDownDto.builder().file(resFile).build();
    }
	
	@DeleteMapping("/{attachmentId}")
	public List<AttachmentDto.AttachmentResDto> deleteAttachment(@PathVariable String attachmentId)  {
		
		return attachmentService.deleteAttachment(attachmentId);
		
	}
	
}