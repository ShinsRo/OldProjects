package com.nastech.upmureport.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.dto.LogDto;
import com.nastech.upmureport.domain.entity.Attachment;
import com.nastech.upmureport.domain.entity.AttachmentLog;
import com.nastech.upmureport.domain.entity.Pdir;
import com.nastech.upmureport.domain.entity.Pfile;
import com.nastech.upmureport.domain.entity.PfileLog;
import com.nastech.upmureport.domain.entity.support.LogStat;
import com.nastech.upmureport.domain.repository.AttachmentLogRepository;
import com.nastech.upmureport.domain.repository.PdirRepository;
import com.nastech.upmureport.domain.repository.PfileLogRepository;
import com.nastech.upmureport.support.Utils;


@Service
public class PfileLogService {

	private static final Log log = LogFactory.getLog(PfileLogService.class);
	
	private PfileLogRepository pfileLogRepository;
	private PdirRepository pdirRepository;
	private AttachmentLogRepository attachmentLogRepository;
	
	
	public PfileLogService(PfileLogRepository pfileLogRepository, PdirRepository pdirRepository,
			AttachmentLogRepository attachmentLogRepository) {
		this.pfileLogRepository = pfileLogRepository;
		this.pdirRepository = pdirRepository;
		this.attachmentLogRepository = attachmentLogRepository;
	}
	
	public PfileLog createPfileLog(Pfile pfile, LogStat logStat) {
		
		log.info("========create pfile log");
		
		PfileLog pfileLog = PfileLog.builder()
				.newDate(LocalDateTime.now())
				.pfile(pfile)
				.stat(logStat)
				.name(pfile.getName())
				.contents(pfile.getContents())
				.pdir(pfile.getPdir())
				.build();
		
		return pfileLogRepository.save(pfileLog);		
	}
	
	public AttachmentLog createAttachmentLog(Attachment attachment) {
		
		AttachmentLog attachmentLog = AttachmentLog.builder()
				.attachment(attachment)
				.newDate(LocalDateTime.now())
				.deleteFlag(false)
				.build();
		
		return attachmentLogRepository.save(attachmentLog);
				
		
		
	}
	
	public List<LogDto.PfileLogDto> getPfileLogs(String pdirId) {
		
		Pdir pdir = pdirRepository.findByDidAndDflagFalse(Utils.StrToBigInt(pdirId));
		
		List<PfileLog> pfileLogs = pfileLogRepository.findAllByPdir(pdir);
		
		List<LogDto.PfileLogDto> pfileLogDtos = new ArrayList<LogDto.PfileLogDto>();
		
		pfileLogs.forEach(pfileLog -> pfileLogDtos.add(
					LogDto.PfileLogDto
					.builder()
					.name(pfileLog.getName())
					.contents(pfileLog.getContents())
					.newDate(pfileLog.getNewDate())
					.state(pfileLog.getStat().getValue()).build()				
				
				));
		
		return pfileLogDtos;
	}
	
	
	
	
}