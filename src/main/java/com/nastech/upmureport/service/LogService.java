package com.nastech.upmureport.service;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.entity.UpmuContent;
import com.nastech.upmureport.domain.entity.UpmuLog;
import com.nastech.upmureport.domain.repository.UpmuLogRepository;

import lombok.extern.java.Log;


@Service @Log
public class LogService {

	UpmuLogRepository upmuLogRepository;
	
	public LogService(UpmuLogRepository upmuLogRepository) {
		this.upmuLogRepository = upmuLogRepository;
	}
	
	public UpmuLog createUpmuLog(UpmuContent upmuContent, UpmuLog.LogStat logStat) {
		
		log.info("========create upmu log");
		
		UpmuLog upmuLog = UpmuLog.builder()
				.newDate(LocalDateTime.now())
				.userId(upmuContent.getDirId().getUser())
				.upmuId(upmuContent)
				.stat(logStat)
				.build();
		
		return upmuLogRepository.save(upmuLog);
	}		
}
