package com.nastech.upmureport.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import com.nastech.upmureport.domain.pk.LogAttachmentPK;
import com.nastech.upmureport.domain.pk.UpmuLogPK;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(LogAttachmentPK.class)
public class LogAttachment {
	
	
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private Integer LogAttachmentId;
		
	@ManyToOne(fetch=FetchType.LAZY)
	private Attachment attachmentNum;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private User userId;
	
	private Date newDate;
		
	private String name;
	
	private String path;
	
	private String localPath;	
	
	private double volume;

}
