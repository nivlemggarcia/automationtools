package com.automationtools.core;

import static org.springframework.util.Assert.*;

import java.io.Serializable;

import com.automationtools.exception.ExecutionFailedException;

/**
 * The class {@code HandlerDecorator} is the base decorator class for 
 * {@code Handler}
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 * 
 * @param <T> 	Execution context type
 */
public class TaskHandlerDecorator<T extends Serializable, R> implements TaskHandler<T, R> {

	/**
	 * The wrapped instance
	 */
	protected TaskHandler<T, R> wrapped;
	
	/**
	 * Constructor that accepts the wrapped 
	 * {@code Handler} instance.
	 * 
	 * @param wrapped	The wrapped {@code TaskHandler} instance
	 */
	public TaskHandlerDecorator(TaskHandler<T, R> wrapped) {
		notNull(wrapped, "Wrapped TaskHandler cannot be null");
		this.wrapped = wrapped;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public R handle(T arg) throws Exception {
		try {
			beforeHandle(arg);
			R value = wrapped.handle(arg);
			afterHandle(arg);
			return value;
		} finally {
			finallyInternal(arg);
			cleanup(arg);
		}
	}
	
	/**
	 * Returns the wrapped instance
	 * 
	 * @return		The wrapped instance
	 */
	protected TaskHandler<T, R> getWrapped() {
		return wrapped;
	}
	
	/**
	 * Performed <em>before</em> the actual execution. This method is intended to be 
	 * implemented by the subclasses. The default implementation does nothing. 
	 * 
	 * @param arg			The execution context containing the wrapped data
	 * @throws Exception 	Exception occurred while in pre-execution 
	 */
	protected void beforeHandle(T arg) throws ExecutionFailedException {
		/* Default: do nothing. Will be implemented by subclasses */
	}
	
	/**
	 * Performed <strong>after successful execution</strong>. This method is intended to be 
	 * implemented by the subclasses. The default implementation does nothing. 
	 * 
	 * @param arg			The execution context containing the wrapped data
	 * @throws Exception 	Exception occurred while in post-execution 
	 */
	protected void afterHandle(T arg) throws ExecutionFailedException {
		/* Default: do nothing. Will be implemented by subclasses */
	}
	
	/**
	 * Performed <em>after</em> the actual execution. This method is intended to be 
	 * implemented by the subclasses. The default implementation does nothing. 
	 * 
	 * @param arg			The execution context containing the wrapped data
	 * @throws Exception 	Exception occurred while in post-execution 
	 */
	protected void finallyInternal(T arg) throws ExecutionFailedException {
		/* Default: do nothing. Will be implemented by subclasses */
	}
	
	/**
	 * Performs cleanup operation. This method is intended to be 
	 * implemented by the subclasses. The default implementation does nothing. 
	 * 
	 * @param arg			The execution context containing the wrapped data
	 * @throws Exception 	Exception occurred while in post-execution 
	 */
	protected void cleanup(T arg) throws ExecutionFailedException {
		/* Default: do nothing. Will be implemented by subclasses */
	}

}
