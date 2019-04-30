package com.nastech.upmureport.domain.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.nastech.upmureport.domain.entity.support.LogStat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentLog {
	
	
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private BigInteger AttachmentLogId;
		
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="attachment_id")
	private Attachment attachment;
	
	private LocalDateTime newDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pdir_id")
	private Pdir pdir;
	
	@Enumerated(EnumType.STRING)
	private LogStat stat;
	
	private Boolean deleteFlag;
	
//	private String name;
//	
//	private String coment;
//	
//	private String localPath;	
//	
//	private Double volume;
	

}