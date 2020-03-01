package com.nastech.upmureport.feature.project.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.common.support.Utils;
import com.nastech.upmureport.feature.project.domain.dto.ProjectInfoDto;
import com.nastech.upmureport.feature.project.domain.dto.ProjectQueryDto;
import com.nastech.upmureport.feature.project.domain.entity.MemberProject;
import com.nastech.upmureport.feature.project.repo.MemberProjectRepository;
import com.nastech.upmureport.feature.project.repo.PdirRepository;
import com.nastech.upmureport.feature.project.repo.ProjectInfoRepository;
import com.nastech.upmureport.feature.project.repo.ProjectRepository;

@Service
public class ProjectInfoService {
	public enum OPTIONS {
		ON_STDATE(0x01),
		GROUPED(0x02);
		
		private int mask;
		
		public boolean isIn(int queryOps) {
			return (this.mask & queryOps) != 0;
		}
		
		private OPTIONS(int mask) {
			this.mask = mask;
		}
	}
	
	@Autowired
	ProjectInfoRepository pir;
	@Autowired
	ProjectRepository pr;
	@Autowired
	MemberProjectRepository mpr;
	@Autowired
	PdirRepository pdr;
	
	public List<ProjectInfoDto> getProjectInfosByOps(ProjectQueryDto pqDto) {
		List<ProjectInfoDto> returnDtos = null;
		
		int qOps 			= pqDto.getQueryOps();
		LocalDateTime from	= pqDto.getFrom();
		LocalDateTime to	= pqDto.getTo();
		
		if (OPTIONS.GROUPED.isIn(qOps)) {
			returnDtos = getGroupedProjectInfos(qOps, from, to);
		} else {
			returnDtos = getProjectInfos(qOps, from, to);
		}		
		
		return returnDtos;
	}
	
	private List<ProjectInfoDto> getGroupedProjectInfos(
			int qOps, LocalDateTime from, LocalDateTime to) {
		
		List<ProjectInfoDto> returnList = new ArrayList<ProjectInfoDto>();
		List<Map<String, Object>> rawList = 
				OPTIONS.ON_STDATE.isIn(qOps) ? 
				pir.groupedProjectInfoPageByStDateBetween(from, to):
				pir.groupedProjectInfoPageByEtDateBetween(from, to);
		
		rawList.stream().forEach(el -> {
			returnList.add(new ProjectInfoDto(el, qOps));
		});0
		
		return returnList;
	}
	
	private List<ProjectInfoDto> getProjectInfos(
			int qOps, LocalDateTime from, LocalDateTime to) {
		
		List<ProjectInfoDto> returnList = new ArrayList<ProjectInfoDto>();
		List<MemberProject> rawList = 
				OPTIONS.ON_STDATE.isIn(qOps)?
					pir.projectInfoByStDateBetween(from, to):
					pir.projectInfoByEdDateBetween(from, to);
		
		rawList.stream().forEach(el -> {
			returnList.add(new ProjectInfoDto(el, qOps));
		});
		
		return returnList;
	}

	public void deleteOne(Integer queryOps, String id) {
		BigInteger BigIntId = Utils.StrToBigInt(id);
		if (OPTIONS.GROUPED.isIn(queryOps)) {
			mpr.deleteById(BigIntId);
		} else {
//			pdr.deleteAllByProject(Project.builder().pid(BigIntId).build());
			pr.deleteById(BigIntId);
		}
	}
}
