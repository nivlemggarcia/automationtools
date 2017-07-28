package com.automationtools.core.support;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public class ExpressionEvaluatorFactory implements FactoryBean<ExpressionEvaluationSupport> {
	
	/**
	 * 
	 */
	private EvaluationContext context;
	
	/**
	 * 
	 */
	private ExpressionParser parser;
	
	/**
	 * 
	 */
	private String pattern;

	@Override
	public ExpressionEvaluationSupport getObject() throws Exception {
		ExpressionEvaluationSupport obj = new ExpressionEvaluationSupport();
		obj.setContext(context);
		obj.setParser(parser);
		if(pattern != null && !pattern.isEmpty())
			obj.setPattern(pattern);
		return obj;
	}

	@Override
	public Class<?> getObjectType() {
		return ExpressionEvaluationSupport.class;
	}

	/**
	 * 
	 */
	@Override
	public boolean isSingleton() {
		return false;
	}
	
	/**
	 * 
	 * @param context
	 */
	@Required
	public void setContext(EvaluationContext context) {
		this.context = context;
	}
	
	/**
	 * 
	 * @param parser
	 */
	@Required
	public void setParser(ExpressionParser parser) {
		this.parser = parser;
	}
	
	/**
	 * 
	 * @param pattern
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
