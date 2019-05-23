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

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PLogService {

	private static final Log LOG = LogFactory.getLog(PLogService.class);
	
	private final PLogRepository pLogRepository;
	private final ProjectRepository projectRepository;
	
	/* 업무 상태 별 로그 생성 */
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
	
	/* 첨부파일 상태 별  로그 생성 */
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
	
	/* 프로젝트 별 로그 리스트 조회 */
	public List<PLogDto> getPLogs(String projectId) {
		
		LOG.info(projectRepository);
		
		Project project = projectRepository.findById(Utils.StrToBigInt(projectId)).get(); // 로그 리스트를 조회 할 프로젝트 조회 
		
		List<PLog> pLogs = pLogRepository.findAllByProject(project); // 해당 프로젝트의 로그 리스트 조회
				
		List<PLogDto> pLogDtos = new ArrayList<>(); // 로그 DTO 객체 생성
		
		pLogs.forEach(pLog -> {
			
			PLogDto pLogDto;
			
			if(pLog.getLogType().equals(LogType.ATTACHMENT)) { // 첨부파일에 대한 로그 일 경우
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
			} else { // 업무에 대한 로그 일 경우
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
			
			pLogDtos.add(pLogDto); // 리스트에 추가
		});		
		
		return pLogDtos; // 반환
	}	
	
}
