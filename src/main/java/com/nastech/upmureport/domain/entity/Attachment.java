package com.nastech.upmureport.domain.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Attachment {
	
	@Id
	private BigInteger attachmentNum;
	
	private String name;
	
	private String path;
	
	private String localPath;
	
	private Date newDate;
	
	private Double volume;
	
	private Boolean deleteFlag;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pdirId")
	private Dir pdirId;
}
