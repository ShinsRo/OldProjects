package com.nastech.upmureport.domain.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.nastech.upmureport.domain.entity.support.Prole;
import com.nastech.upmureport.domain.entity.support.Pstat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class MemberProject {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private BigInteger mpid;
	
	@ManyToOne(optional = false, fetch=FetchType.LAZY, cascade={ CascadeType.REFRESH })
	@JoinColumn(name = "mid")
	private Member member;
	
	@ManyToOne(optional = false, fetch=FetchType.LAZY, 
			cascade={ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@JoinColumn(name = "pid")
	private Project project;
	
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private Prole pRole;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Pstat pStat;
	
	@NotNull
	@Max(100)
	@Min(0)
	private Integer progress;
	
	@CreationTimestamp
	private Date cDate;
	
	@UpdateTimestamp
	private Date uDate;

	@Builder.Default
	private Boolean dFlag = false;
}
