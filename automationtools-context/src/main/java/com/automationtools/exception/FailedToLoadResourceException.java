package com.automationtools.exception;

import java.io.File;

/**
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 */
public class FailedToLoadResourceException extends RuntimeException {

	/** Generated serialVersionUID */
	private static final long serialVersionUID = -4488711257897018244L;
	
	private static final String _MESSAGE_FORMAT = "Failed to load %s";

	public FailedToLoadResourceException() {
		super();
	}
	
	public FailedToLoadResourceException(String resourceName) {
		super(format(resourceName));
	}
	
	public FailedToLoadResourceException(File resource) {
		super(format(resource.getName()));
	}
	
	public FailedToLoadResourceException(String message, Throwable t) {
		super(format(message), t);
	}
	
	static String format(String message) {
		return String.format(_MESSAGE_FORMAT, message);
	}
	
}
