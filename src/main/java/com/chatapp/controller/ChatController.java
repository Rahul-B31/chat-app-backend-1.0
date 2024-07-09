package com.chatapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.model.Chat;
import com.chatapp.model.User;
import com.chatapp.request.GroupChatRequest;
import com.chatapp.request.SingleChatRequest;
import com.chatapp.response.ApiResponse;
import com.chatapp.service.IChatService;
import com.chatapp.service.IUserService;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
	
	@Autowired
	private IChatService chatService;
	@Autowired
	private IUserService userService;
	
	@PostMapping("/single")
	public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest singleChatRequest,@RequestHeader("Authorization")String jwt)
	{
		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.createChat(reqUser, singleChatRequest.getUserId());
		return new ResponseEntity<Chat>(chat,HttpStatus.OK);
	}
	
	
	@PostMapping("/group")
	public ResponseEntity<Chat> createGroupHandler(@RequestBody GroupChatRequest req,@RequestHeader("Authorization")String jwt)
	{
		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.createGroup(req,reqUser);
		return new ResponseEntity<Chat>(chat,HttpStatus.OK);
	}
	
	@GetMapping("/{charId}")
	public ResponseEntity<Chat> findChatHandler(@PathVariable("chatId") Integer charId ,@RequestHeader("Authorization")String jwt)
	{
//		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.findChatById(charId);
		return new ResponseEntity<Chat>(chat,HttpStatus.OK);
	}
	
	@GetMapping("/user")
	public ResponseEntity<?> findAllChatHandler(@RequestHeader("Authorization")String jwt)
	{
		 User reqUser = userService.findUserProfile(jwt);
		 List<Chat> chats = chatService.findAllChatByuserId(reqUser.getId());
		return new ResponseEntity<List<Chat>>(chats,HttpStatus.OK);
	}
	
	@PutMapping("/{chatId}/add/{userId}")
	public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable("chatId") Integer chatId, @PathVariable("userId") Integer userId,@RequestHeader("Authorization")String jwt)
	{
		User reqUser = userService.findUserProfile(jwt);
		Chat chats = chatService.addUserToGroup(userId, chatId, reqUser);
		return new ResponseEntity<Chat>(chats,HttpStatus.OK);
	}
	
	@PutMapping("/{chatId}/remove/{userId}")
	public ResponseEntity<Chat> removeUserFormGroupHandler(@PathVariable("chatId") Integer chatId, @PathVariable("userId") Integer userId,@RequestHeader("Authorization")String jwt)
	{
		User reqUser = userService.findUserProfile(jwt);
		Chat chats = chatService.removeFromGroup(chatId, userId, reqUser);
		return new ResponseEntity<Chat>(chats,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete/{chatId}")
	public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable("chatId") Integer chatId,@RequestHeader("Authorization")String jwt)
	{
		 User reqUser = userService.findUserProfile(jwt);
		 chatService.deleteChat(chatId, reqUser.getId());
		 ApiResponse apiResponse = new ApiResponse("chat is deleted",true);
		 return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
	}
	
	
    

}
