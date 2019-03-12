package com.nastech.upmureport.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity @Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Dept {
	@Id
	@GeneratedValue
	private Integer deptCode;
	private String deptName;
}
