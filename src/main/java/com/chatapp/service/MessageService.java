package com.chatapp.service;

import java.util.List;

import com.chatapp.exception.ChatException;
import com.chatapp.exception.MessageException;
import com.chatapp.exception.UserException;
import com.chatapp.model.Message;
import com.chatapp.model.User;
import com.chatapp.request.SendMessageRequest;

public interface MessageService {
	
	public Message sendMessage(SendMessageRequest req) throws UserException,ChatException;
	
	public List<Message> getChatMessages(Integer chatId,User reqUser) throws ChatException;
	
	public Message findMessageById(Integer messageId) throws MessageException;
	
	public void deleteMessage(Integer messageId,User reqUser) throws MessageException;
	
	
	

}
