package com.nastech.upmureport.domain.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
		
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="attachmentId")
	private Attachment attachmentId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId")
	private Member mId;
	
	private Date newDate;
		
	private String name;
	
	private String path;
	
	private String localPath;	
	
	private double volume;
	
	private boolean deleteFlag;

}
