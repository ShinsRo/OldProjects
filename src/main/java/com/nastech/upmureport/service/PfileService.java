package com.nastech.upmureport.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.dto.PfileReqDto;
import com.nastech.upmureport.domain.dto.PfileResDto;
import com.nastech.upmureport.domain.entity.Dir;
import com.nastech.upmureport.domain.entity.Pfile;
import com.nastech.upmureport.domain.entity.PfileLog.LogStat;
import com.nastech.upmureport.domain.repository.DirRepository;
import com.nastech.upmureport.domain.repository.PfileRepository;

import lombok.extern.java.Log;

@Service
@Log
public class PfileService {
	
	PfileRepository pfileRepository;
	
	DirRepository dirRepository;
	
	LogService logService;
	
	// 생성자로 빈 등록
	public PfileService(PfileRepository pfileRepository, DirRepository dirRepository
			,LogService logService) {
		this.pfileRepository = pfileRepository;
		this.dirRepository = dirRepository;
		this.logService = logService;
	}
	
	
	// 업무 일지 등록
	public PfileResDto addUpmuContents(PfileReqDto pfileReqDto) {
		Dir dir;
		try {		
			dir = dirRepository.findById(pfileReqDto.getDirId()).get();
		} catch(Exception e){
			log.warning(e.getMessage());
			return null;
		}
		
		Pfile pfile = Pfile.builder()
				.dirId(dir)
				.name(pfileReqDto.getName())
				.contents(pfileReqDto.getContents())
				.localPath(pfileReqDto.getLocalPath())
				.newDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now())
				.deleteFlag(false)
				.build();
		
		pfile = pfileRepository.save(pfile);
		
		logService.createUpmuLog(pfile, LogStat.CREATE);
			
		try {
			return upmuContentsToUpmuResDto(pfile);
		}catch(Exception e){
			e.getMessage();
			return null;
		}
	}
	
	public PfileResDto updateUpmucontents(PfileReqDto pfileReqDto) {
		Pfile pfile = pfileRepository.findById(pfileReqDto.getDirId()).get();
		
		pfile.changeName(pfileReqDto.getName());
		pfile.changeContents(pfileReqDto.getContents());
		pfile.updateDate();
		
		logService.createUpmuLog(pfile, LogStat.UPDATE);
		
		return upmuContentsToUpmuResDto(pfileRepository.save(pfile));
	}
	
	public List<PfileResDto> getUpmu(BigInteger bigInteger){
		
		Dir dir = dirRepository.findById(bigInteger).get();
		
		List<Pfile> pfiles = pfileRepository.findByDirId(dir);
		
		log.info("size ==== " + pfiles.size());
		
		List<PfileResDto> pfileResDtos = new ArrayList<>();
		
		for(Pfile pfile : pfiles) {
			PfileResDto pfileResDto = upmuContentsToUpmuResDto(pfile);
			pfileResDtos.add(pfileResDto);		
		}
		
		return pfileResDtos;
	}
	
	public List<PfileResDto> deleteUpmu(String pfileId) {
		
		Pfile pfile = pfileRepository.findById(BigInteger.valueOf(Long.parseLong(pfileId))).get();
		
		pfile.deleteUpmuContent();
		
		log.info(pfile.toString());
		log.info(pfile.getDeleteFlag() + "");
		
		logService.createUpmuLog(pfile, LogStat.DELETE);
		
		pfileRepository.save(pfile);
		
		return getUpmu(pfile.getDirId().getDirId());
		
		//upmuContentRepository.findByDirId(dirId)
	}
	
	public PfileResDto upmuContentsToUpmuResDto(Pfile pfile) {
		PfileResDto pfileResDto = PfileResDto.builder()
				.dirId(pfile.getDirId().getDirId())
				.name(pfile.getName())
				.contents(pfile.getContents())
				.localPath(pfile.getLocalPath())
				.dirId(pfile.getDirId().getDirId())
				.newDate(pfile.getNewDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.updateDate(pfile.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))										
				.build();
		
		return pfileResDto;
	}
		
}