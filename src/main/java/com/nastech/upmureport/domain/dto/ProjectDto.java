package com.nastech.upmureport.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.entity.MemberProject;
import com.nastech.upmureport.domain.entity.Project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
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
	private String pstat;
	private String prole;
	private Integer progress;
	
	private List<PdirDto> dirs;

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
		this.pstat = mp.getPstat().toString();
		this.prole = mp.getProle().toString();
		this.progress = mp.getProgress();
	}
}
