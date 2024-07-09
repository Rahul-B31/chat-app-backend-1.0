package com.chatapp.advice;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.chatapp.exception.ChatException;
import com.chatapp.exception.ErrorDetails;
import com.chatapp.exception.MessageException;
import com.chatapp.exception.UserException;

@RestControllerAdvice
public class GlobalException {
     
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetails> UserExceptionHandler(UserException e,WebRequest req)
	{
		ErrorDetails err = new ErrorDetails(e.getMessage(),req.getDescription(false),LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorDetails> usernameNotFoundException(UserException e)
	{
		ErrorDetails err = new ErrorDetails("User Not Found",e.getMessage(),LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(err,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(MessageException.class)
	public ResponseEntity<ErrorDetails> messageExceptionHandler(MessageException e,WebRequest req)
	{
		ErrorDetails err = new ErrorDetails(e.getMessage(),req.getDescription(false),LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetails> noHandlerFoundExceptionHandler(NoHandlerFoundException e,WebRequest req)
	{

		ErrorDetails err = new ErrorDetails("EndPoint not Found",e.getMessage(),LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e,WebRequest req)
	{
		String error = e.getBindingResult().getFieldError().getDefaultMessage();
		ErrorDetails err = new ErrorDetails("Validation error",error,LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ChatException.class)
	public ResponseEntity<ErrorDetails> chatExceptiont(ChatException e,WebRequest req)
	{
		ErrorDetails err = new ErrorDetails(e.getMessage(),req.getDescription(false),LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
	}

	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> GenericExceptionHandler(Exception e,WebRequest req)
	{
		ErrorDetails err = new ErrorDetails(e.getMessage(),req.getDescription(false),LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
	}
}
