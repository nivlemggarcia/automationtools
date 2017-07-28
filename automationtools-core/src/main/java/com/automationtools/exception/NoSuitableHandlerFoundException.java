package com.automationtools.exception;

import com.automationtools.core.TaskDispatcher;

/**
 * Thrown by {@linkplain TaskDispatcher#dispatch(com.automationtools.core.Task) dispatcher}
 * in a event that it could not find a suitable {@linkplain Handler handler} 
 * for a given {@linkplain Task task}.
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 * @see TaskDispatcher
 */
public class NoSuitableHandlerFoundException extends Exception {

	/** Generated serialVersionUID */
	private static final long serialVersionUID = 7292398274923533819L;
	
	private static final String _MESSAGE_FORMAT = "Failed to find appropriate handler for [%s]";

	public NoSuitableHandlerFoundException(Class<?> c) {
		super(format(c.getSimpleName()));
	}
	
	static String format(String message) {
		return String.format(_MESSAGE_FORMAT, message);
	}
	
}
