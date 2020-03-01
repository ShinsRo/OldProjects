package com.nastech.upmureport.feature.project.domain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectQueryDto {
	private String queryOps;
	
	private LocalDateTime from;
	private LocalDateTime to;
	
	public int getQueryOps() {
		return Integer.parseInt(this.queryOps, 2);
	}
}
