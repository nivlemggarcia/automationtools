package com.automationtools.core.listener;

import java.util.Observer;

import com.automationtools.core.Task;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public abstract class AbstractTaskListener implements Observer {
	
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
