package com.nastech.upmureport.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.dto.UpmuReqDto;
import com.nastech.upmureport.domain.entity.Dir;
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
	
	public List<UpmuReqDto> getUpmu(Integer dirId){
		
		Dir dir = dirRepository.findById(dirId).get();
		
		List<UpmuContent> upmuContents = upmuContentRepository.findByDirId(dir);
		
		List<UpmuReqDto> upmuReqDtos = new ArrayList<>();
		
		for(UpmuContent upmuContent : upmuContents) {
			UpmuReqDto upmuReqDto = UpmuReqDto.builder()
										.name(upmuContent.getName())
										.contents(upmuContent.getContents())
										.localPath(upmuContent.getLocalPath())
										.dir(upmuContent.getDirId())
										.build();
			upmuReqDtos.add(upmuReqDto);			
		}
		
		return upmuReqDtos;		
	}
}