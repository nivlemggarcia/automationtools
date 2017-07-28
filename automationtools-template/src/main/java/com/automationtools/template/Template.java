package com.automationtools.template;

import static org.springframework.util.Assert.*;
import com.automationtools.core.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This serves as a data model of a file. 
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 *
 * @param <T>	The type of data that this {@code Template} holds.
 */
public class Template {

	/**
	 * Used as identifier of this {@code Template} 
	 */
	private Key key;
	
	/**
	 * This is where the <strong>raw</strong> data 
	 * of this {@code Template} came from.
	 */
	@JsonIgnore
	private TemplateRepository source;
	
	/**
	 * Represents the <strong>raw</strong> data 
	 * of this {@code Template}.
	 */
	private Data data;
	
	/**
	 * Default Constructor
	 */
	public Template() {
		super();
	}
	
	/**
	 * Constructor that accepts the <strong>raw</strong> data 
	 * of this {@code Template}.
	 */
	public Template(Data data) {
		super();
		setData(data);
	}
	
	/**
	 * Factory method for creating a new instance of {@code Template}
	 * using the wrapped {@code Data} object.
	 * 
	 * <p>
	 * Using this is similar to: <pre>new Template(Data);</pre>
	 * </p>
	 */
	public static Template wrap(Data data) {
		return new Template(data);
	}

	/**
	 * Performs the operation in which the system will try to remove
	 * the reference of this {@code Template} data from the location 
	 * where this {@code Template} came from.
	 * 
	 * @return
	 * 		Returns <strong>true</strong> iff successfully deleted, 
	 * 		otherwise <strong>false</strong>.		
	 * 
	 * @throws Exception
	 */
	public boolean delete() throws Exception {
		if(source == null)
			return false;
		
		return source.delete(this);
	}
	
	/**
	 * Performs the operation in which the system will try to replace
	 * the old data with the latest information that this instance holds.
	 * 
	 * @return
	 * 		Returns <strong>true</strong> iff updated successfully, 
	 * 		otherwise <strong>false</strong>.		
	 * 
	 * @throws Exception
	 */
	public boolean update() throws Exception {
		if(source == null)
			return false;
		
		return source.update(this);
	}

	/**
	 * Returns the identifier of this {@code Template} 
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * Sets the identifier of this {@code Template} 
	 */
	public void setKey(Key key) {
		notNull(key, "Template key cannot be null");
		this.key = key;
	}

	/**
	 * Returns the object that holds the reference 
	 * to a place where the data that this {@code Template}
	 * holds come from.
	 */
	@JsonIgnore
	public TemplateRepository getSource() {
		return source;
	}

	/**
	 * Sets the object that holds the reference 
	 * to a place where the data that this {@code Template}
	 * holds come from.
	 */
	void setSource(TemplateRepository source) {
		notNull(source, "TemplateRepository cannot be null");
		this.source = source;
	}
	
	/**
	 * Returns the <strong>raw</strong> data 
	 * that this {@code Template} represents.
	 */
	public Data getData() {
		return data;
	}
	
	/**
	 * Sets the <strong>raw</strong> data 
	 * that this {@code Template} represents.
	 */
	public void setData(Data data) {
		notNull(data, "Data cannot be null");
		this.data = data;
	}
	
}
