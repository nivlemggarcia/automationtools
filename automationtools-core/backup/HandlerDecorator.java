package com.automationtools.core;

import static org.springframework.util.Assert.*;
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
public class HandlerDecorator<T, R, E extends ExecutionFailedException> implements Handler<T, R, E> {

	/**
	 * The wrapped instance
	 */
	protected Handler<T, R, E> wrapped;
	
	/**
	 * Constructor that accepts the wrapped 
	 * {@code Handler} instance.
	 * 
	 * @param wrapped	The wrapped {@code Handler} instance
	 */
	public HandlerDecorator(Handler<T, R, E> wrapped) {
		notNull(wrapped);
		this.wrapped = wrapped;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(Task<T> arg) throws ExecutionFailedException {
		try {
			beforeHandle(arg);
			wrapped.handle(arg);
			afterHandle(arg);
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
	protected Handler<T> getWrapped() {
		return wrapped;
	}
	
	/**
	 * Performed <em>before</em> the actual execution. This method is intended to be 
	 * implemented by the subclasses. The default implementation does nothing. 
	 * 
	 * @param arg			The execution context containing the wrapped data
	 * @throws Exception 	Exception occurred while in pre-execution 
	 */
	protected void beforeHandle(Task<T> arg) throws ExecutionFailedException {
		/* Default: do nothing. Will be implemented by subclasses */
	}
	
	/**
	 * Performed <strong>after successful execution</strong>. This method is intended to be 
	 * implemented by the subclasses. The default implementation does nothing. 
	 * 
	 * @param arg			The execution context containing the wrapped data
	 * @throws Exception 	Exception occurred while in post-execution 
	 */
	protected void afterHandle(Task<T> arg) throws ExecutionFailedException {
		/* Default: do nothing. Will be implemented by subclasses */
	}
	
	/**
	 * Performed <em>after</em> the actual execution. This method is intended to be 
	 * implemented by the subclasses. The default implementation does nothing. 
	 * 
	 * @param arg			The execution context containing the wrapped data
	 * @throws Exception 	Exception occurred while in post-execution 
	 */
	protected void finallyInternal(Task<T> arg) throws ExecutionFailedException {
		/* Default: do nothing. Will be implemented by subclasses */
	}
	
	/**
	 * Performs cleanup operation. This method is intended to be 
	 * implemented by the subclasses. The default implementation does nothing. 
	 * 
	 * @param arg			The execution context containing the wrapped data
	 * @throws Exception 	Exception occurred while in post-execution 
	 */
	protected void cleanup(Task<T> arg) throws ExecutionFailedException {
		/* Default: do nothing. Will be implemented by subclasses */
	}

}
