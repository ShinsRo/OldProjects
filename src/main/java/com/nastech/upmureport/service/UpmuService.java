package com.nastech.upmureport.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.dto.UpmuReqDto;
import com.nastech.upmureport.domain.entity.UpmuContents;
import com.nastech.upmureport.domain.repository.DirRepository;
import com.nastech.upmureport.domain.repository.UpmuContentsRepository;

@Service
public class UpmuService {
	
	UpmuContentsRepository upmuContentsRepository;
	
	DirRepository dirRepository;
	
	public UpmuService(UpmuContentsRepository upmuContentsRepository, DirRepository dirRepository) {
		this.upmuContentsRepository = upmuContentsRepository;
		this.dirRepository = dirRepository;
	}
	
	public UpmuContents addUpmuContents(UpmuReqDto upmuReqDto) {
		UpmuContents upmuContents = UpmuContents.builder()
				.dirId(upmuReqDto.getDir())
				.name(upmuReqDto.getName())
				.contents(upmuReqDto.getContents())
				.localPath(upmuReqDto.getLocalPath())
				.newDate(LocalDateTime.now())
				.build();
		
		try {
			return upmuContentsRepository.save(upmuContents);
		}catch(Exception e){
			e.getMessage();
			return null;
		}
	}
}