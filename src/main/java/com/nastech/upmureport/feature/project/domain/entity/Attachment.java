package com.nastech.upmureport.feature.project.domain.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity 
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Attachment {
	
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private BigInteger attachmentId;
	
	@NotNull
	private String name;
	
	private String url;
	
	@NotNull 
	private String localPath;
	
	@CreationTimestamp @NotNull
	private LocalDateTime newDate;
	
	@NotNull
	private Long volume;
	
	@NotNull
	private Boolean deleteFlag;
	
	private String contentType;
	
	@Lob
	private String coment;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pdir_id")
	private Pdir pdir;
	
	public void deleteAttachment() {
		this.deleteFlag = true;
	}
	
	public void moveAttachment(Pdir pdir) {
		this.pdir = pdir;
	}
}