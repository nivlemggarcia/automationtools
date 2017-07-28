package com.automationtools.template;

import java.util.Collection;

/**
 * Serves as a central location in which data is stored and managed.
 * 
 * @param <T> 	The type of data that this repository supports.
 * @param <K> 	The type of object that is used as identifier of data.
 * 
 * @author 		Melvin Garcia
 * @since 		1.0.0
 */
public interface Repository<K, T> {
	
	/**
	 * Returns all data of type {@literal <T>} that is stored in 
	 * this repository.
	 */
	public abstract Collection<T> getAll() throws Exception;
	
	/**
	 * Returns data of type {@literal <T>} that is stored in 
	 * this repository which matches the identifier of type {@literal <K>}.
	 */
	public abstract T get(K arg) throws Exception;
	
	/**
	 * Performs the operation in which the system will try to remove
	 * the data from this repository.
	 * 
	 * @param arg
	 * 		data that is subject for deletion
	 * 
	 * @return
	 * 		Returns <i>true</i> iff successfully deleted, 
	 * 		otherwise <i>false</i>.		
	 * 
	 * @throws Exception
	 */
	public abstract boolean delete(T arg) throws Exception;
	
	/**
	 * Performs the operation in which the system will try to replace
	 * the data from this repository with the latest information.
	 * 
	 * @param arg
	 * 		data containing the latest information.
	 * 
	 * @return
	 * 		Returns <i>true</i> iff updated successfully, 
	 * 		otherwise <i>false</i>.		
	 * 
	 * @throws Exception
	 */
	public abstract boolean update(T arg) throws Exception;
	
	/**
	 * Performs the operation in which the system will try to create
	 * a new data from this repository.
	 * 
	 * @param arg
	 * 		new data.
	 * 
	 * @return
	 * 		Returns the identifier of the newly created entry.		
	 * 
	 * @throws Exception
	 */
	public abstract K create(String name, T arg) throws Exception;
	
	/**
	 * Reloads the content of this {@code Repository}.
	 */
	public abstract void reload();
	
}
