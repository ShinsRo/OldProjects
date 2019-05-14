package com.nastech.upmureport.domain.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PLogDto {
	
	// 로그 이름 ( 파일 이름 or 첨부파일 이름 or 프로젝트 이름 ...) 
	private String name;
	
	// 로그 내용
	private String contents;
	
	// 생성 일시
	private LocalDateTime newDate;
	
	// 로그 상태  create, update ...
	private String logState;
	
	// 로그 타입 pfile, attachment, project ...
	private String logType;
	
	// 해당 dir 이름
	private String pdirName;
	
	// 해당 dir id
	private BigInteger dId;
	
	// 첨부파일에서 사용
	private String contentType;
	
}