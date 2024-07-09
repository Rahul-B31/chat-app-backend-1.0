package com.chatapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.config.TokenProvider;
import com.chatapp.model.User;
import com.chatapp.request.LoginRequest;
import com.chatapp.response.AuthResponse;
import com.chatapp.service.CustomUserService;
import com.chatapp.service.IUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	IUserService userService;
	@Autowired
	CustomUserService customUserService;
	@Autowired
	TokenProvider tokenProvider;
	@Autowired 
	PasswordEncoder passwordEncoder;
	
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user)
	{
		System.out.println(user);
		User createUser = userService.createUser(user);	
		String email = createUser.getEmail();
		String password = createUser.getPassword();
		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = tokenProvider.generateToken(authentication);
		AuthResponse res = new AuthResponse(jwt,true);
		return new ResponseEntity<AuthResponse>(res,HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req)
	{
		String email = req.getEmail();
		String password = req.getPassword();
		Authentication authentication = authenticate(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		AuthResponse res = new AuthResponse(jwt,true);
		return new ResponseEntity<AuthResponse>(res,HttpStatus.ACCEPTED);
	}
	
	public Authentication authenticate(String username,String password)
	{
		UserDetails userDetails = customUserService.loadUserByUsername(username);
		if(userDetails==null)
			throw new BadCredentialsException("invalid username");
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid password or username");
		}
		
		return new UsernamePasswordAuthenticationToken(userDetails,userDetails.getAuthorities());
	}

}
