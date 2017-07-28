package com.automationtools.exception;

/**
 * Thrown when an unexpected error occurred during execution.
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class ExecutionFailedException extends RuntimeException {
	
	/** Generated serialVersionUID */
	private static final long serialVersionUID = 5174417116450691022L;
	
	public ExecutionFailedException() {
		super();
	}
	
	public ExecutionFailedException(String message) {
		super(message);
	}
	
	public ExecutionFailedException(Throwable t) {
		super(t);
	}
	
	public ExecutionFailedException(String message, Throwable t) {
		super(message, t);
	}
	
}
