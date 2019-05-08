package com.nastech.upmureport.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.config.MessageSession;
import com.nastech.upmureport.config.MessageSessionContainer;
import com.nastech.upmureport.domain.dto.CollaboratorDto;
import com.nastech.upmureport.domain.dto.ProjectDto;

@Service
public class ProjectNotificationService {

	@Autowired
	MessageSessionContainer msc;
	
	@Autowired
	SimpMessagingTemplate smt;
	
	public void notifyTo(String from, ProjectDto projectDto, String type) {
		List<CollaboratorDto> collaborators = projectDto.getCollaborators();
		Map<String, MessageSession> msMap = msc.getContainer();
		
		for (CollaboratorDto collaboratorDto : collaborators) {
			if (collaboratorDto.getMid().equals(from)) continue;
			
			MessageSession collabSession = msMap.get(collaboratorDto.getMid());
			if (collabSession != null) {
				smt.convertAndSend("/user." + collabSession.getSessionId() + type, projectDto, createHeaders(collabSession.getSessionId()));
			}
		}
	}
	
	private MessageHeaders createHeaders(String sessionId) {
		SimpMessageHeaderAccessor ha = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
		ha.setSessionId(sessionId);
		ha.setLeaveMutable(true);
		return ha.getMessageHeaders();
	}
}
