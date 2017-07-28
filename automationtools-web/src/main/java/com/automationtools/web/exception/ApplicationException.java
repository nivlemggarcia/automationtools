package com.automationtools.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApplicationException extends RuntimeException {
	
	/** Generated serialVersionUID */
	private static final long serialVersionUID = 8157149040927424162L;
	
	public ApplicationException() {
		super();
	}

	public ApplicationException(String message) {
		super(message);
	}
	
	public ApplicationException(Exception e) {
		super(e);
	}
	
	public ApplicationException(String message, Exception e) {
		super(message, e);
	}
	
}
