package com.chatapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.model.Message;
import com.chatapp.model.User;
import com.chatapp.request.SendMessageRequest;
import com.chatapp.response.ApiResponse;
import com.chatapp.service.IUserService;
import com.chatapp.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	@Autowired
	private IUserService userService;
	
	
	@PostMapping("/create")
	public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest req, @RequestHeader("Authorization") String jwt)
	{
		User user = userService.findUserProfile(jwt);
		req.setUserId(user.getId());
		Message message = messageService.sendMessage(req);
		return new ResponseEntity<Message>(message,HttpStatus.OK);
		
	}
	
	@GetMapping("/chat/{chatId}")
	public ResponseEntity<List<Message>> getMessageHandler(@PathVariable Integer chatId,@RequestHeader("Authorization") String jwt)
	{
		 User user = userService.findUserProfile(jwt);
		 List<Message> chatMessages = messageService.getChatMessages(chatId,user);
		 return new ResponseEntity<>(chatMessages,HttpStatus.OK);
		
	}
	@DeleteMapping("/{messageId}")
	public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Integer msgId,@RequestHeader("Authorization") String jwt)
	{
		User user = userService.findUserProfile(jwt);
		messageService.deleteMessage(msgId, user);
		ApiResponse response = new ApiResponse("message deleted successfully",false);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	 
	

}
