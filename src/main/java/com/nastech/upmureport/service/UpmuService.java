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
import com.nastech.upmureport.domain.repository.DirRepository;
import com.nastech.upmureport.domain.repository.UpmuContentRepository;

import lombok.extern.java.Log;

@Service
@Log
public class UpmuService {
	
	UpmuContentRepository upmuContentRepository;
	
	DirRepository dirRepository;
	
	public UpmuService(UpmuContentRepository upmuContentRepository, DirRepository dirRepository) {
		this.upmuContentRepository = upmuContentRepository;
		this.dirRepository = dirRepository;
	}
	
	public UpmuResDto addUpmuContents(UpmuReqDto upmuReqDto) {
		Dir dir = dirRepository.findById(upmuReqDto.getDirId()).get();
		
		UpmuContent upmuContents = UpmuContent.builder()
				.dirId(dir)
				.name(upmuReqDto.getName())
				.contents(upmuReqDto.getContents())
				.localPath(upmuReqDto.getLocalPath())
				.newDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now())
				.build();
		
		upmuContents = upmuContentRepository.save(upmuContents);
		
		UpmuResDto upmuResDto = UpmuResDto.builder()
				.name(upmuContents.getName())
				.contents(upmuContents.getContents())
				.localPath(upmuContents.getLocalPath())
				.dirId(upmuContents.getDirId().getDirId())
				.newDate(upmuContents.getNewDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.updateDate(upmuContents.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))										
				.build();
		
		try {
			return upmuResDto;
		}catch(Exception e){
			e.getMessage();
			return null;
		}
	}
	
	public List<UpmuResDto> getUpmu(Integer dirId){
		
		Dir dir = dirRepository.findById(dirId).get();
		
		List<UpmuContent> upmuContents = upmuContentRepository.findByDirId(dir);
		
		log.info("size ==== " + upmuContents.size());
		
		List<UpmuResDto> upmuResDtos = new ArrayList<>();
		
		for(UpmuContent upmuContent : upmuContents) {
			UpmuResDto upmuResDto = UpmuResDto.builder()
										.name(upmuContent.getName())
										.contents(upmuContent.getContents())
										.localPath(upmuContent.getLocalPath())
										.dirId(upmuContent.getDirId().getDirId())
										.newDate(upmuContent.getNewDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
										.updateDate(upmuContent.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))										
										.build();
			upmuResDtos.add(upmuResDto);		
			
			log.info(upmuContent.getNewDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		}
		
		return upmuResDtos;		
	}
}