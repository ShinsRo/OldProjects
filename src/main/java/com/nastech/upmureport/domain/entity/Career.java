package com.nastech.upmureport.domain.entity;

import java.math.BigInteger;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Career {
	
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private BigInteger cid;
	private String dept;
	private String posi;
	private LocalDate startDate;
	private LocalDate endDate;
	private Boolean active;
	private BigInteger mid;
	
}
