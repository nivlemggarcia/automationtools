package com.automationtools.core.backup;

import java.util.Properties;

import com.automationtools.core.TemplateData;

/**
 * A type of {@code TemplateData} that represent a content that is 
 * parameterized. Parameter resolution process is <strong>not</strong>
 * included in this implementation.
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 */
public class ParameterizedTemplateData extends TemplateData {

	/**
	 * Holds the key-value paired parameters.
	 */
	private Properties parameters;
	
	/**
	 * Default constructor
	 */
	public ParameterizedTemplateData() {
		super();
		this.parameters = new Properties();
	}
	
	/**
	 * Returns the key-value paired parameters.
	 */
	public Properties getParameters() {
		return parameters;
	}
	
	/**
	 * Sets the key-value paired parameters.
	 */
	public void setParameters(Properties parameters) {
		this.parameters = parameters;
	}
	
}
