package com.nastech.upmureport.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.dto.PdirDto;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.entity.MemberProject;
import com.nastech.upmureport.domain.entity.Pdir;
import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.domain.repository.MemberProjectRepository;
import com.nastech.upmureport.domain.repository.MemberRepository;
import com.nastech.upmureport.domain.repository.PdirRepository;
import com.nastech.upmureport.support.Utils;

@Service
public class PdirService {
	@Autowired
	private MemberProjectRepository mpr;
	@Autowired
	private PdirRepository dr;
	
	public List<PdirDto> listByPid(String pid) {
		List<Pdir> dirs = dr.findAllByPidAndDflagFalse(Utils.StrToBigInt(pid));
		
		List<PdirDto> dirDtos = new ArrayList<>();
		for (Pdir dir : dirs) {
			PdirDto pdirDto = new PdirDto(dir);
			
			dirDtos.add(pdirDto);
		}
		return dirDtos;
	}
	
	@Transactional
	public Pdir register(PdirDto dto) {
		BigInteger mid = Utils.StrToBigInt(dto.getMid());
		BigInteger pid = Utils.StrToBigInt(dto.getPid());
		String parentDid = dto.getParentDid();
		
		Member m = Member.builder().mid(mid).build();
		Project p = Project.builder().pid(pid).build();
		MemberProject mp = mpr.findOneByMemberAndProject(m, p);
		
		Pdir parentDir = null;
		if (parentDid.equals("root")) { /* 최상위 루트 디렉토리 ("/")는 수정 불가하므로 익셉션 처리 할 것 */ }
		else { parentDir = dr.findByDidAndDflagFalse(Utils.StrToBigInt(parentDid)); }	
		
		Pdir pdir = Pdir.builder()
				.parentDir(parentDir)
				.member(m)
				.project(p)
				.build();
		
		Utils.overrideEntity(pdir, dto);
		return dr.save(pdir);
	}
	
	
//	public Pdir update(PdirDto dto, String gubun) {
//		BigInteger did = Utils.StrToBigInt(dto.getDid());
//		String parentDir = 
//		Pdir dir = dr.findByDidAndDflagFalse(did);
//		Pdir parentDir = dr.findByDidAndDflagFalse()
//		switch (gubun) {
//		case "이동":
//			
//			
//			break;
//		case "변경":
//		default:			
//			Utils.overrideEntity(dir, dto);
//			break;
//		}
//		
//		return null;
//	}
	
	public void disable(PdirDto dto) {
		BigInteger did = Utils.StrToBigInt(dto.getDid());
		Pdir dir = dr.findByDidAndDflagFalse(did);
		dir.setDflag(true);
		dr.save(dir);
	}
}
