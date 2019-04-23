package com.nastech.upmureport.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class CollaboratorDto {
	private String mid;
	private String name;
	private String prole;
}
