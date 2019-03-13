package com.nastech.upmureport.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @Builder @NoArgsConstructor @AllArgsConstructor
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

//	@OneToMany(mappedBy = "project")
//	private List<UserProject> userProject;
	
	private Boolean DELETE_FLAG;
}
