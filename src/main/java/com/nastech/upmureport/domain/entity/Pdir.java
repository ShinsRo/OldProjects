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
import javax.validation.constraints.NotNull;

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
	private String dName;
	
	@Nullable
	@ManyToOne(fetch=FetchType.LAZY, cascade={ CascadeType.REFRESH, CascadeType.DETACH })
	@JoinColumn(name = "parentDirId")
	private Pdir parentDir;
	
//	@NotNull
//	@ManyToOne(optional = true) @JoinColumn(name = "mid")
//	private Member member;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY, cascade={ CascadeType.REFRESH }) 
	@JoinColumn(name = "parentProjId")
	private Project project;
	
	@CreationTimestamp
	private LocalDateTime cDate;
	
	@UpdateTimestamp
	private LocalDateTime uDate;
	
	@Builder.Default
	private Boolean dFlag = false;
}