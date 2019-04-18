package com.nastech.upmureport.domain.dto;

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
		
		private String attachmentName;
		private Long volume;
		private String memberName;
				
	}
	
	
}
