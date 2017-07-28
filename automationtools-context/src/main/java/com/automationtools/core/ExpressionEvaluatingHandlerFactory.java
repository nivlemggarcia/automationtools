package com.automationtools.core;

import static org.springframework.util.Assert.*;
import org.springframework.beans.factory.annotation.Required;
import com.automationtools.core.support.ExpressionEvaluationSupport;
import com.automationtools.exception.NoSuitableHandlerFoundException;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public class ExpressionEvaluatingHandlerFactory implements TaskHandlerFactory {
	
	/**
	 * Helper object that offers the <em>expression evaluation capability</em>.
	 */
	private ExpressionEvaluationSupport evaluator;
	
	/**
	 * The wrapped {@code HandlerFactory}.
	 */
	private TaskHandlerFactory wrapped;
	
	/**
	 * Creates a new instance of {@code ExpressionEvaluatingCapableHandlerFactory}
	 * that wraps the specified {@code HandlerFactory}.
	 */
	public ExpressionEvaluatingHandlerFactory(TaskHandlerFactory wrapped) {
		this.wrapped = wrapped;
	}

	/**
	 * Returns the {@linkplain TaskHandler handler} for the specified type with 
	 * <em>expression evaluation capability</em>.
	 */
	@Override
	public <T, R> TaskHandler<T, R> get(Task<T> task) throws NoSuitableHandlerFoundException {
		TaskHandler<T, R> handler = wrapped.get(task);
		/* Decorates Handler with Expression Evaluation capability */
		handler = new ExpressionEvaluatingHandler<T, R>(handler, evaluator);
		return handler;
	}
	
	/**
	 * Sets the helper object that offers the <em>expression evaluation capability</em>.
	 */
	@Required
	public void setEvaluator(ExpressionEvaluationSupport evaluator) {
		notNull(evaluator, "Expression evaluator cannot be null");
		this.evaluator = evaluator;
	}
	
	/**
	 * Returns the helper object that offers the <em>expression evaluation capability</em>.
	 */
	public ExpressionEvaluationSupport getEvaluator() {
		return evaluator;
	}
	
}
