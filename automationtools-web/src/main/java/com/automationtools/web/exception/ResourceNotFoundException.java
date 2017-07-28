package com.automationtools.web.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	
	/** Generated serialVersionUID */
	private static final long serialVersionUID = 3708490570190152226L;

	public ResourceNotFoundException() {
		super();
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	public ResourceNotFoundException(Exception e) {
		super(e);
	}
	
	public ResourceNotFoundException(String message, Exception e) {
		super(message, e);
	}
	
}
