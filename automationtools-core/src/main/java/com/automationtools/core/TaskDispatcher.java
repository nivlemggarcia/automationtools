package com.automationtools.core;

import java.util.concurrent.Future;
import com.automationtools.exception.NoSuitableHandlerFoundException;

/**
 * 
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public interface TaskDispatcher {
	
	/**
	 * Dispatches the given {@code Task} at some time in the future. 
	 * The command may execute in a new thread, in a pooled thread, 
	 * or in the calling thread, at the discretion of this 
	 * {@code Dispatcher} implementation.
	 * 
	 * @param task {@code Task} subject for dispatch.
	 * 
	 * @throws NoSuitableHandlerFoundException
	 * 			In a event that this {@code Dispatcher} could not
	 * 			find suitable {@code TaskHandler} for the given {@code Task}.
	 */
	public <T, R> Future<R> dispatch(Task<T> task) throws NoSuitableHandlerFoundException;
	
}
