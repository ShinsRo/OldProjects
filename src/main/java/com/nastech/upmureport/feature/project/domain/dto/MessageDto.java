package com.nastech.upmureport.feature.project.domain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class MessageDto {
	private String targetSessionId;
	private String sessionId;
	private String transactionId;
	private String content;
	private LocalDateTime validUntil;
	private LocalDateTime timestamp;
}