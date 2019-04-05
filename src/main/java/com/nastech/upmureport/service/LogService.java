//package com.nastech.upmureport.service;
//
//import java.time.LocalDateTime;
//import org.springframework.stereotype.Service;
//
//import com.nastech.upmureport.domain.entity.Pfile;
//import com.nastech.upmureport.domain.entity.PfileLog;
//import com.nastech.upmureport.domain.repository.PfileLogRepository;
//
//import lombok.extern.java.Log;
//
//
//@Service @Log
//public class LogService {
//
//	PfileLogRepository pfileLogRepository;
//	
//	public LogService(PfileLogRepository pfileLogRepository) {
//		this.pfileLogRepository = pfileLogRepository;
//	}
//	
//	public PfileLog createUpmuLog(Pfile pfile, PfileLog.LogStat logStat) {
//		
//		log.info("========create upmu log");
//		
//		PfileLog pfileLog = PfileLog.builder()
//				.newDate(LocalDateTime.now())
//				.userId(pfile.getDirId().getUser())
//				.pfileId(pfile)
//				.stat(logStat)
//				.build();
//		
//		return pfileLogRepository.save(pfileLog);
//	}		
//}
