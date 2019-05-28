package com.nastech.upmureport.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.dto.ProjectInfoDto;
import com.nastech.upmureport.domain.dto.ProjectQueryDto;
import com.nastech.upmureport.domain.entity.MemberProject;
import com.nastech.upmureport.domain.repository.ProjectInfoRepository;

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
	
	public List<ProjectInfoDto> getProjectInfosByOps(ProjectQueryDto pqDto) {
		List<ProjectInfoDto> returnDtos = null;
		
		int qOps 			= pqDto.getQueryOps();
		LocalDateTime from	= pqDto.getFrom();
		LocalDateTime to	= pqDto.getTo();
		Pageable pageable 	= PageRequest.of(pqDto.getPage(), pqDto.getSize());
		
		if (OPTIONS.GROUPED.isIn(qOps)) {
			returnDtos = getGroupedProjectInfos(qOps, from, to, pageable);
		} else {
			returnDtos = getProjectInfos(qOps, from, to, pageable);
		}		
		
		return returnDtos;
	}
	
	private List<ProjectInfoDto> getGroupedProjectInfos(
			int qOps, LocalDateTime from, LocalDateTime to, Pageable pageable) {
		
		List<ProjectInfoDto> returnList = new ArrayList<ProjectInfoDto>();
		Page<Map<String, Object>> page = 
				OPTIONS.ON_STDATE.isIn(qOps) ? 
				pir.groupedProjectInfoPageByStDateBetween(from, to, pageable):
				pir.groupedProjectInfoPageByEtDateBetween(from, to, pageable);
		
		page.get().forEach(el -> {
			returnList.add(new ProjectInfoDto(el, qOps));
		});
		
		return returnList;
	}
	
	private List<ProjectInfoDto> getProjectInfos(
			int qOps, LocalDateTime from, LocalDateTime to, Pageable pageable) {
		
		List<ProjectInfoDto> returnList = new ArrayList<ProjectInfoDto>();
		Page<MemberProject> page = 
				OPTIONS.ON_STDATE.isIn(qOps)?
					pir.projectInfoByStDateBetween(from, to, pageable):
					pir.projectInfoByEdDateBetween(from, to, pageable);
		
		page.get().forEach(el -> {
			returnList.add(new ProjectInfoDto(el, qOps));
		});
		
		return returnList;
	}
}
