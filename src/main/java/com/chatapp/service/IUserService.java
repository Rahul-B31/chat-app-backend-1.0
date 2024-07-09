package com.chatapp.service;

import java.util.List;
import com.chatapp.model.User;
import com.chatapp.request.UpdateUserRequest;

public interface IUserService {
	
	public User findUserById(Integer id);
	
	public List<User> searchUsers(String query);
	
	public User findUserProfile(String jwt);
	
	public User createUser(User user);
	
	public User updateUser(Integer userId,UpdateUserRequest red);

}
