package com.automationtools.core;

import static org.springframework.util.Assert.*;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Required;
import com.automationtools.exception.NoSuitableHandlerFoundException;

/**
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class DefaultTaskHandlerFactory implements TaskHandlerFactory {
	
	/**
	 * A {@code Data}-type to {@code TaskHandler} mapping.
	 */
	private Map<Class<?>, TaskHandler<?, ?>> mapping;
	
	@SuppressWarnings("unchecked")
	@Override
	public <T, R> TaskHandler<T, R> get(Task<T> task) throws NoSuitableHandlerFoundException {
		for(Entry<Class<?>, TaskHandler<?, ?>> entry : mapping.entrySet()) {
			/* Checks if the mapped Class matches the type of data that the task has */
			if(entry.getKey().equals(task.getType()))
				return (TaskHandler<T, R>) entry.getValue();
		}
		
		throw new NoSuitableHandlerFoundException(task.getType());
	}
	
	/**
	 * Sets the {@code Data}-type to {@code TaskHandler} mapping.
	 */
	@Required
	public void setMapping(Map<Class<?>, TaskHandler<?, ?>> mapping) {
		notNull(mapping, "TaskHandler mapping cannot be null");
		this.mapping = mapping;
	}
	
	/**
	 * Returns the {@code Data}-type to {@code TaskHandler} mapping.
	 */
	public Map<Class<?>, TaskHandler<?, ?>> getMapping() {
		return mapping;
	}

}
