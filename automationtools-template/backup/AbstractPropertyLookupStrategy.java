package com.automationtools.properties;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import com.automationtools.context.ReadWriteLockSupport;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public abstract class AbstractPropertyLookupStrategy extends ReadWriteLockSupport implements PropertiesLookupStrategy {

	/**
	 * Constructor that accepts boolean to set the 
	 * {@linkplain ReentrantReadWriteLock#isFair() fair policy} 
	 * of {@code ReentrantReadWriteLock}.
	 */
	public AbstractPropertyLookupStrategy(boolean isfair) {
		super(isfair);
	}
	
}
