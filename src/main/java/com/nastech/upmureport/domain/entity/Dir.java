package com.nastech.upmureport.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dir implements Serializable{
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "dir_id")
	private Integer dirId;
	
	@Column
	private String dirName;
	
	@Column
	private LocalDateTime createDate;
	
	@ManyToOne
	@JoinColumn(name = "proj_id")
	private Project project;
	
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Dir parentDir;
	
	
//	@OneToMany(mappedBy = "dir")
//	private List<Dir> childDirs = new ArrayList<Dir>(); 
}
