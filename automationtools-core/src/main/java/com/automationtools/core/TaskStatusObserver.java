package com.automationtools.core;

import static com.automationtools.util.Utilities.*;
import static com.automationtools.core.Status.Default.*;
import java.util.Observable;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public abstract class TaskStatusObserver extends AbstractTaskObserver {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable o, Object arg) {
		/* Ignore all other than Task */
		if(!(o instanceof Task))
			return;
		
		Task<?> task = (Task<?>) o;
		fireTaskStatusChanged(new TaskStatusChangedEvent(task.getId(), (Status) arg, task.getStatus()));
		
		/* Stop listening once this task is completed */
		if(equalsAny(task.getStatus(), FAILED, STOPPED, SUCCESSFUL))
			unregisterTo(task);
	}
	
	/**
	 * This method is called whenever a change in {@code Task}'s status is observed.
	 */
	protected abstract void fireTaskStatusChanged(TaskStatusChangedEvent event);

}
