/** 
 * 서버의 메세지 브로커 정의 및 설정
 * 
 * 2019.05.22.
 * @author 김승신
 */
package com.nastech.upmureport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.nastech.upmureport.feature.message.model.ActiveSessionStore;


@Configuration
@EnableWebSocketMessageBroker
public class PushMessageConfig implements WebSocketMessageBrokerConfigurer {
	
	static final String DEV_ORIGIN = "http://localhost:3000";
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/globalChannel", "/queue", "/user");
		registry.setApplicationDestinationPrefixes("/app", "/");
		registry.setPathMatcher(new AntPathMatcher("."));
	}
	
	@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }
	
	@Bean
	public ActiveSessionStore messageSessionIdsContainer() {
		return new ActiveSessionStore();
	}
}