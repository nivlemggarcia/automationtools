package com.automationtools.core.backup;

import java.util.Set;

import com.automationtools.template.Template;

/**
 * Implementation of {@code Source} represents a place from 
 * which instances of {@linkplain Template} can be obtained.
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 */
public abstract class Source {
	
	/**
	 * Returns a set of {@code Template} instances that is obtained 
	 * in this particular {@code Source}.
	 * 
	 * @throws Exception	
	 * 		An error occurred while reading/parsing the {@code Template}s
	 */
	public abstract Set<Template<?>> load() throws Exception;
	
	/**
	 * Performs the operation in which the system will try to remove
	 * the {@code Template} data from the location which this source 
	 * represents.
	 * 
	 * @param arg
	 * 		{@code Template} instance that is subject for deletion
	 * 
	 * @return
	 * 		Returns <strong>true</strong> iff successfully deleted, 
	 * 		otherwise <strong>false</strong>.		
	 * 
	 * @throws Exception
	 */
	public abstract boolean delete(Template<?> arg) throws Exception;
	
	/**
	 * Performs the operation in which the system will try to replace
	 * the {@code Template} data with the latest information.
	 * 
	 * @param arg
	 * 		{@code Template} data containing the latest information.
	 * 
	 * @return
	 * 		Returns <strong>true</strong> iff updated successfully, 
	 * 		otherwise <strong>false</strong>.		
	 * 
	 * @throws Exception
	 */
	public abstract boolean update(Template<?> arg) throws Exception;
	
}
