package com.nastech.upmureport.domain.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PfileDto {	
	
	@Data 
	@Builder 
	@AllArgsConstructor 
	@NoArgsConstructor
	public static class  PfileReqDto{
		private String name;
		
		private String contents;
		
		private String localPath;
		
		private BigInteger pdirId;
		
		private BigInteger pfileId;
	}
	
	@Data 
	@Builder 
	@AllArgsConstructor 
	@NoArgsConstructor
	public static class  PfileResDto{
	
		private BigInteger pfileId;
		
		private String name;
		
		private String contents;
		
		private String localPath;
		
		private BigInteger pdirId;
		
		private String newDate;
		
		private String updateDate;
	}

}