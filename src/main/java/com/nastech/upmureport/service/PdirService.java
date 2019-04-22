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
		
		switch (gubun) {
		case "rename":
			Utils.overrideEntity(dir, dto);
			break;
		case "move":			
			if (parentDid.equals("root")) { /* 최상위 루트 디렉토리 ("/")는 수정 불가하므로 익셉션 처리 할 것 */ }
			else { 
				dir.setParentDir(Pdir.builder().did(Utils.StrToBigInt(parentDid)).build());
				dir.setProject(Project.builder().pid(Utils.StrToBigInt(dto.getPid())).build());
			}
		default:			
			break;
		}
		
		return new PdirDto(dr.save(dir));
	}
	
	public List<PdirDto> disable(PdirDto dto) {
		BigInteger did = Utils.StrToBigInt(dto.getDid());
		List<Pdir> dirs = dr.findAllByDidOrParentDirAndDflagFalse(did, Pdir.builder().did(did).build());
		List<PdirDto> dDtos = new ArrayList<PdirDto>();
		
		dirs.forEach(dir -> { dir.setDflag(true); });		
		dr.saveAll(dirs).forEach(dir -> { dDtos.add(new PdirDto(dir)); });
		
		return dDtos;
	}
}
