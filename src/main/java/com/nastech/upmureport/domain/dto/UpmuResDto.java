package com.nastech.upmureport.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.nastech.upmureport.domain.dto.UpmuReqDto.UpmuReqDtoBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class UpmuResDto {
	
	private String name;
	
	private String contents;
	
	private String localPath;
	
	private Integer dirId;
	
	private String newDate;
	
	private String updateDate;

}
