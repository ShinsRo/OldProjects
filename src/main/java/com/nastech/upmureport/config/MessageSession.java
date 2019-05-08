package com.nastech.upmureport.config;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

public class MessageSession {
	@Getter @Setter
	private String sessionId;
	
	private LocalDateTime validUntil;
	
	
	
	public void renew(String sessionId) {
		if (!sessionId.equals(this.sessionId)) { }
		this.validUntil = LocalDateTime.now().plusMinutes(30);
	}
	
	public boolean isExpired() {
		int cmp = LocalDateTime.now().compareTo(this.validUntil);
		
		return cmp > 0;
	}

	public MessageSession(String sessionId) {
		this.sessionId = sessionId;
		renew(sessionId);
	}
}