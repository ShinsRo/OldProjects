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
	public byte[] downloadAttachment(@PathVariable String attachmentId,HttpServletResponse resp) throws Exception {
		
		LOG.info(resp.getHeaderNames());

//		Resource resource = attachmentService.downloadAttachment(attachmentId);
		return attachmentService.downloadAttachment(attachmentId, resp);
			
		
		//return new FileSystemResource(new File(attachment.getLocalPath())); 
//		// Try to determine file's content type
//        String contentType = null;
//        try {
//            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//        } catch (IOException ex) {
//            LOG.info("Could not determine file type.");
//        }
//
//        // Fallback to the default content type if type could not be determined
//        if(contentType == null) {
//            contentType = "application/octet-stream";
//        }
//        
//        
//        LOG.info("content type: " + MediaType.parseMediaType(contentType));
//        LOG.info("file name : " + resource.getFilename());
//        LOG.info("resource : " + resource);     
        
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType("application/octet-stream"))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                //.header("Access-Control-Expose-Headers", HttpHeaders.CONTENT_DISPOSITION + "," + HttpHeaders.CONTENT_LENGTH)
//                .body(resource);
//        return resource;
    }
	
	
	
	
	@DeleteMapping("/attachment")
	public void deleteAttachment()  {
		
		
	}
	
}