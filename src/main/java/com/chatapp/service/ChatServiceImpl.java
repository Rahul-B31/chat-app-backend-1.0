package com.chatapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatapp.exception.ChatException;
import com.chatapp.exception.UserException;
import com.chatapp.model.Chat;
import com.chatapp.model.User;
import com.chatapp.repository.ChatRepository;
import com.chatapp.request.GroupChatRequest;

@Service
public class ChatServiceImpl implements IChatService {

	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private IUserService userService;
	
	
	@Override
	public Chat createChat(User reqUser, Integer userId) {
		
		User user = userService.findUserById(userId);
		
		Chat isChatExist = chatRepository.findSingleChatByUserIds(user,reqUser);
		if(isChatExist!=null)
		{
			return isChatExist;
					
		}
		Chat chat = new Chat();
		chat.setCreatedBy(reqUser);
		chat.getUsers().add(user);
		chat.setChat_name(user.getFull_name());
		chat.getUsers().add(reqUser);
		chat.setIsGroup(false);
		
		chatRepository.save(chat);
		
		return chat;
	}

	@Override
	public List<Chat> findAllChatByuserId(Integer userId) {
		
		User user = userService.findUserById(userId);
		List<Chat> chats = chatRepository.findChatByUserid(user.getId());
		return chats;
	}

	@Override
	public Chat findChatById(Integer userId) {
		
		Optional<Chat> chat = chatRepository.findById(userId);
		return chat.orElseThrow(()->new ChatException("The Chat is Not Found With id "+userId));
	}

	@Override
	public Chat createGroup(GroupChatRequest req, User reqUser) {

		Chat group =  new Chat();
		group.setIsGroup(true);
		group.setChat_image(req.getChat_image());
		group.setChat_name(req.getChat_name());
		group.setCreatedBy(reqUser);
		group.getAdmin().add(reqUser);
		
		for(Integer userId:req.getUserIds())
		{
			User user = userService.findUserById(userId);
			group.getUsers().add(user);
			
		}
		chatRepository.save(group);
		
		return group;
	}

	@Override
	public Chat addUserToGroup(Integer userId, Integer chatId,User reqUser) {
          
		 Optional<Chat> result = chatRepository.findById(chatId);
		 Chat chat = result.orElseThrow(()->new ChatException("Chat Not Found"));
		
		 User user = userService.findUserById(chatId);
		 if(chat.getAdmin().contains(reqUser))
		 {
			 chat.getUsers().add(user);
			 return chatRepository.save(chat);
		 }
		 else
		 {
			  throw new UserException("You are Not Admin");
		 }
		  
		
	}

	@Override
	public Chat renameGroup(Integer chatId, String groupName, User reqUser) {
          
		Optional<Chat> opt = chatRepository.findById(chatId);
		
		if(opt.isPresent())
		{
			Chat chat = opt.get();
			if(chat.getUsers().contains(reqUser))
			{
				chat.setChat_name(groupName);
				return chatRepository.save(chat);
			}
			throw new UserException("You Are Not Member Of the Group");
		}
		
		return null;
	}

	@Override
	public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) {

		 Optional<Chat> result = chatRepository.findById(chatId);
		 Chat chat = result.orElseThrow(()->new ChatException("Chat Not Found"));
		
		 User user = userService.findUserById(chatId);
		 if(chat.getAdmin().contains(reqUser))
		 {
			 chat.getUsers().remove(user);
			 return chatRepository.save(chat);
		 }
		 else if(chat.getUsers().contains(reqUser))
		 {
			 if(user.getId().equals(reqUser.getId()))
			 {
				 chat.getUsers().remove(user);
				 return chatRepository.save(chat);
			 }
		 }
			  throw new UserException("You can't remove another");
		
//		return null;
	}

	@Override
	public void deleteChat(Integer chatId, Integer userId) {
        
		 Optional<Chat> opt = chatRepository.findById(userId);
		 if(opt.isPresent())
		 {
			 Chat chat = opt.get();
			 chatRepository.deleteById(chat.getId());	 
		 }
		
	}

}
