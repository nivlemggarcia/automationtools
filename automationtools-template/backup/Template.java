package com.automationtools.template;

import org.springframework.util.Assert;
import com.automationtools.context.Data;

/**
 * This serves as a data model of a file. 
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 *
 * @param <T>	The type of data that this {@code Template} holds.
 */
public class Template<T extends Data> {

	/**
	 * Used as identifier of this {@code Template} 
	 */
	private Key key;
	
	/**
	 * This is where the <strong>raw</strong> data 
	 * of this {@code Template} came from.
	 */
	private Repository<Template<?>> source;
	
	/**
	 * Represents the <strong>raw</strong> data 
	 * of this {@code Template}.
	 */
	private T data;
	
	/**
	 * The type of data that this {@code Template} holds.
	 */
	private Class<T> dataType;
	
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
	public Template(T data) {
		super();
		setData(data);
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
		this.key = key;
	}

	/**
	 * Returns the object that holds the reference 
	 * to a place where the data that this {@code Template}
	 * holds come from.
	 */
	public Repository<Template<?>> getSource() {
		return source;
	}

	/**
	 * Sets the object that holds the reference 
	 * to a place where the data that this {@code Template}
	 * holds come from.
	 */
	public void setSource(Repository<Template<?>> source) {
		Assert.notNull(source);
		this.source = source;
	}
	
	/**
	 * Returns the <strong>raw</strong> data 
	 * that this {@code Template} represents.
	 */
	public T getData() {
		return data;
	}
	
	/**
	 * Sets the <strong>raw</strong> data 
	 * that this {@code Template} represents.
	 */
	@SuppressWarnings("unchecked")
	public void setData(T data) {
		Assert.notNull(data);
		this.data = data;
		this.dataType = (Class<T>) data.getClass();
	}
	
	/**
	 * Returns the type of data that this 
	 * {@code Template} holds.
	 */
	public Class<T> getDataType() {
		return dataType;
	}
	
}
