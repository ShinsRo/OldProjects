package com.nastech.upmureport.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.dto.PLogDto;
import com.nastech.upmureport.domain.entity.Attachment;
import com.nastech.upmureport.domain.entity.PLog;
import com.nastech.upmureport.domain.entity.Pfile;
import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.domain.entity.support.LogState;
import com.nastech.upmureport.domain.entity.support.LogType;
import com.nastech.upmureport.domain.repository.PLogRepository;
import com.nastech.upmureport.domain.repository.ProjectRepository;
import com.nastech.upmureport.support.Utils;


@Service
public class PLogService {

	private static final Log LOG = LogFactory.getLog(PLogService.class);
	
	
	private PLogRepository pLogRepository;
	private ProjectRepository projectRepository;

		
	public PLogService(PLogRepository pLogRepository, ProjectRepository projectRepository) {		
		
		this.pLogRepository = pLogRepository;
		this.projectRepository = projectRepository;
		
	}
	
	public PLog createPfileLog(Pfile pfile, LogState logState) {
		
		PLog pLog = PLog.builder()
				.newDate(LocalDateTime.now())
				.pfile(pfile)
				.project(pfile.getPdir().getProject())
				.pdir(pfile.getPdir())
				.name(pfile.getName())
				.contents(pfile.getContents())
				.logType(LogType.PFILE)
				.deleteFlag(Boolean.FALSE)
				.logState(logState)
				.build();
		
		return pLogRepository.save(pLog);		
	}
	
	public PLog createAttachmentLog(Attachment attachment, LogState logState) {
		
		PLog pLog = PLog.builder()
				.newDate(LocalDateTime.now())
				.attachment(attachment)
				.project(attachment.getPdir().getProject())
				.pdir(attachment.getPdir())
				.name(attachment.getName())
				.contents(attachment.getComent())
				.logType(LogType.ATTACHMENT)
				.deleteFlag(Boolean.FALSE)
				.logState(logState)
				.build();
		
		return pLogRepository.save(pLog);						
	}
	
	public List<PLogDto> getPLogs(String projectId) {
		Project project = null;
				
		project = projectRepository.findById(Utils.StrToBigInt(projectId)).get();
		
		List<PLog> pLogs = pLogRepository.findAllByProject(project);
				
		List<PLogDto> pLogDtos = new ArrayList<>();
		
		pLogs.forEach(pLog -> {
			
			PLogDto pLogDto;
			
			if(pLog.getLogType().equals(LogType.ATTACHMENT)) {
				pLogDto = PLogDto.builder()
						.name(pLog.getName())
						.contents(pLog.getContents())
						.newDate(pLog.getNewDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
						.logType(pLog.getLogType().getKey())
						.logState(pLog.getLogState().getKey())
						.pdirName(pLog.getPdir().getDname())
						.dId(pLog.getPdir().getDid())
						.contentType(pLog.getAttachment().getContentType())
						.build();				
			} else {
				pLogDto = PLogDto.builder()
						.name(pLog.getName())
						.contents(pLog.getContents())
						.newDate(pLog.getNewDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
						.logType(pLog.getLogType().getKey())
						.logState(pLog.getLogState().getKey())
						.pdirName(pLog.getPdir().getDname())
						.dId(pLog.getPdir().getDid())
						.build();
			}
			
			pLogDtos.add(pLogDto);				
		});		
		
		return pLogDtos;
	}	
	
}
