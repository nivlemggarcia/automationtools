package com.automationtools.exception;

/**
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class EvaluationFailedException extends RuntimeException {
	
	/** Generated serialVersionUID */
	private static final long serialVersionUID = 5174417116450691022L;
	
	public EvaluationFailedException() {
		super();
	}
	
	public EvaluationFailedException(String message) {
		super(message);
	}
	
	public EvaluationFailedException(Throwable t) {
		super(t);
	}
	
	public EvaluationFailedException(String message, Throwable t) {
		super(message, t);
	}
	
}
