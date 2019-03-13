package com.nastech.upmureport.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class UserProject {
	
	@Id
	@GeneratedValue
	@Column
	private Integer projConnId;
	
	@Column
	private String roll;
	
//	@ManyToOne
//	@JoinColumn(name = "user_id")
//	private User user;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@Builder
	public UserProject(Integer projConnId, String roll, Project project) {
		super();
		this.projConnId = projConnId;
		this.roll = roll;
		this.project = project;
	}
}
