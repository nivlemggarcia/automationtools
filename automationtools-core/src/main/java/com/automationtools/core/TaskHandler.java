package com.automationtools.core;

import java.util.function.Function;
import com.automationtools.exception.ExecutionFailedException;

/**
 * Implementations of {@code TaskHandler} accepts 
 * {@code Task} argument to do a specific type of work 
 * and produce result.
 * 
 * <p>
 * This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #handle(Task)}.
 * </p>
 * 
 * @param <T> the type of {@code Task} input to the function
 * @param <R> the type of the result of the function
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
@FunctionalInterface
public interface TaskHandler<T, R> extends Function<Task<T>, R> {
	
	/**
	 * Performs this function to the given argument.
	 */
	public abstract R handle(T t) throws Exception;
	
	@Override
	public default R apply(Task<T> t) {
		try {
			return handle(t.getData());
		} catch (Throwable throwable) {
			throw new ExecutionFailedException(
					String.format("Failed with [%s]", throwable.getMessage()), throwable);
		}
	}
	
}
