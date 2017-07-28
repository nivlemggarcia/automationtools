package com.automationtools.core;

import java.util.Observer;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public abstract class AbstractTaskObserver implements Observer {
	
	/**
	 * Start observing to the specified {@code Task}.
	 */
	protected void registerTo(Task<?> task) {
		task.addObserver(this);
	}
	
	/**
	 * Stop observing to the specified {@code Task}.
	 */
	protected void unregisterTo(Task<?> task) {
		task.deleteObserver(this);
	}

}
