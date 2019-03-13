package com.nastech.upmureport.domain.dto;

import com.nastech.upmureport.domain.entity.Dir;

import lombok.Data;

@Data
public class UpmuReqDto {
	
	private String name;
	
	private String contents;
	
	private String localPath;
	
	private Dir dir;

}