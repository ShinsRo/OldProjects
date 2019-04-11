package com.nastech.upmureport.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.dto.PfileReqDto;
import com.nastech.upmureport.domain.dto.PfileResDto;
import com.nastech.upmureport.domain.entity.Pdir;
import com.nastech.upmureport.domain.entity.Pfile;
import com.nastech.upmureport.domain.entity.PfileLog.LogStat;
import com.nastech.upmureport.domain.repository.PdirRepository;
import com.nastech.upmureport.domain.repository.PfileLogRepository;
import com.nastech.upmureport.domain.repository.PfileRepository;

import lombok.extern.java.Log;

@Service
@Log
public class PfileService {
	
	PfileRepository pfileRepository;
	
	PdirRepository pdirRepository;
	
	PfileLogService pfileLogService;
	
	// 생성자로 빈 등록
	public PfileService(PfileRepository pfileRepository, PdirRepository pdirRepository
			, PfileLogService pfileLogService) {
		this.pfileRepository = pfileRepository;
		this.pdirRepository = pdirRepository;
		this.pfileLogService = pfileLogService;
	}
	
	
	// 업무 일지 등록
	public PfileResDto addPfile(PfileReqDto pfileReqDto) {
		Pdir pdir;
		try {		
			pdir = pdirRepository.findById(pfileReqDto.getPdirId()).get();
		} catch(Exception e){
			log.warning(e.getMessage());
			return null;
		}
		
		Pfile pfile = Pfile.builder()
				.pdir(pdir)
				.name(pfileReqDto.getName())
				.contents(pfileReqDto.getContents())
				.newDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now())
				.deleteFlag(false)
				.build();
		
		pfile = pfileRepository.save(pfile);
		
		pfileLogService.createPfileLog(pfile, LogStat.CREATE);
			
		try {
			return pfileToPfileResDto(pfile);
		}catch(Exception e){
			e.getMessage();
			return null;
		}
	}
	
	public PfileResDto updatePfile(PfileReqDto pfileReqDto) {
		Pfile pfile = pfileRepository.findById(pfileReqDto.getPdirId()).get();
		
		pfile.changeName(pfileReqDto.getName());
		pfile.changeContents(pfileReqDto.getContents());
		pfile.updateDate();
		
		pfileLogService.createPfileLog(pfile, LogStat.UPDATE);
		
		return pfileToPfileResDto(pfileRepository.save(pfile));
	}
	
	public List<PfileResDto> getPfiles(BigInteger bigInteger){
		
		Pdir pdir = pdirRepository.findById(bigInteger).get();
		
		List<Pfile> pfiles = pfileRepository.findByDirId(pdir);
		
		log.info("size ==== " + pfiles.size());
		
		List<PfileResDto> pfileResDtos = new ArrayList<>();
		
		for(Pfile pfile : pfiles) {
			PfileResDto pfileResDto = pfileToPfileResDto(pfile);
			pfileResDtos.add(pfileResDto);		
		}
		
		return pfileResDtos;
	}
	
	public List<PfileResDto> deletePfile(String pfileId) {
		
		Pfile pfile = pfileRepository.findById(BigInteger.valueOf(Long.parseLong(pfileId))).get();
		
		pfile.deletePfile();
		
		log.info(pfile.toString());
		log.info(pfile.getDeleteFlag() + "");
		
		pfileLogService.createPfileLog(pfile, LogStat.DELETE);
		
		pfileRepository.save(pfile);
		
		return getPfiles(pfile.getPdir().getDid());
		
		//upmuContentRepository.findByDirId(dirId)
	}
	
	public PfileResDto pfileToPfileResDto(Pfile pfile) {
		PfileResDto pfileResDto = PfileResDto.builder()
				.pfileId(pfile.getFId())
				.pdirId(pfile.getPdir().getDid())
				.name(pfile.getName())
				.contents(pfile.getContents())
				.newDate(pfile.getNewDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.updateDate(pfile.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))										
				.build();
		
		return pfileResDto;
	}
		
}