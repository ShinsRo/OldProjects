package com.nastech.upmureport.domain.dto;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class AttachmentDto {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AttachmentReqDto {
		
		private String pdir;
		
	}
	
	
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AttachmentResDto {
		
		private BigInteger attachmentId;
		private String attachmentName;
		private Long volume;
		private LocalDate newDate;		
		private String contentType;
	}
	
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AttachmentDownDto {		
		
		private List<String> file;

	}
}