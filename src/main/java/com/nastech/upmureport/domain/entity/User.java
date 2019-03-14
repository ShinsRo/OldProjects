package com.nastech.upmureport.domain.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class User {
	@Id
	@Column(length=32)
	private String userId;
	private String userName;
	private String userPass;
	private String dept;
	private String posi;
	private Boolean deleteFlag;
}	