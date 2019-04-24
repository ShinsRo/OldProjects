package com.nastech.upmureport.domain.entity;

import java.math.BigInteger;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity 
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Attachment {
	
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private BigInteger attachmentId;
	
	private String name;
	
	private String url;
	
	private String localPath;
	
	@CreationTimestamp
	private LocalDate newDate;
	
	private Long volume;
	
	private Boolean deleteFlag = false;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pdirId")
	private Pdir dId;
}