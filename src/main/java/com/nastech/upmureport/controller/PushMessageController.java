/** 
 * 앱 소켓 목적지 매핑과 제어 정의
 * 
 * 2019.05.22.
 * @file PushMessageController 정의
 * @author 김승신
 */
package com.nastech.upmureport.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.nastech.upmureport.config.MessageSession;
import com.nastech.upmureport.config.MessageSessionContainer;
import com.nastech.upmureport.domain.dto.MessageDto;

@Controller
public class PushMessageController {
	Log Logger = LogFactory.getLog(PushMessageController.class);
	
	@Autowired
	private MessageSessionContainer messageSessionIdsContainer;
	
	/**
	 * 접속한 클라이언트로부터 소켓 등록 메세지를 처리한다.
	 * 
 	 * @author 김승신	2019.05.21.
	 * @param mid			클라이언트 사원 식별 아이디
	 * @param message		메세지
	 * @param sha			소켓 메세지 헤더
	 * @throws Exception
	 */
	@MessageMapping("register.{mid}")
	public void register(@DestinationVariable String mid, MessageDto message, SimpMessageHeaderAccessor sha) throws Exception {
		Logger.info(String.format("[MID %s] 프로젝트 관리패널 접속, 접속자 채널 연결", mid));
		
		// 메세지 세션 생성 및 유효 시간 갱신
		MessageSession messageSession = new MessageSession(sha.getSessionId());
		messageSessionIdsContainer.add(mid, messageSession);
	}
	
	/**
	 * 접속을 해제할 클라이언트로부터 소켓 해제 메세지를 처리한다.
	 * 
 	 * @author 김승신	2019.05.21.
	 * @param mid			클라이언트 사원 식별 아이디
	 * @param message		메세지
	 * @throws Exception
	 */
	@MessageMapping("unregister.{mid}")
	public void unregister(@DestinationVariable String mid, MessageDto message) throws Exception {
		messageSessionIdsContainer.remove(message.getSessionId());
	}
	
//	/**
//	 * 유저에 보내는 메세지를 같이 받아 로깅하고, 예외사항을 처리한다.
//	 * 
//	 * @author 김승신	2019.05.21.
//	 * @param targetSessionId	받는 이 소켓 세션 아이디
//	 * @param message			보낼 메세지
//	 * @param sha				헤더
//	 * @throws Exception
//	 */
//	@MessageMapping("user.{targetSessionId}:{type}")
//	public void sniff(
//			@DestinationVariable String targetSessionId, 
//			@DestinationVariable String type,
//			MessageDto message, SimpMessageHeaderAccessor sha) throws Exception {
//		System.out.println("======================================================");
//		Logger.info(sha);
//		Logger.info(type);
//		Logger.info(message);
//	}
//	
	
}
