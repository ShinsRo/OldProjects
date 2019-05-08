package com.nastech.upmureport.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.nastech.upmureport.domain.entity.support.LogStat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


public class LogDto {
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PfileLogDto {
		private String name;
		
		private String contents;
		
		private LocalDateTime newDate;
		
		private LogStat stat;
	}
	
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class AttachmentLogDto {
		private String name;
		
		private String coment;
		
		private LocalDateTime newDate;
		
		private String contentType;
		
		private LogStat stat;
	}
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	public static class PLogDto {
		
		private List<PfileLogDto> pfileLogs;
		
		private List<AttachmentLogDto> attachmentLogs;
	}
	
	

}
