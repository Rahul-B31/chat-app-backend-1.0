package com.chatapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer   {
	
	 
	@Override
	public void registerStompEndpoints( StompEndpointRegistry registry)
	{
		
		 registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
		System.err.println("register");
		
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		
		System.err.println("configure");
		 registry.setApplicationDestinationPrefixes("/app");
		 registry.enableSimpleBroker("/group","/user");
		 registry.setUserDestinationPrefix("/user");
		 
	}

}
