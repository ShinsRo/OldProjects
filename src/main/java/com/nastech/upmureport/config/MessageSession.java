/** 
 * 클라이언트별 소켓을 식별하는 아이디와 유효 시간을 갖는 객체 정의
 * 
 * 2019.05.22.
 * @author 김승신
 */
package com.nastech.upmureport.config;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

public class MessageSession {
	private static final int VALID_IN = 30;
	
	@Getter @Setter
	private String sessionId;			// 소켓 세션 아이디
	private LocalDateTime validUntil;	// 유효 시간
	
	public MessageSession(String sessionId) {
		this.sessionId = sessionId;
		renew(sessionId);
	}
	
	/**
	 * sessionId와 비교하여 유효시간을 갱신한다.
	 * 
	 * @param sessionId 이 인스턴스가 갖는 sessionId와 비교할 아이디
	 */
	public void renew(String sessionId) {
		if (!sessionId.equals(this.sessionId)) { }
		this.validUntil = LocalDateTime.now().plusMinutes(VALID_IN);
	}
	
	
	/**
	 * 현재 세션아이디가 유효한 지 확인한다.
	 * 
	 * @return 유효한 지에 따라 참/거짓
	 */
	public boolean isExpired() {
		int cmp = LocalDateTime.now().compareTo(this.validUntil);
		
		return cmp > 0;
	}
}