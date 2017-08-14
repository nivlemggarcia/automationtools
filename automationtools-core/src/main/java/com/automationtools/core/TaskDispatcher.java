package com.automationtools.core;

import java.util.concurrent.Future;

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
	 */
	public <T, R> Future<R> dispatch(Task<T> task);
	
}
