package com.nastech.upmureport.domain.entity;

import java.math.BigInteger;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

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
	
	@NotNull
	@Column(unique = true)
	private String name;
	
	@NotNull	
	private String url;
	
	@NotNull 
	@Column(unique = true)
	private String localPath;
	
	@CreationTimestamp @NotNull
	private LocalDate newDate;
	
	@NotNull
	private Long volume;
	
	@NotNull
	private Boolean deleteFlag = false;
	
	private String contentType;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pdirId")
	private Pdir dId;
	
	public void deleteAttachment() {
		this.deleteFlag = true;
	}
}