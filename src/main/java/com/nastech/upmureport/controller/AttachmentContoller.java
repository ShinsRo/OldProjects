package com.nastech.upmureport.controller;

import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nastech.upmureport.domain.dto.AttachmentDto;
import com.nastech.upmureport.domain.dto.PfileDto;
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
	public ResponseEntity<Resource> downloadAttachment(@PathVariable String attachmentId,HttpServletResponse resp) throws Exception {
		
       return attachmentService.downloadAttachment(attachmentId, resp);
		
    }
	
	@PostMapping("/download/group")
	public ResponseEntity<byte[]> downloadAttachment(@RequestBody List<String> attachmentIds) throws Exception {
		
       return attachmentService.downloadAttachmentGroup(attachmentIds);

    }

	
	@DeleteMapping("/{attachmentId}")
	public List<AttachmentDto.AttachmentResDto> deleteAttachment(@PathVariable String attachmentId)  {
		
		return attachmentService.deleteAttachment(attachmentId);
		
	}
	
	@PutMapping("/move/{attachmentId}/{pdirId}")
	public List<AttachmentDto.AttachmentResDto> moveAttachment(@PathVariable String attachmentId, @PathVariable String pdirId){
		
		return attachmentService.moveAttachment(attachmentId, pdirId);		
	}
	
	@PutMapping("/copy/{attachmentId}/{pdirId}")
	public AttachmentDto.AttachmentResDto copyAttachment(@PathVariable String attachmentId, @PathVariable String pdirId){
		
		return attachmentService.copyAttachment(attachmentId, pdirId);		
	}
	
}