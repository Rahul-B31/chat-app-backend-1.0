package com.chatapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.model.User;
import com.chatapp.request.UpdateUserRequest;
import com.chatapp.response.ApiResponse;
import com.chatapp.service.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	IUserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<User> getuserProfileHandler(@RequestHeader("Authorization") String token)
	{
		// Authorize the user using the token 
		User user = userService.findUserProfile(token);
		return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
	}
	
   @CrossOrigin(origins = "http://localhost:5173")
   @GetMapping("/search")	
   public ResponseEntity<List<User>> searchUserHandler(@RequestParam String query)
   {
	   List<User> users = userService.searchUsers(query);
	   return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	   
   }
    
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest req,@RequestHeader("Authorization") String token)
    {
    	System.out.println("image ---"+req.getProfile_picture());
        Integer userId = userService.findUserProfile(token).getId();	
    	userService.updateUser(userId,req);
    	
    	ApiResponse res = new ApiResponse("User updated successfully",true);
    	return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
    	
    }
   
   
   

}
