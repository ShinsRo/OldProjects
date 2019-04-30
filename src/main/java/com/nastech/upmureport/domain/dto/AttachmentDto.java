package com.nastech.upmureport.domain.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;
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
		private String coment;
	}
	
	
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AttachmentResDto {
		
		private BigInteger attachmentId;
		private String attachmentName;
		private Long volume;
		private LocalDateTime newDate;		
		private String contentType;
		private String coment;
		
	}
	
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AttachmentDownDto {		
		
		private List<String> file;

	}
}