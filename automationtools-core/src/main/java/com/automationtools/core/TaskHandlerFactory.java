package com.automationtools.core;

import java.io.Serializable;
import com.automationtools.exception.NoSuitableHandlerFoundException;

/**
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public interface TaskHandlerFactory {

	/**
	 * Returns the {@linkplain TaskHandler handler} for
	 * the given {@code Task}. 
	 */
	public <T extends Serializable, R> TaskHandler<T, R> get(Task<T> task) throws NoSuitableHandlerFoundException;
	
}
