package com.nastech.upmureport.domain.entity;

import java.time.LocalDateTime;

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
	private LocalDateTime createdDate;
	private LocalDateTime projStartDate;
	private LocalDateTime projEndDate;
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
				.projProgress(projProgress)
				.startYear(Integer.toString(projStartDate.getYear()))
				.startMonth(Integer.toString(projStartDate.getMonthValue()))
				.startDay(Integer.toString(projStartDate.getDayOfMonth()))
				.endYear(Integer.toString(projEndDate.getYear()))
				.endMonth(Integer.toString(projEndDate.getMonthValue()))
				.endDay(Integer.toString(projEndDate.getDayOfMonth()))
				.build();
	}
}
