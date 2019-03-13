package com.nastech.upmureport.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.nastech.upmureport.domain.pk.UserProjectPK;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @Builder @NoArgsConstructor @AllArgsConstructor @IdClass(UserProjectPK.class)
public class UserProject {

	@Id
	@ManyToOne(optional = false)
	@JoinColumn(name = "userId")
	private User user;
	
	@Id
	@ManyToOne(optional = false)
	@JoinColumn(name = "projId")
	private Project project;
	
	private ProjStat projStat;
	
	private Boolean DELETE_FLAG;
}
