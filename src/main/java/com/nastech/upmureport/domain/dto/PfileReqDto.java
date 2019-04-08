package com.nastech.upmureport.domain.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class PfileReqDto {
	
	private String name;
	
	private String contents;
	
	private String localPath;
	
	private BigInteger pdirId;
	
	private BigInteger pfileId;

}