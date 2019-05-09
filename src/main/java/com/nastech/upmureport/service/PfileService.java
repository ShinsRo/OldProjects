package com.nastech.upmureport.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.dto.PfileDto;
import com.nastech.upmureport.domain.entity.Pdir;
import com.nastech.upmureport.domain.entity.Pfile;
import com.nastech.upmureport.domain.entity.support.LogState;
import com.nastech.upmureport.domain.repository.PdirRepository;
import com.nastech.upmureport.domain.repository.PfileRepository;
import com.nastech.upmureport.support.Utils;

@Service
public class PfileService {
	
	PfileRepository pfileRepository;
	
	PdirRepository pdirRepository;
	
	PLogService pfileLogService;
	
	private static final Log LOG = LogFactory.getLog(PfileService.class);
	
	// 생성자로 빈 등록
	public PfileService(PfileRepository pfileRepository, PdirRepository pdirRepository
			, PLogService pfileLogService) {
		this.pfileRepository = pfileRepository;
		this.pdirRepository = pdirRepository;
		this.pfileLogService = pfileLogService;
	}
	
	
	// 업무 일지 등록
	public PfileDto.PfileResDto addPfile(PfileDto.PfileReqDto pfileReqDto) {
		Pdir pdir;
		try {		
			pdir = pdirRepository.findById(pfileReqDto.getPdirId()).get();
		} catch(Exception e){
			LOG.warn(e.getMessage());
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
		
		try {			
			pfile = pfileRepository.save(pfile);
			
			pfileLogService.createPfileLog(pfile, LogState.CREATE);
			
			return pfile2PfileResDto(pfile);
		}catch(Exception e){
			e.getMessage();
			return null;
		}
	}
	
	public PfileDto.PfileResDto updatePfile(PfileDto.PfileReqDto pfileReqDto) {
		LOG.info("updatePfile pfileId-----" +  pfileReqDto.getPfileId());
		
		Pfile pfile = pfileRepository.findById(pfileReqDto.getPfileId()).get();
		
		LOG.info("updatePfile -----" +  pfile);
		
		pfile.changeName(pfileReqDto.getName());
		pfile.changeContents(pfileReqDto.getContents());
		pfile.updateDate();
		
		pfileLogService.createPfileLog(pfile, LogState.UPDATE);
		
		return pfile2PfileResDto(pfileRepository.save(pfile));
	}
	
	public List<PfileDto.PfileResDto> getPfiles(BigInteger pdirId){
		
		Pdir pdir = pdirRepository.findById(pdirId).get();
		
		List<Pfile> pfiles = pfileRepository.findByDirId(pdir);
		
		LOG.info("size ==== " + pfiles.size());
		
		List<PfileDto.PfileResDto> pfileResDtos = new ArrayList<>();
		
		pfiles.forEach(pfile -> {
			PfileDto.PfileResDto pfileResDto = pfile2PfileResDto(pfile);
			pfileResDtos.add(pfileResDto);
		});
		
//		for(Pfile pfile : pfiles) {
//			PfileResDto pfileResDto = pfile2PfileResDto(pfile);
//			pfileResDtos.add(pfileResDto);		
//		}
		
		return pfileResDtos;
	}
	
	
	public List<PfileDto.PfileResDto> deletePfile(String pfileId) {
		
		//Pfile pfile = pfileRepository.findById(BigInteger.valueOf(Long.parseLong(pfileId))).get();
		
		Pfile pfile = pfileRepository.findById(Utils.StrToBigInt(pfileId)).get();
		pfile.deletePfile();
		
		LOG.info(pfile.toString());
		LOG.info(pfile.getDeleteFlag() + "");
		
		pfileLogService.createPfileLog(pfile, LogState.DELETE);
		
		pfileRepository.save(pfile);
		
		return getPfiles(pfile.getPdir().getDid());
		
		//upmuContentRepository.findByDirId(dirId)
	}
	
	public PfileDto.PfileResDto pfile2PfileResDto(Pfile pfile) {
		PfileDto.PfileResDto pfileResDto = PfileDto.PfileResDto.builder()
				.pfileId(pfile.getFId())
				.pdirId(pfile.getPdir().getDid())
				.name(pfile.getName())
				.contents(pfile.getContents())
				.newDate(pfile.getNewDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.updateDate(pfile.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))										
				.build();
		
		return pfileResDto;
	}
	
	
	public void apoTest() {
		LOG.info("test====================");
	}
		
}