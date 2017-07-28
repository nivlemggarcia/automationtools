package com.automationtools.core;

import java.util.Map;
import java.util.Properties;

/**
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class ParameterizedData extends Data {
	
	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 8230862167033974090L;

	/**
	 * The <strong>raw content</strong>.
	 */
	private Data data;
	
	/**
	 * Holds the data that describes the content.
	 */
	private Properties parameters = new Properties();
	
	/**
	 * Constructor that accepts a source of <strong>raw content</strong>.
	 */
	public ParameterizedData(Data wrapped) {
		super(wrapped.getContent());
		this.data = wrapped;
	}
	
	/**
	 * Constructor that accepts a source of <strong>raw content</strong>
	 * and the data that describes it.
	 */
	public ParameterizedData(Data wrapped, Map<String, String> parameters) {
		this(wrapped);
		setParameters(parameters);
	}
	
	/**
	 * Constructor that accepts a source of <strong>raw content</strong>
	 * and the data that describes it.
	 */
	public ParameterizedData(Data wrapped, Properties parameters) {
		this(wrapped);
		setParameters(parameters);
	}
	
	/**
	 * Factory method for creating a new instance of {@code ParameterizedData}
	 * using the wrapped {@code Data} object.
	 * 
	 * <p>
	 * Using this is similar to: <pre>new ParameterizedData(Data);</pre>
	 * </p>
	 */
	public static ParameterizedData create(Data data) {
		return new ParameterizedData(data);
	}
	
	/**
	 * Returns the data that describes the content.
	 */
	public Properties getParameters() {
		return parameters;
	}

	/**
	 * Sets the data that describes the content.
	 */
	public void setParameters(Map<String, String> parameters) {
		if(parameters == null) {
			this.parameters = null;
			return;
		}

		this.parameters = new Properties();
		if(parameters != null)
			for(String key : parameters.keySet())
				this.parameters.put(key, parameters.get(key));
	}
	
	/**
	 * Sets the data that describes the content.
	 */
	public void setParameters(Properties parameters) {
		this.parameters = parameters;
	}
	
	/**
	 * 
	 * @param other
	 */
	public void mergeProperties(Properties other) {
		this.parameters.putAll(other);
	}
	
	/**
	 * 
	 * @param other
	 */
	public void mergeProperties(Map<String, String> other) {
		this.parameters.putAll(other);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public ParameterizedData copy() {
		ParameterizedData copy = (ParameterizedData) super.copy();
		if(parameters == null || parameters.isEmpty())
			return copy;
		
		copy.setParameters(new Properties(parameters));
		return copy;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getType() {
		/* Returns the type of the wrapped data */
		return data.getType();
	}

}
