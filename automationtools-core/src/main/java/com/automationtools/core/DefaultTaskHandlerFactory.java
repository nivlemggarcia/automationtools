package com.automationtools.core;

import static org.springframework.util.Assert.*;
import java.util.Set;
import org.springframework.beans.factory.annotation.Required;
import com.automationtools.exception.NoSuitableHandlerFoundException;
import com.automationtools.util.KeyValuePair;

/**
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class DefaultTaskHandlerFactory implements TaskHandlerFactory {
	
	/**
	 * A set of {@code Data}-type to {@code TaskConsumer} mapping.
	 */
	private Set<KeyValuePair<Class<?>, TaskHandler<?, ?>>> mapping;
	
	@SuppressWarnings("unchecked")
	@Override
	public <T, R> TaskHandler<T, R> get(Task<T> task) throws NoSuitableHandlerFoundException {
		for(KeyValuePair<Class<?>, TaskHandler<?, ?>> entry : mapping) {
			/* Checks if the mapped Class matches the type of data that the task has */
			if(entry.getKey().equals(task.getType()))
				return (TaskHandler<T, R>) entry.getValue();
		}
		
		throw new NoSuitableHandlerFoundException(task.getType());
	}
	
	/**
	 * Sets the {@code Data}-type to {@code TaskConsumer} mapping.
	 */
	@Required
	public void setMapping(Set<KeyValuePair<Class<?>, TaskHandler<?, ?>>> mapping) {
		notNull(mapping, "TaskHandler mapping cannot be null");
		this.mapping = mapping;
	}
	
	/**
	 * Returns the {@code Data}-type to {@code TaskConsumer} mapping.
	 */
	public Set<KeyValuePair<Class<?>, TaskHandler<?, ?>>> getMapping() {
		return mapping;
	}

}
