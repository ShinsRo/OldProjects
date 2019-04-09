package com.nastech.upmureport.domain.dto;

import java.time.LocalDateTime;

import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.entity.MemberProject;
import com.nastech.upmureport.domain.entity.Project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProjectDto {
	private String pid;
	private String pname;
	private String description;
	private LocalDateTime stDate;
	private LocalDateTime edDate;
	private LocalDateTime cdate;
	private LocalDateTime udate;
	
	private String mid;
	private String pStat;
	private String pRole;
	private Integer progess;

	public ProjectDto(MemberProject mp) {
		Project p = mp.getProject();
		Member m = mp.getMember();
		
		this.pid = p.getPid().toString();
		this.pname = p.getPname();
		this.description = p.getDescription();
		this.stDate = p.getStDate();
		this.edDate = p.getEdDate();
		this.cdate = p.getCdate();
		this.udate = p.getUdate();
		
		this.mid = m.getMid().toString();
		this.pStat = mp.getPstat().toString();
		this.pRole = mp.getProle().toString();
		this.progess = mp.getProgress();
	}
}
