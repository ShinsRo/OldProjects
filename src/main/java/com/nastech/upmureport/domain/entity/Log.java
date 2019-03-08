package com.nastech.upmureport.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String contents;
	
	private Date newDate;
	
	private String type;
	
	@ManyToOne
	private File file;
}
