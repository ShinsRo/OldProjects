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

import lombok.Data;

@Entity
@Data
public class Dir {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long dirId;
	
	@Column
	private String dirName;
	
	@Column
	private LocalDateTime createDate;
	
	@ManyToOne
	@JoinColumn(name = "proj_id")
	private Project project;
	
	@ManyToOne
	@JoinColumn(name = "dir_id")
	private Dir parentDir;
	
//	@OneToMany(mappedBy = "dir")
//	private List<Dir> childDirs = new ArrayList<Dir>(); 
}
