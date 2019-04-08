package com.nastech.upmureport.domain.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.entity.MemberProject;
import com.nastech.upmureport.domain.entity.Project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProjectDTO {
	private String pid;
	private String pName;
	private String description;
	private LocalDateTime stDate;
	private LocalDateTime edDate;
	private LocalDateTime cDate;
	private LocalDateTime uDate;
	
	private String mid;
	private String pStat;
	private String pRole;
	private Integer progess;

	public ProjectDTO(MemberProject mp) {
		Project p = mp.getProject();
		Member m = mp.getMember();
		
		this.pid = p.getPid().toString();
		this.pName = p.getPName();
		this.description = p.getDescription();
		this.stDate = p.getStDate();
		this.edDate = p.getEdDate();
		this.cDate = p.getCDate();
		this.uDate = p.getUDate();
		
		this.mid = m.getMid().toString();
		this.pStat = mp.getPStat().toString();
		this.pRole = mp.getPRole().toString();
		this.progess = mp.getProgress();
	}
}
