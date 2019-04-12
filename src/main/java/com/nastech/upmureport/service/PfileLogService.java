package com.nastech.upmureport.service;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.entity.Pfile;
import com.nastech.upmureport.domain.entity.PfileLog;
import com.nastech.upmureport.domain.repository.PfileLogRepository;

import lombok.extern.java.Log;


@Service @Log
public class PfileLogService {

	PfileLogRepository pfileLogRepository;
	
	public PfileLogService(PfileLogRepository pfileLogRepository) {
		this.pfileLogRepository = pfileLogRepository;
	}
	
	public PfileLog createPfileLog(Pfile pfile, PfileLog.LogStat logStat) {
		
		log.info("========create upmu log");
		
		PfileLog pfileLog = PfileLog.builder()
				.newDate(LocalDateTime.now())
				.pfile(pfile)
				.stat(logStat)
				.build();
		
		return pfileLogRepository.save(pfileLog);
	}
}