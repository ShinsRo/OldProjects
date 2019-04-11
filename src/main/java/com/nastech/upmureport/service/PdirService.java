package com.nastech.upmureport.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.dto.PdirDto;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.entity.Pdir;
import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.domain.repository.PdirRepository;
import com.nastech.upmureport.support.Utils;

@Service
public class PdirService {
	@Autowired
	private PdirRepository dr;
	
	public List<PdirDto> listByPid(String pid) {
		Project p = Project.builder().pid(Utils.StrToBigInt(pid)).build();
		List<Pdir> dirs = dr.findAllByProjectAndDflagFalse(p);
		
		List<PdirDto> dirDtos = new ArrayList<>();
		for (Pdir dir : dirs) {
			PdirDto pdirDto = new PdirDto(dir);
			
			dirDtos.add(pdirDto);
		}
		return dirDtos;
	}
	
	@Transactional
	public PdirDto register(PdirDto dto) {
		Long mid = Long.valueOf(dto.getMid());
		BigInteger pid = Utils.StrToBigInt(dto.getPid());
		String parentDid = dto.getParentDid();
		
		Member m = Member.builder().mid(mid).build();
		Project p = Project.builder().pid(pid).build();
		
		Pdir parentDir = null;
		if (parentDid.equals("root")) { /* 최상위 루트 디렉토리 ("/")는 수정 불가하므로 익셉션 처리 할 것 */ }
		else { parentDir = dr.findByDidAndDflagFalse(Utils.StrToBigInt(parentDid)); }	
		
		Pdir pdir = Pdir.builder()
				.parentDir(parentDir)
				.member(m)
				.project(p)
				.build();
		
		Utils.overrideEntity(pdir, dto);
		return new PdirDto(dr.save(pdir));
	}
	
	
	public PdirDto correct(PdirDto dto, String gubun) {
		BigInteger did = Utils.StrToBigInt(dto.getDid());
		String parentDid = dto.getParentDid();
		Pdir dir = dr.findByDidAndDflagFalse(did);
		Pdir parentDir = null;
		
		switch (gubun) {
		case "수정":
			Utils.overrideEntity(dir, dto);
		case "이동":			
			if (parentDid.equals("root")) { /* 최상위 루트 디렉토리 ("/")는 수정 불가하므로 익셉션 처리 할 것 */ }
			else { 
				parentDir = dr.findByDidAndDflagFalse(Utils.StrToBigInt(parentDid)); 
				dir.setParentDir(parentDir);
			}
		default:			
			break;
		}
		
		return new PdirDto(dr.save(dir));
	}
	
	public PdirDto disable(PdirDto dto) {
		BigInteger did = Utils.StrToBigInt(dto.getDid());
		Pdir dir = dr.findByDidAndDflagFalse(did);
		dir.setDflag(true);
		return new PdirDto(dr.save(dir));
	}
}
