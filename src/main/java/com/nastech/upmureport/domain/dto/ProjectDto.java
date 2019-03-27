package com.nastech.upmureport.domain.dto;

import java.time.LocalDateTime;
import java.util.Date;

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
	private Integer projProgress;
	
	private Date startDate;
	private Date endDate;
	
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
