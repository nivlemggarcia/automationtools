package com.automationtools.core;

/**
 * 
 * @param <T>	Execution context type
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 */
public abstract class BaseHandler<T extends Data> implements Handler<T> {

	/**
	 * The type of object that this {@code Executor} accepts.
	 */
	private Class<T> clazz;
	
	/**
	 * Constructor that accepts the type that defines
	 * the object that this {@code Handler} accepts.
	 */
	public BaseHandler(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * Returns the type of data that this {@code ExecutorBase} accepts.
	 */
	public Class<T> forType() {
		return this.clazz;
	}
	
}
