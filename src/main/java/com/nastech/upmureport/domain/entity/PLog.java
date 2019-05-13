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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import com.nastech.upmureport.domain.entity.support.LogState;
import com.nastech.upmureport.domain.entity.support.LogType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity 
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PLog {
	
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private BigInteger logId;
	
	@CreationTimestamp
	@NotNull
	private LocalDateTime newDate;	
	
	@NotNull
	private String name;
	
	@Lob
	private String contents;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private LogState logState;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private LogType logType;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="dId")
	private Pdir pdir;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pId")
	private Project project;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="fId")
	private Pfile pfile;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="attachment_id")
	private Attachment attachment;
	
	private Boolean deleteFlag;
}
