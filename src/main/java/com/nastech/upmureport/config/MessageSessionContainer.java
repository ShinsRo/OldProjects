/** 
 * 접속 중인 클라이언트의 MessageSession 객체를 관리하는 컨테이너 정의
 * 
 * 2019.05.22.
 * @author 김승신
 */
package com.nastech.upmureport.config;

import java.util.HashMap;
import java.util.Map;

public class MessageSessionContainer {
	private final Map<String, MessageSession> container = new HashMap<>();
	
	public Map<String, MessageSession> getContainer() {
		return container;
	}

	public void add(String mid, MessageSession messageSession) 	{ this.container.put(mid, messageSession); }
	public void remove(String mid) 								{ this.container.remove(mid); }
};