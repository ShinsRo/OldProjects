package com.nastech.upmureport.domain.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class Pdir {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private BigInteger did;
	
	@NotEmpty
	private String dname;
	
	@Nullable
	@ManyToOne(optional = true, fetch=FetchType.LAZY, cascade={ CascadeType.REFRESH, CascadeType.DETACH })
	@JoinColumn(name = "parentDid")
	private Pdir parentDir;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade={ CascadeType.REFRESH })
	@JoinColumn(name = "mid")
	private Member member;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade={ CascadeType.REFRESH }) 
	@JoinColumn(name = "parentPid")
	private Project project;
	
	@CreationTimestamp
	private LocalDateTime cdate;
	
	@UpdateTimestamp
	private LocalDateTime udate;
	
	@Builder.Default
	private Boolean dflag = false;
}