package com.nastech.upmureport.domain.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data @Builder @NoArgsConstructor @AllArgsConstructor 
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private BigInteger pid;
	
	private String pname;
	
	private String description;
	
	private LocalDateTime stDate;
	
	private LocalDateTime edDate;
	
	@CreationTimestamp
	private LocalDateTime cdate;
	
	@UpdateTimestamp
	private LocalDateTime udate;

	@Builder.Default
	private Boolean dflag = false;
}
