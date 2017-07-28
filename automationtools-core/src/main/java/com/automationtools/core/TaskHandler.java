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
public interface TaskHandler<T, R> extends Function<Task<T>, Result<R>> {
	
	/**
	 * Performs this function to the given argument.
	 */
	abstract R handle(T t) throws Exception;
	
	@Override
	default Result<R> apply(Task<T> t) {
		try {
			R value = handle(t.getData());
			/* Wrap the return value with Result object */
			return new Result<>(t.getId(), value);
		} catch (Throwable throwable) {
			throw new ExecutionFailedException(
					String.format("Failed with [%s]", throwable.getMessage()), throwable);
		}
	}
	
}
