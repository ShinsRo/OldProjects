package com.nastech.upmureport.domain.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	
	@NotEmpty
	private String pName;
	
	@NotNull
	private String desc;
	
	@Temporal(TemporalType.DATE)
	private LocalDateTime stDate;
	
	@Temporal(TemporalType.DATE)
	private LocalDateTime edDate;
	
	@CreationTimestamp
	private LocalDateTime cDate;
	
	@UpdateTimestamp
	private LocalDateTime uDate;

	@Builder.Default
	private Boolean dFlag = false;
}
