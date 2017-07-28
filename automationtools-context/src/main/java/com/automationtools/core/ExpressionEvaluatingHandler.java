package com.automationtools.core;

import static org.springframework.util.Assert.*;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Required;
import com.automationtools.core.TaskHandler;
import com.automationtools.core.support.ExpressionEvaluationSupport;
import com.automationtools.exception.ExecutionFailedException;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 * 
 * @param <T>
 * 			Execution context type
 */
public class ExpressionEvaluatingHandler<T extends Serializable, R> extends TaskHandlerDecorator<T, R> {
	
	/**
	 * The expression evaluator
	 */
	private ExpressionEvaluationSupport evaluator;
	
	public ExpressionEvaluatingHandler(TaskHandler<T, R> wrapped) {
		super(wrapped);
	}
	
	public ExpressionEvaluatingHandler(TaskHandler<T, R> wrapped, ExpressionEvaluationSupport evaluator) {
		super(wrapped);
		setEvaluator(evaluator);
	}
	
	/**
	 * Performed <em>before</em> the actual execution 
	 * by evaluating the {@code Data} and its parameters, 
	 * if it has any.
	 */
	@Override
	protected void beforeHandle(T data) throws ExecutionFailedException {
		state(evaluator != null, "ExpressionEvaluationSupport cannot be null");
		if(data instanceof Data) {
			Data raw = (Data) data;
			String oldcontent = data.toString(); 
			String newcontent = null;
			if(raw instanceof ParameterizedData)
				newcontent = evaluator.evaluate(oldcontent, ((ParameterizedData) raw).getParameters());
			else
				newcontent = evaluator.evaluate(oldcontent);
			raw.setContent(newcontent);
		}
		
		super.beforeHandle(data);
	}
	
	/**
	 * Sets the expression evaluator
	 */
	@Required
	public void setEvaluator(ExpressionEvaluationSupport evaluator) {
		notNull(evaluator, "Expression evaluator cannot be null");
		this.evaluator = evaluator;
	}
	
	/**
	 * Returns the expression evaluator
	 */
	public ExpressionEvaluationSupport getEvaluator() {
		return evaluator;
	}
	
}
