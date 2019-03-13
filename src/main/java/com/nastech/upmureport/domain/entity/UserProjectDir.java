package com.nastech.upmureport.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @Builder @NoArgsConstructor @AllArgsConstructor @IdClass(UserProjectDirPK.class)
public class UserProjectDir {
	@Id
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "projId")
	private Project project;
	
	@Id
	@ManyToOne
	private Dir dir;
	
	private Boolean deleteFlag;
}
