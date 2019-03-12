package com.nastech.upmureport.domain.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserProjectDirPK implements Serializable {
	private static final long serialVersionUID = 110915459184422098L;
	
	private Long user;
	private Long project;
	private Long dir;
}
