package com.automationtools.exception;

/**
 * The class {@code EvaluationFailedException} and its subclasses are a form of
 * {@code RuntimeException} that indicates an unexpected error while evaluating 
 * an arbitrary {@code Object}.
 * 
 * <p>{@code EvaluationFailedException} and its subclasses are <em>unchecked
 * exceptions</em>. Unchecked exceptions do <em>not</em> need to be
 * declared in a method or constructor's {@code throws} clause if they
 * can be thrown by the execution of the method or constructor and
 * propagate outside the method or constructor boundary.
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
