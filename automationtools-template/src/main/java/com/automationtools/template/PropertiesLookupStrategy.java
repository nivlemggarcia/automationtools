package com.automationtools.template;

import java.util.Properties;

/**
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 */
public abstract interface PropertiesLookupStrategy {
	
	/**
	 * Returns the associated key-value pair property map based on
	 * the {@linkplain Key template key} provided.
	 * 
	 * @param templatekey	
	 * 		Reference {@code Template} identifier
	 */
	public abstract Properties get(Key templatekey);
	
	/**
	 * Performs the operation in which the system will try to remove
	 * the associated property file to a particular {@code Template} 
	 * using the provided {@linkplain Key template key}.
	 * 
	 * @param templatekey
	 * 		The referenced {@code Template} identifier
	 * 
	 * @return
	 * 		Returns <strong>true</strong> iff successfully deleted, 
	 * 		otherwise <strong>false</strong>.		
	 * 
	 * @throws Exception
	 */
	public abstract boolean delete(Key templatekey) throws Exception;
	
	/**
	 * Performs the operation in which the system will try to replace
	 * the content of the associated property file to a particular 
	 * {@code Template} using the provided {@linkplain Key template key}.
	 * 
	 * @param templatekey
	 * 		The referenced {@code Template} identifier
	 * 
	 * @return
	 * 		Returns <strong>true</strong> iff updated successfully, 
	 * 		otherwise <strong>false</strong>.		
	 * 
	 * @throws Exception
	 */
	public abstract boolean update(Key templatekey, Properties newproperties) throws Exception;
	
}
