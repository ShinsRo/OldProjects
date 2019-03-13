package com.nastech.upmureport.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity @Data @NoArgsConstructor @Builder @AllArgsConstructor
public class UpmuContents implements Serializable{
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer upmuId;
	
	@ManyToOne
	@JoinColumn
	private Dir dirId;
	
	private String name;
	
	private String contents;
	
	private LocalDateTime newDate;
	
	private LocalDateTime updateDate;
	
	private String localPath;
	
	private boolean DELETE_FLAG;
}
