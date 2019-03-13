package com.nastech.upmureport.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.nastech.upmureport.domain.entity.LogAttachment.LogAttachmentBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
	
	@Id
	private Integer attachmentNum;
	
	private String name;
	
	private String path;
	
	private String localPath;
	
	private Date newDate;
	
	private double volume;
	
	@ManyToOne
	private Dir dir;
}
