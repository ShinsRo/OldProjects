package com.nastech.upmureport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class PushMessageConfig implements WebSocketMessageBrokerConfigurer {
	
	static final String DEV_ORIGIN = "http://localhost:3000";
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/globalChannel", "/queue", "/user");
		registry.setApplicationDestinationPrefixes("/app");
		registry.setPathMatcher(new AntPathMatcher("."));
	}
	
	@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins(DEV_ORIGIN).withSockJS();
    }
	
	@Bean
	public MessageSessionContainer messageSessionIdsContainer() {
		return new MessageSessionContainer();
	}
}
