package com.nastech.upmureport.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nastech.upmureport.controller.UpmuController;
import com.nastech.upmureport.domain.dto.UpmuReqDto;
import com.nastech.upmureport.domain.dto.UpmuResDto;
import com.nastech.upmureport.domain.entity.Dir;
import com.nastech.upmureport.domain.entity.UpmuContent;
import com.nastech.upmureport.domain.entity.UpmuLog.LogStat;
import com.nastech.upmureport.domain.repository.DirRepository;
import com.nastech.upmureport.domain.repository.UpmuContentRepository;

import lombok.extern.java.Log;

@Service
@Log
public class UpmuService {
	
	UpmuContentRepository upmuContentRepository;
	
	DirRepository dirRepository;
	
	LogService logService;
	
	// 생성자로 빈 등록
	public UpmuService(UpmuContentRepository upmuContentRepository, DirRepository dirRepository
			,LogService logService) {
		this.upmuContentRepository = upmuContentRepository;
		this.dirRepository = dirRepository;
		this.logService = logService;
	}
	
	
	// 업무 일지 등록
	public UpmuResDto addUpmuContents(UpmuReqDto upmuReqDto) {
		Dir dir;
		try {		
			dir = dirRepository.findById(upmuReqDto.getDirId()).get();
		} catch(Exception e){
			log.warning(e.getMessage());
			return null;
		}
		
		UpmuContent upmuContent = UpmuContent.builder()
				.dirId(dir)
				.name(upmuReqDto.getName())
				.contents(upmuReqDto.getContents())
				.localPath(upmuReqDto.getLocalPath())
				.newDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now())
				.build();
		
		upmuContent = upmuContentRepository.save(upmuContent);
		
		logService.createUpmuLog(upmuContent, LogStat.CREATE);
			
		try {
			return upmuContentsToUpmuResDto(upmuContent);
		}catch(Exception e){
			e.getMessage();
			return null;
		}
	}
	
	public UpmuResDto updateUpmucontents(UpmuReqDto upmuReqDto) {
		UpmuContent upmuContent = upmuContentRepository.findById(upmuReqDto.getUpmuId()).get();
		
		upmuContent.setName(upmuReqDto.getName());
		upmuContent.setContents(upmuReqDto.getContents());
		upmuContent.setUpdateDate(LocalDateTime.now());
		
		logService.createUpmuLog(upmuContent, LogStat.UPDATE);
		
		return upmuContentsToUpmuResDto(upmuContentRepository.save(upmuContent));
	}
	
	public List<UpmuResDto> getUpmu(Integer dirId){
		
		Dir dir = dirRepository.findById(dirId).get();
		
		List<UpmuContent> upmuContents = upmuContentRepository.findByDirId(dir);
		
		log.info("size ==== " + upmuContents.size());
		
		List<UpmuResDto> upmuResDtos = new ArrayList<>();
		
		for(UpmuContent upmuContent : upmuContents) {
			UpmuResDto upmuResDto = upmuContentsToUpmuResDto(upmuContent);
			upmuResDtos.add(upmuResDto);		
		}
		
		return upmuResDtos;		
	}
	
	public UpmuResDto upmuContentsToUpmuResDto(UpmuContent upmuContents) {
		UpmuResDto upmuResDto = UpmuResDto.builder()
				.upmuId(upmuContents.getUpmuId())
				.name(upmuContents.getName())
				.contents(upmuContents.getContents())
				.localPath(upmuContents.getLocalPath())
				.dirId(upmuContents.getDirId().getDirId())
				.newDate(upmuContents.getNewDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.updateDate(upmuContents.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))										
				.build();
		
		return upmuResDto;
	}
		
}