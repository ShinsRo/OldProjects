package com.nastech.upmureport.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.dto.UpmuReqDto;
import com.nastech.upmureport.domain.entity.UpmuContent;
import com.nastech.upmureport.domain.repository.DirRepository;
import com.nastech.upmureport.domain.repository.UpmuContentRepository;

@Service
public class UpmuService {
	
	UpmuContentRepository upmuContentRepository;
	
	DirRepository dirRepository;
	
	public UpmuService(UpmuContentRepository upmuContentRepository, DirRepository dirRepository) {
		this.upmuContentRepository = upmuContentRepository;
		this.dirRepository = dirRepository;
	}
	
	public UpmuContent addUpmuContents(UpmuReqDto upmuReqDto) {
		UpmuContent upmuContents = UpmuContent.builder()
				.dirId(upmuReqDto.getDir())
				.name(upmuReqDto.getName())
				.contents(upmuReqDto.getContents())
				.localPath(upmuReqDto.getLocalPath())
				.newDate(LocalDateTime.now())
				.build();
		
		try {
			return upmuContentRepository.save(upmuContents);
		}catch(Exception e){
			e.getMessage();
			return null;
		}
	}
}