package com.automationtools.exception;

import com.automationtools.core.Task;
import com.automationtools.core.TaskHandler;
import com.automationtools.core.TaskHandlerFactory;

/**
 * The class {@code NoSuitableHandlerFoundException} and its subclasses 
 * are a form of {@code RuntimeException} that is thrown by the 
 * {@linkplain TaskHandlerFactory#get(com.automationtools.core.Task) handler factory}
 * in a event that it could not find a suitable {@linkplain TaskHandler handler} 
 * for a given {@linkplain Task task}.
 * 
 * <p>{@code NoSuitableHandlerFoundException} and its subclasses are <em>unchecked
 * exceptions</em>. Unchecked exceptions do <em>not</em> need to be
 * declared in a method or constructor's {@code throws} clause if they
 * can be thrown by the execution of the method or constructor and
 * propagate outside the method or constructor boundary.
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 * @see 	TaskHandlerFactory
 */
public class NoSuitableHandlerFoundException extends RuntimeException {

	/** Generated serialVersionUID */
	private static final long serialVersionUID = 7292398274923533819L;
	
	/**
	 * Default error message construct.
	 */
	private static final String _MESSAGE_FORMAT = "Failed to find appropriate handler for [%s]";

	/**
	 * Constructs a new exception indicating that the system cannot find the appropriate 
	 * {@linkplain TaskHandler handler} for the class specified. The cause is not 
	 * initialized, and may subsequently be initialized by a call to {@link #initCause}.
     * 
	 * @param clazz		The type of {@code Task} whose {@code TaskHandler handler} 
	 * 					could not be found. A formatted message is saved for later 
	 * 					retrieval by the {@link #getMessage()} method.
	 */
	public NoSuitableHandlerFoundException(Class<?> clazz) {
		super(format(clazz.getSimpleName()));
	}
	
	/**
	 * Convenience method for error message formatting.
	 */
	static String format(String message) {
		return String.format(_MESSAGE_FORMAT, message);
	}
	
}
