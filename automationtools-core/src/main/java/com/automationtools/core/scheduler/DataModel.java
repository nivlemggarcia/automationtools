package com.automationtools.core.scheduler;

import java.io.Serializable;

/**
 * A generic model bean representation of objects that can be built. 
 * 
 * @param <T>	Object type of which this DataModel represents
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public interface DataModel<T> extends Serializable {
	
	/**
	 * Where the actual creation of the object that this {@code DataModel} represent
	 * happens. 
	 * 
	 * @return	
	 * 		Instance of the ModelBean this {@code DataModel} represents. 
	 */
	public T build();
	
}
