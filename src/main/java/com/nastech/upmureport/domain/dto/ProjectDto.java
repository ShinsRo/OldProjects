package com.nastech.upmureport.domain.dto;

import java.time.LocalDateTime;

import com.nastech.upmureport.domain.entity.ProjStat;
import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.domain.entity.User;
import com.nastech.upmureport.domain.entity.UserProject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProjectDto {
	private Integer projId;
	private String projName;
	private String projCaleGubun;
	private String projSubject;
	private String projDesc;
	private String startYear, startMonth, startDay;
	private String endYear, endMonth, endDay;
	private Integer projProgress;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	private String userId;
	private String userName;
	private String dept;
	private String posi;
	
	private String projStat;
	
	public ProjectDto(User user) {
		this.userId = user.getUserId();
		this.userName = user.getUserName();
		this.dept = user.getDept();
		this.posi = user.getPosi();
	}
	
	public ProjectDto(User user, Project project) {
		this(user);
		this.projId = project.getProjId();
		this.projName = project.getProjName();
		this.projCaleGubun = project.getProjCaleGubun();
		this.projSubject = project.getProjSubject();
		this.projDesc = project.getProjDesc();
		this.startDate = project.getProjStartDate();
		this.endDate = project.getProjEndDate();
		this.projProgress = project.getProjProgress();
	}
	
	public ProjectDto(UserProject userProject) {
		this(userProject.getUser(), userProject.getProject());
		this.projStat = userProject.getProjStat().name();
	}
	
	public ProjectDto() {
		this.projId = -1;
	}
	
	private LocalDateTime getStartDate() {
		if (startDate != null) return startDate;
		if (startYear == null || startMonth == null || startDay == null) return null;
		LocalDateTime startLdt = 
				LocalDateTime.of(
						Integer.valueOf(startYear.trim()), 
						Integer.valueOf(startMonth.trim()), 
						Integer.valueOf(startDay.trim()), 9, 0);
		
		return startLdt;
	}
	
	private LocalDateTime getEndDate() {
		if (endDate != null) return endDate;
		if (endYear == null || endMonth == null || endDay == null) return null;
		LocalDateTime endLdt = 
				LocalDateTime.of(
						Integer.valueOf(endYear.trim()), 
						Integer.valueOf(endMonth.trim()), 
						Integer.valueOf(endDay.trim()), 18, 0);
		return endLdt;
	}
	
	public Project toEntity() {
		Project project = Project.builder()
				.projName(projName)
				.projCaleGubun(projCaleGubun)
				.projSubject(projSubject)
				.projDesc(projDesc)
				.projStartDate(getStartDate())
				.projEndDate(getEndDate())
				.build();
		
		if (projId != null && projId != 0) project.setProjId(projId);
		
		return project;
	}
}
