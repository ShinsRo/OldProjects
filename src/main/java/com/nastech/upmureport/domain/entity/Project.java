package com.nastech.upmureport.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer projId;
	
	@Column
	private String projName;
	
	@Column
	private String projCaleGubun;
	
	@Column
	private String projSubject;
	
	@Column
	private String projDesc;
	
	@Column
	private LocalDateTime projStartDate;
	
	@Column
	private LocalDateTime projEndDate;

	@Column
	private Integer projProgress;
	
	@Column
	private ProjStat projStatCode;

	@OneToMany(mappedBy = "project")
	private List<Dir> dirs;

	@OneToMany(mappedBy = "project")
	private List<UserProject> userProject;

	@Builder
	public Project(Integer projId, String projName, String projCaleGubun, String projSubject, String projDesc,
			LocalDateTime projStartDate, LocalDateTime projEndDate, Integer projProgress, ProjStat projStatCode,
			List<Dir> dirs, List<UserProject> userProject) {
		super();
		this.projId = projId;
		this.projName = projName;
		this.projCaleGubun = projCaleGubun;
		this.projSubject = projSubject;
		this.projDesc = projDesc;
		this.projStartDate = projStartDate;
		this.projEndDate = projEndDate;
		this.projProgress = projProgress;
		this.projStatCode = projStatCode;
		this.dirs = dirs;
		this.userProject = userProject;
	} 
}
