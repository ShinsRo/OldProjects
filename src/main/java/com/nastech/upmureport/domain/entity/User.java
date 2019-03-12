package com.nastech.upmureport.domain.entity;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @Builder @NoArgsConstructor @AllArgsConstructor
public class User {
	@Id
	@GeneratedValue
	private Integer userId;
	private String userName;
	private String userPass;
	
	@ManyToOne
	@JoinColumn(name="deptCode")
	private Dept dept;
	
	@OneToMany(mappedBy = "user")
	private List<UserProject> userProject;
}
