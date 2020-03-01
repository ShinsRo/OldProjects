package com.nastech.upmureport.feature.project.domain.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import com.nastech.upmureport.feature.project.domain.enums.Prole;
import com.nastech.upmureport.feature.project.domain.enums.Pstat;
import com.nastech.upmureport.feature.user.domain.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class MemberProject {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private BigInteger mpid;
	
	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	@JoinColumn(name = "mid")
	private Member member;
	
	@ManyToOne(optional = false, fetch=FetchType.LAZY, 
			cascade={ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@JoinColumn(name = "pid")
	private Project project;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private Prole prole = Prole.게스트;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private Pstat pstat = Pstat.대기;
	
	@NotNull
	@Max(100)
	@Min(0)
	@Builder.Default
	private Integer progress = 0;
	
	@Column(updatable=false)
	@CreationTimestamp
	@Builder.Default
	private LocalDateTime cdate = LocalDateTime.now();
	
	@UpdateTimestamp
	@Builder.Default
	private LocalDateTime udate = LocalDateTime.now();

	@Builder.Default
	private Boolean dflag = false;
}
