package com.chatapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatapp.exception.ChatException;
import com.chatapp.exception.MessageException;
import com.chatapp.exception.UserException;
import com.chatapp.model.Chat;
import com.chatapp.model.Message;
import com.chatapp.model.User;
import com.chatapp.repository.MessageRepository;
import com.chatapp.request.SendMessageRequest;


@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private IUserService userService;
	@Autowired
	private IChatService chatService;
	

	@Override
	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
         User user = userService.findUserById(req.getUserId());
         Chat chat = chatService.findChatById(req.getChatId());
         
         Message message = new Message();
         message.setChat(chat);
         message.setUser(user);
         message.setContent(req.getContent());
         
         message.setTimestamp(LocalDateTime.now());
         
         Message save = messageRepository.save(message);
         
  
		return save;
	}

	@Override
	public List<Message> getChatMessages(Integer chatId,User reqUser) throws ChatException {
		
		Chat chat  = chatService.findChatById(chatId);
		
		if (!chat.getUsers().contains(reqUser)) {
		    throw new UserException("you cannot access this chat"+chat.getId());	
		}
		
		List<Message> messages = messageRepository.findByChatId(chat.getId()); 
		return messages;
	}

	@Override
	public Message findMessageById(Integer messageId) throws MessageException {
	   
		Optional<Message> opt = messageRepository.findById(messageId);
		 if(opt.isPresent())
		 {
			 return opt.get();
		 }
		throw new MessageException("message not found with id "+messageId);
	}

	@Override
	public void deleteMessage(Integer messageId,User reqUser) throws MessageException {
       
		Optional<Message> opt = messageRepository.findById(messageId);
		
		Message message = opt.get();
		if(message.getUser().getId().equals(reqUser.getId())) {
			
			messageRepository.deleteById(messageId);
		}
		else
		{
			 throw new UserException("You cannot delete another user message "+reqUser.getFull_name());
		}
		
		
		
	}

}
