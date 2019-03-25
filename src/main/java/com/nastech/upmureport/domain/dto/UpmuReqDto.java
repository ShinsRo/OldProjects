package com.nastech.upmureport.domain.dto;

import com.nastech.upmureport.domain.entity.Dir;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class UpmuReqDto {
	
	private String name;
	
	private String contents;
	
	private String localPath;
	
	private Dir dir;

}