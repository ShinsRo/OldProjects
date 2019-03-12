package com.nastech.upmureport.domain.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity @Data @NoArgsConstructor @Builder @AllArgsConstructor
public class File {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String contents;
	
	private Date newDate;
	
	private Date updateDate;
	
	private String localPath;
	
	@ManyToOne
	private Dir dir;
}
