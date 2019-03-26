package com.nastech.upmureport.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

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
@IdClass(UpmuLogPK.class)
public class UpmuLog {
	@Id
	private Date newDate;
	
	@Id @ManyToOne 
	private User userId;
	
	@Id @ManyToOne
	private UpmuContent upmuId;
	
	private String contents;	
	
	private LogStat stat;	
	
}

enum LogStat{
	AUTO, MANUAL
}