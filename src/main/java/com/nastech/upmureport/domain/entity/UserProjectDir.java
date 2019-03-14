package com.nastech.upmureport.domain.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.nastech.upmureport.domain.pk.UserProjectDirPK;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @Builder @NoArgsConstructor @AllArgsConstructor @IdClass(UserProjectDirPK.class)
public class UserProjectDir {
	@Id
	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;
	
	@Id
	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	@JoinColumn(name = "projId")
	private Project project;
	
	@Id
	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	@JoinColumn(name = "dirId")
	private Dir dir;
	
	@Builder.Default
	private Boolean deleteFlag = false;
}
