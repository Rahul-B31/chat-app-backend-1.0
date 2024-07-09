package com.chatapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatapp.config.TokenProvider;
import com.chatapp.exception.UserException;
import com.chatapp.model.User;
import com.chatapp.repository.UserRepository;
import com.chatapp.request.UpdateUserRequest;

@Service
public class UserServiceImpl implements IUserService{
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	

	@Override
	public User findUserById(Integer id){

		 Optional<User> user = userRepository.findById(id);
		 return user.orElseThrow(()->new UserException("User Not Found  For The Id "+id));
	}

	@Override
	public User findUserProfile(String jwt){
          String email = tokenProvider.getEmailFromToken(jwt);
   
          if(email==null)
        	  throw new BadCredentialsException("Recevied Invalid Token");
          Optional<User> user = userRepository.findByEmail(email);
          return user.orElseThrow(()->new UserException("User Not Found for the email"+email));
    
	}

	@Override
	public User updateUser(Integer userId, UpdateUserRequest req){
         Optional<User> oldUser = userRepository.findById(userId);
         User user = oldUser.orElseThrow(()->new UserException("The User Not Found With id"+userId));
         
         if(req.getFull_name()!=null)
         {
        	 user.setFull_name(req.getFull_name());
         }
         if(req.getProfile_picture()!=null)
         {
        	 user.setProfile_picture(req.getProfile_picture());
         }
		return userRepository.save(user);
	}


	@Override
	public List<User> searchUsers(String query) {
		 List<User> users = userRepository.searchUsers(query);
		 return users;
	}

	@Override
	public User createUser(User user) {
		
		String email = user.getEmail();
		String full_name = user.getFull_name();
		String password = user.getPassword();
		
		//check for the email is already exits or not 
		Optional<User> isUser = userRepository.findByEmail(email);
		
		if(isUser.isPresent())
		{
			throw new UserException("The Email is Already Exits with Diffrent Account "+email);
		}
			
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setFull_name(full_name);
		createdUser.setPassword(passwordEncoder.encode(password));
		
		return userRepository.save(createdUser);
	}

	

}
