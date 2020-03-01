package com.nastech.upmureport.feature.project.domain.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

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
	
	@Lob @Column(columnDefinition = "text", length = 52428800)
	private String description;
	
	private LocalDateTime stDate;
	private LocalDateTime edDate;
	
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
