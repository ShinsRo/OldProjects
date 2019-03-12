package com.nastech.upmureport.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @Builder @NoArgsConstructor @AllArgsConstructor
@Table(name="posi")
public class Position {
	@Id
	@GeneratedValue
	private Integer posiCode;
	private Integer rollLevel;
	private String posiName;
}