package com.nastech.upmureport.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler.Builder;
import com.nastech.upmureport.domain.dto.PfileDto;
import com.nastech.upmureport.domain.entity.Pdir;
import com.nastech.upmureport.domain.entity.Pfile;
import com.nastech.upmureport.domain.entity.support.LogState;
import com.nastech.upmureport.domain.repository.PdirRepository;
import com.nastech.upmureport.domain.repository.PfileRepository;
import com.nastech.upmureport.support.Utils;

import lombok.RequiredArgsConstructor;


/*
 * @Author : 김윤상		2019.05.22. 
 * 
 * @Description : 업무 일지 ( Pfile ) CRUD 비즈니스 로직  
 */
@Service
@RequiredArgsConstructor
public class PfileService {
	
	PfileRepository pfileRepository;
	
	PdirRepository pdirRepository;
	
	PLogService pfileLogService;
	
	private static final Log LOG = LogFactory.getLog(PfileService.class);
	
	
	/* 업무 일지 등록 */
	public PfileDto.PfileResDto addPfile(PfileDto.PfileReqDto pfileReqDto) {
		Pdir pdir = pdirRepository.findById(pfileReqDto.getPdirId()).get();		// 소속 디렉토리 조회
		
		Pfile pfile = pfileBuilder(pdir, pfileReqDto);		// 업무 빌드

		pfile = pfileRepository.save(pfile);  // 업무 DB 저장
		
		pfileLogService.createPfileLog(pfile, LogState.CREATE);  // 업무 등록 로그 생성
		
		return pfile2PfileResDto(pfile); // 업무 일지 DTO 변환 후 반환
	}
	
	/* 업무 일지 수정 */
	public PfileDto.PfileResDto updatePfile(PfileDto.PfileReqDto pfileReqDto) {		
		Pfile pfile = pfileRepository.findById(pfileReqDto.getPfileId()).get();   // 수정 할 업무 조회
		
		pfile.changeName(pfileReqDto.getName());   // 업무 이름 변경 
		pfile.changeContents(pfileReqDto.getContents()); // 업무 내용 변경
		pfile.updateDate(); // 수정 날짜 등록
		
		pfileLogService.createPfileLog(pfile, LogState.UPDATE);  // 업무 수정 로그 생성
		
		return pfile2PfileResDto(pfileRepository.save(pfile));  // 업무 DB 수정 -> DTO 변환 -> 반환 
	}
	
	/* 업무 리스트 조회 */
	public List<PfileDto.PfileResDto> getPfiles(BigInteger pdirId){
		
		Pdir pdir = pdirRepository.findById(pdirId).get();   // 해당 디렉토리 조회
		
		List<Pfile> pfiles = pfileRepository.findByDirId(pdir); //  DB에서 디렉토리 별 업무 리스트 조회
		
		List<PfileDto.PfileResDto> pfileResDtos = new ArrayList<>();  // 반환 할 리스트 객체 생성 
		
		pfiles.forEach(pfile -> {  // DB 조회 된 업무 엔티티 리스트를 업무 DTO로 변환 
			PfileDto.PfileResDto pfileResDto = pfile2PfileResDto(pfile);
			pfileResDtos.add(pfileResDto);
		});
		
		return pfileResDtos;  // 업무 DTO 리스트 반환
	}
	
	/* 업무 삭제 */
	public List<PfileDto.PfileResDto> deletePfile(String pfileId) {
		
		Pfile pfile = pfileRepository.findById(Utils.StrToBigInt(pfileId)).get(); // 삭제 할 업무 조회
		pfile.deletePfile(); // 해당 업무의 삭제 플래그 수정
		
		pfileLogService.createPfileLog(pfile, LogState.DELETE);  // 업무 삭제 로그 생성
		
		pfileRepository.save(pfile);  // 변경 된 삭제 플래그 DB 수정
		
		return getPfiles(pfile.getPdir().getDid()); // 삭제 된 후 해당 디렉토리의 업무 리스트 반환
	}
	
	
	/* 업무 이동 */
	public List<PfileDto.PfileResDto> movePfile(String pfileId, String pdirId) {
		
		Pfile pfile = pfileRepository.findById(Utils.StrToBigInt(pfileId)).get(); // 이동 할 업무 조회
		
		Pdir targetPdir = pdirRepository.findByDidAndDflagFalse(Utils.StrToBigInt(pdirId)); // 업무가 이동 될 타겟 디렉토리 조회
	
		Pdir originPdir = pfile.getPdir(); // 업무가 원래 있던 디렉토리 조회
		
		pfile.movePdir(targetPdir); // 해당 업무의 디렉토리 변경
		
		pfileLogService.createPfileLog(pfile, LogState.MOVE); // 업무 이동 로그 생성
		
		pfileRepository.save(pfile); // 업무의 디렉토리 변경 후 DB 저장
		
		return getPfiles(originPdir.getDid()); // 업무가 이동 하고 난 후 기존 디렉토리의 업무 리스트 조회 후 반환
	}
	
	/* 업무 복사 */
	public PfileDto.PfileResDto copyPfile(String pfileId, String targetPdirId) {
		
		Pfile originPfile = pfileRepository.findById(Utils.StrToBigInt(pfileId)).get(); // 복사 될 업무 조회
		
		Pdir targetPdir = pdirRepository.findByDidAndDflagFalse(Utils.StrToBigInt(targetPdirId)); // 업무가 복사 될 디렉토리 조회
		
		Pfile copyPfile = copyPfileBuilder(targetPdir, originPfile); // 업무 복사
		
		pfileLogService.createPfileLog(copyPfile, LogState.COPY); // 업무 복사 로그 생성
		
		return pfile2PfileResDto(pfileRepository.save(copyPfile)); // 업무 복사 후 DB 저장 -> 복사 된 업무 반환
	}
	
	/* pfile -> pfileResDto 변환 */
	private PfileDto.PfileResDto pfile2PfileResDto(Pfile pfile) {
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
	
	/* 업무 빌더 */
	private Pfile pfileBuilder(Pdir pdir, PfileDto.PfileReqDto pfileReqDto ) {
		return Pfile.builder()
				.pdir(pdir)
				.name(pfileReqDto.getName())
				.contents(pfileReqDto.getContents())
				.newDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now())
				.deleteFlag(false)
				.build();	
	}
	
	/* 업무 복사 빌더 */
	private Pfile copyPfileBuilder(Pdir targetPdir, Pfile originPfile) {
		return Pfile.builder().pdir(targetPdir)
				.name(originPfile.getName())
				.contents(originPfile.getContents())
				.deleteFlag(false)
				.build();
	}
}