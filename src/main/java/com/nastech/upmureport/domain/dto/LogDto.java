package com.nastech.upmureport.domain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LogDto {
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PfileLogDto {
		private String name;
		
		private String contents;
		
		private LocalDateTime newDate;
		
		private String state;
	}

}
