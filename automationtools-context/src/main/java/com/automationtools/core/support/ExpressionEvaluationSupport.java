package com.automationtools.core.support;

import static org.springframework.util.Assert.*;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import com.automationtools.exception.EvaluationFailedException;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public class ExpressionEvaluationSupport {
	
	private static final Logger log = LoggerFactory.getLogger(ExpressionEvaluationSupport.class);

	/**
	 * This syntax regex follows this pattern: <pre>${EXPRESSION}</pre>
	 */
	public static final String DEFAULT_PATTERN = "\\$\\{([^{}]*|[^\\{\\}]*)\\}";
	
	private EvaluationContext context;
	
	private ExpressionParser parser;
	
	private String pattern = DEFAULT_PATTERN;
	
	public String evaluate(String toInspect, Map<String, String> parameters) {
		state(context != null, "EvaluationContext cannot be null");
		state(pattern != null, "Expression pattern cannot be null");
		
		Pattern pattern = Pattern.compile(this.pattern);
		Matcher matcher = pattern.matcher(toInspect);
		if(matcher.find()) {
			context.setVariable("VALUES", evaluate(parameters));
	        return evaluate(toInspect);
	    }
		
		return toInspect;
	}
	
	public String evaluate(String toInspect, Properties parameters) {
		state(context != null, "EvaluationContext cannot be null");
		state(pattern != null, "Expression pattern cannot be null");
		
		Pattern pattern = Pattern.compile(this.pattern);
		Matcher matcher = pattern.matcher(toInspect);
		if(matcher.find()) {
			context.setVariable("VALUES", evaluate(parameters));
	        return evaluate(toInspect);
	    }
		
		return toInspect;
	}
	
	public Map<String, String> evaluate(Map<String, String> parameters) {
		notNull(parameters, "Parameter argument cannot be null");
		
		/* Resolve the parameters' expressions */
		for(String key : parameters.keySet()) {
			String newValue = evaluate(parameters.get(key));
			String oldValue = parameters.put(key, newValue);
			log.debug("Evaluated Property '" + key + "': " + oldValue + " -> " + newValue);
		}
		
		return parameters;
	}
	
	public Properties evaluate(Properties parameters) {
		notNull(parameters, "Properties argument cannot be null");
		
		/* Resolve the parameters' expressions */
		for(String key : parameters.stringPropertyNames()) {
			String newValue = evaluate(parameters.getProperty(key));
			String oldValue = (String) parameters.put(key, newValue);
			log.debug("Evaluated Property '" + key + "': " + oldValue + " -> " + newValue);
		}
		
		return parameters;
	}
	
	public String evaluate(String toInspect) {
		notNull(toInspect, "String subject for evaluation cannot be null");
		state(context != null, "EvaluationContext cannot be null");
		state(parser != null, "ExpressionParser cannot be null");
		state(pattern != null, "Expression pattern cannot be null");
		
		Pattern pattern = Pattern.compile(this.pattern);
		Matcher matcher = pattern.matcher(toInspect);
		
		/* recursively evaluate the expressions */
		if(matcher.find()) {
			StringBuffer sb = null;
	        do {
	        	sb = new StringBuffer();
	            String tokenKey = matcher.group(1);
	            /* resolve parameter tokens */
	            String tokenValue = evaluate(tokenKey);
	            if(tokenValue == null) 
	            	throw new EvaluationFailedException("No value found for token '" + tokenKey + "'");
	            
	            log.debug("Evaluated Token " + tokenKey + " -> " + tokenValue);
            	String replacement = Matcher.quoteReplacement(tokenValue);
	            matcher.appendReplacement(sb, replacement);
	            matcher.appendTail(sb);
	            
	            /* will recursively replace all parameters, at any level */
	            matcher = pattern.matcher(sb.toString());
	        } while(matcher.find());
	        return evaluate(sb.toString());
	    }
		
    	try { /* The actual expression evaluation happens here */
			return parser.parseExpression(toInspect).getValue(context, String.class);
		} catch (Exception e) {
			throw new EvaluationFailedException("Failed to evaluate [" + toInspect + "]", e);
		}
	}
	
	public EvaluationContext getContext() {
		return context;
	}
	
	@Required
	public void setContext(EvaluationContext context) {
		notNull(context, "EvaluationContext cannot be null");
		this.context = context;
	}
	
	public ExpressionParser getParser() {
		return parser;
	}
	
	@Required
	public void setParser(ExpressionParser parser) {
		notNull(parser, "ExpressionParser cannot be null");
		this.parser = parser;
	}
	
	public String getPattern() {
		return pattern;
	}
	
	public void setPattern(String pattern) {
		notNull(pattern, "Expression pattern cannot be null");
		state(!pattern.isEmpty(), "Pattern cannot be empty");
		this.pattern = pattern;
	}

}
