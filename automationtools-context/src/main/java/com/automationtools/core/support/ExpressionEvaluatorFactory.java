package com.automationtools.core.support;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;

/**
 * A {@linkplain FactoryBean} for creating instances of {@linkplain ExpressionEvaluationSupport}.
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public class ExpressionEvaluatorFactory implements FactoryBean<ExpressionEvaluationSupport> {
	
	/**
	 * The {@linkplain EvaluationContext} used when creating {@code ExpressionEvaluationSupport}.
	 */
	private EvaluationContext context;
	
	/**
	 * The {@linkplain ExpressionParser} used when creating {@code ExpressionEvaluationSupport}.
	 */
	private ExpressionParser parser;
	
	/**
	 * The pattern that will be used to evaluate the expressions. 
	 */
	private String pattern;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExpressionEvaluationSupport getObject() throws Exception {
		ExpressionEvaluationSupport obj = new ExpressionEvaluationSupport();
		obj.setContext(context);
		obj.setParser(parser);
		if(pattern != null && !pattern.isEmpty())
			obj.setPattern(pattern);
		return obj;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getObjectType() {
		return ExpressionEvaluationSupport.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSingleton() {
		return false;
	}
	
	/**
	 * Sets the {@linkplain EvaluationContext} used when creating {@code ExpressionEvaluationSupport}.
	 */
	@Required
	public void setContext(EvaluationContext context) {
		this.context = context;
	}
	
	/**
	 * Sets the {@linkplain ExpressionParser} used when creating {@code ExpressionEvaluationSupport}.
	 */
	@Required
	public void setParser(ExpressionParser parser) {
		this.parser = parser;
	}
	
	/**
	 * Sets the pattern that will be used to evaluate the expressions. 
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
