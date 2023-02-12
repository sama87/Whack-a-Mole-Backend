package com.cognixia.jump.exception;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> badCredentialsExcpeption(BadCredentialsException exc, WebRequest request ){
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exc.getMessage(), request.getDescription(false) );
		
		return ResponseEntity.status(403).body(errorDetails);
		
	}

}
