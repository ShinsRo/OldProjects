package com.nastech.upmureport.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.nastech.upmureport.domain.dto.ProjectDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data @Builder @NoArgsConstructor @AllArgsConstructor 
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer projId;
	
	private String projName;
	private String projCaleGubun;
	private String projSubject;
	private String projDesc;
	private Date createdDate;
	private Date projStartDate;
	private Date projEndDate;
	private Integer projProgress;

	@Builder.Default
	private Boolean deleteFlag = false;
	
	public ProjectDto toDto() {
		return ProjectDto.builder()
				.projId(projId)
				.projName(projName)
				.projCaleGubun(projCaleGubun)
				.projSubject(projSubject)
				.projDesc(projDesc)
				.startDate(projStartDate)
				.endDate(projEndDate)
				.projProgress(projProgress)
				.build();
	}
}
