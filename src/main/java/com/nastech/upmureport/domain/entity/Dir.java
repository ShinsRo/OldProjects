package com.nastech.upmureport.domain.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class Dir {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer dirId;
	
	@Column
	private String dirName;
	
	@ManyToOne(optional = true, fetch=FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;
	
	@ManyToOne(optional = true, fetch=FetchType.LAZY)
	@JoinColumn(name = "parentProjId")
	private Project project;
	
	@ManyToOne(optional = true, fetch=FetchType.LAZY, cascade={ CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "parentDirId", nullable = true)
	private Dir parentDir;
	
//	@OneToMany(mappedBy="parentDir", fetch=FetchType.LAZY)
//	private List<Dir> childDirs;
	
	@Column
	private Boolean deleteFlag;

//	
//	public Dir() {
//		super();
//		this.parentDir = this;
//		this.deleteFlag = false;
//		this.childDirs = new ArrayList<Dir>();
//	}
}