package com.chatapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.model.Message;

@RestController
public class RealTimeChat{
	
	public RealTimeChat() {
		System.err.println("real time chat");
	}
	

	private SimpMessagingTemplate messagingTemplate;
	

    @Autowired
    public RealTimeChat(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        System.err.println("real time chat object created");
    }
	
	@MessageMapping("/message")
	@SendTo("/group/public")
	public Message receviedMessage(@Payload Message message) {
		
		System.err.println("recevied");
		
		messagingTemplate.convertAndSend("/group"+message.getChat().getId().toString(),message);
		return message;
	}

}
