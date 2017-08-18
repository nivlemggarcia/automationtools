package com.automationtools.core;

import java.util.Observer;

/**
 * {@code TaskObserver} observes the changes in {@code Task} objects.
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public abstract class TaskObserver implements Observer {
	
	/**
	 * Start observing to the specified {@code Task}.
	 */
	public void registerTo(Task<?> task) {
		task.addObserver(this);
	}
	
	/**
	 * Stop observing to the specified {@code Task}.
	 */
	protected void unregisterTo(Task<?> task) {
		task.deleteObserver(this);
	}

}
