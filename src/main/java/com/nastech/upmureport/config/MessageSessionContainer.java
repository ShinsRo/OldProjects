package com.nastech.upmureport.config;

import java.util.HashMap;
import java.util.Map;

public class MessageSessionContainer {
	private final Map<String, MessageSession> container = new HashMap<>();
	
	public Map<String, MessageSession> getContainer() {
		return container;
	}

	public void add(String mid, MessageSession messageSession) {
		// 존재 확인 필요
		this.container.put(mid, messageSession);
	}

	public void remove(String mid) {
		this.container.remove(mid);
	}
};