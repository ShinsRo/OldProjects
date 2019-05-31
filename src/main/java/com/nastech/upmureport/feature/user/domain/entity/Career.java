package com.nastech.upmureport.feature.user.domain.entity;

import java.math.BigInteger;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class Career {
	
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private BigInteger cid;
	private String dept;
	private String posi;
	private LocalDate startDate;
	private LocalDate endDate;
	private Boolean active;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name="mid")
	@JsonBackReference
	private Member member;
}
