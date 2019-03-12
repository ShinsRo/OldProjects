package com.nastech.upmureport.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Dir {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long dirId;
	
	@Column
	private String dirName;
	
	@Column
	private LocalDateTime createDate;
	
	@ManyToOne
	@JoinColumn(name = "projId")
	private Project project;
	
	@ManyToOne
	@JoinColumn(name = "parentDirId")
	private Dir parentDir;
	
	@OneToMany(mappedBy = "dir")
	private List<Dir> dir = new ArrayList<Dir>(); 
	
	private Boolean DELETE_FLAG;
}
