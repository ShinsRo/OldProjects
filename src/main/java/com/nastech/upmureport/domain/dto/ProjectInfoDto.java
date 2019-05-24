package com.nastech.upmureport.domain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInfoDto {
	private String mpid;
	
	private String pid;
	private String pname;
	private String description;
	private LocalDateTime stDate;
	private LocalDateTime edDate;
	private LocalDateTime cdate;
	private LocalDateTime udate;
	
	private String mid;
	private String mname;
	private String pstat;
	private String prole;
	private Integer progress;
	
	private Long mcnt;
	private Boolean dflag;
}
