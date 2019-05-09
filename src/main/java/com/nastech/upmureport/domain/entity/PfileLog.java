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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.nastech.upmureport.domain.entity.support.LogStat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity 
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PfileLog {
	
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private BigInteger LogId;
	
	@CreationTimestamp
	private LocalDateTime newDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mId")
	private Member mId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="fId")
	private Pfile pfile;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="dId")
	private Pdir pdir;
	
	private String name;
	
	@Lob
	private String contents;	
	
	@Enumerated(EnumType.STRING)
	private LogStat stat;	

	
}