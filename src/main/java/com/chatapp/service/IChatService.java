package com.chatapp.service;

import java.util.List;

import com.chatapp.model.Chat;
import com.chatapp.model.User;
import com.chatapp.request.GroupChatRequest;

public interface IChatService {

	public Chat createChat(User reqUser,Integer userId);
	
	public List<Chat> findAllChatByuserId(Integer userId);
	
	public Chat findChatById(Integer userId);
	
	public Chat createGroup(GroupChatRequest req,User reqUser);
	
	public Chat addUserToGroup(Integer userId,Integer chatId,User reqUser);
	
	public Chat renameGroup(Integer chatId,String GroupName,User reqUser);
	
	public Chat removeFromGroup(Integer chatId,Integer userId,User reqUser);
	
	public void deleteChat(Integer chatId,Integer userId);
	
}
