/** 
 * 요청에 따라 클라이언트에 소켓 메세지를 보내는 ProjectNotificationService를 정의.
 * 
 * 2019.05.22.
 * @author 김승신
 */
package com.nastech.upmureport.feature.project.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.config.MessageSession;
import com.nastech.upmureport.config.MessageSessionContainer;
import com.nastech.upmureport.feature.project.controller.ProjectController;
import com.nastech.upmureport.feature.project.domain.dto.CollaboratorDto;
import com.nastech.upmureport.feature.project.domain.dto.ProjectDto;

@Service
public class ProjectNotificationService {
	public enum NOTIFICATION_TYPE {
		NEW_PROJECT,
		CORRECT_PROJECT,
		NOTIFY,
		CHAT,
	}
	Log Logger = LogFactory.getLog(ProjectController.class);
	
	@Autowired
	MessageSessionContainer msc;
	
	@Autowired
	SimpMessagingTemplate smt;
	
	/**
	 * 타입에 따라 메세지를 특정(혹은 불특정) 소켓을 통해 보낸다.
	 * 
	 * NEW_PROJECT: CORRECT_PROJECT:
	 * 	프로젝트를 생성(수정)할 때, 접속 중인 클라이언트가 협업자로 포함되었으면
	 * 	해당 클라이언트에 동기화하기위한 메세지를 보낸다.
	 * 
	 * NOTIFY:
	 *  모든 접속 중인 클라이언트에게 소켓 메세지를 보낸다.
	 * 
	 * CHAT:
	 * 	채팅 메세지를 관련한 사원에 브로드캐스트한다.
	 * 
	 * @author 김승신		2019.05.21.
	 * @param fromMid	보내는 이 멤버 식별 아이디
	 * @param dto		보낼 DTO		
	 * @param type		메세지 타입
	 * @throws Exception 
	 */
	public <MSG> void sendDtoByType(String fromMid, MSG dto, NOTIFICATION_TYPE type) throws Exception {
		// 현재 접속 중인 세션 맵 { mid : MessageSession }
		Map<String, MessageSession> sessionMap 	= msc.getContainer();				

		/* 유효하지 않은 DTO 타입 */
		if (dto == null) {
			Logger.error("인자 dto는 NULL일 수 없습니다.");
			throw new IllegalDtoArgException();
		}
		
		/* 유효하지 않은(이미 연결을 해제한) 멤버 식별 아이디 */
		if (!sessionMap.containsKey(fromMid)) {
			Logger.error("유효하지 않은 mid입니다.");
			throw new SessionNotExistsExcepiton();
		}
		
		/* 만료된 세션 */
		if (sessionMap.get(fromMid).isExpired()) {
			Logger.error("만료된 세션으로부터의 요청입니다.");
			msc.remove(fromMid);
			throw new SessionExpiredExcepiton();
		}
		
		switch(type) {
		case NEW_PROJECT: case CORRECT_PROJECT:
			/* 유효하지 않은 DTO 타입 */
			if (!(dto instanceof ProjectDto)) { 
				Logger.error("메세지 타입과 다른 타입의 DTO입니다.");
				throw new IllegalDtoArgException();
			}
			
			ProjectDto projectDto 					= (ProjectDto) dto;					// 보낼 프로젝트 내용
			List<CollaboratorDto> collaborators 	= projectDto.getCollaborators();	// 프로젝트에서 관련한 협업자 리스트
			
			MessageSession fromSession 				= sessionMap.get(fromMid);			// 보내는 이
			
			/* 메세지 보내기 시작 */
			for (CollaboratorDto collaboratorDto : collaborators) {
				String collabMid = collaboratorDto.getMid();	// 메세지 받을 이 mid
				
				if (collaboratorDto.getMid().equals(fromMid)) 	continue;					// 스스로에 대해 처리하지 않는다.
				if (!sessionMap.containsKey(collabMid)) 		continue;					// 접속 중이지 않은 멤버는 처리하지 않는다.
				
				MessageSession collaboratorSession 	= sessionMap.get(collabMid);			// 받을 이
				String collaboratorSessionId 		= collaboratorSession.getSessionId();	// 받을 이 소켓 세션 아이디
				
				// 보낸 이와 타입을 헤더에 첨부
				MessageHeaders msgHeaders = createHeaders(fromSession.getSessionId(), type);
								
				/* 받는 이 주소 셋팅 "/user.{targetSocketId}:TYPE"  */
				StringBuilder channelName = new StringBuilder("/user.");
				channelName.append(collaboratorSessionId);
				channelName.append(":PROJECT");
				/* 받는 이 주소 셋팅 끝 */
				
				// 메세지 전송
				smt.convertAndSend(channelName.toString(), projectDto, msgHeaders);
			}
			/* 메세지 보내기 끝 */
			break;
		default:
			break;
		}
	}
	
	private MessageHeaders createHeaders(String sessionId, NOTIFICATION_TYPE type) {
		SimpMessageHeaderAccessor ha = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
		ha.setSessionId(sessionId);
		ha.setHeader("TYPE", type);
		ha.setLeaveMutable(true);
		return ha.getMessageHeaders();
	}
	
	/* 고유 익셉션 정의 */
	public class IllegalDtoArgException extends Exception {
		private static final long serialVersionUID = 1L;
	}
	public class SessionNotExistsExcepiton extends Exception {
		private static final long serialVersionUID = 1L;
	}
	public class SessionExpiredExcepiton extends Exception {
		private static final long serialVersionUID = 1L;
	}
}
