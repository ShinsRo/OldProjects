package com.nastech.upmureport.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.nastech.upmureport.config.MessageSession;
import com.nastech.upmureport.config.MessageSessionContainer;
import com.nastech.upmureport.domain.dto.MessageDto;

@Controller
public class PushMessageController {
	Log Logger = LogFactory.getLog(PushMessageController.class);
	
	@Autowired
	private MessageSessionContainer messageSessionIdsContainer;
	
	@MessageMapping("register.{mid}")
	@SendTo("/globalChannel/notify")
	public MessageDto register(@DestinationVariable String mid, MessageDto message, SimpMessageHeaderAccessor sha) throws Exception {
		Logger.info(String.format("[MID %s] 프로젝트 관리패널 접속, 접속자 채널 연결", mid));
		
		MessageSession messageSession = new MessageSession(message.getSessionId());
		messageSessionIdsContainer.add(mid, messageSession);
		return message;
	}
	
	@MessageMapping("unregister.{mid}")
	public void unregister(@DestinationVariable String mid, MessageDto message) throws Exception {
		messageSessionIdsContainer.remove(message.getSessionId());
	}
	
//	@MessageMapping("/user.{targetSessionId}")
//	@SendTo("/user.{targetSessionId}")
//	public MessageDto sendTo(@DestinationVariable String targetSessionId, MessageDto message, SimpMessageHeaderAccessor sha) throws Exception {
//		System.out.println("SENDTO :::: " + targetSessionId);
//		return message;
//	}
}
