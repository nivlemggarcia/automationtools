package com.automationtools.core.listener;

import static com.automationtools.util.Utilities.*;
import static com.automationtools.core.Status.Default.*;
import java.util.Observable;

import com.automationtools.core.Status;
import com.automationtools.core.Task;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public abstract class TaskStatusListener extends AbstractTaskListener {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable o, Object arg) {
		/* Ignore all other than Task */
		if(!(o instanceof Task))
			return;
		
		Task<?> task = (Task<?>) o;
		processEvent(new TaskStatusChangedEvent(task.getId(), (Status) arg, task.getStatus()));
		
		/* Stop listening once this task is completed */
		if(equalsAny(task.getStatus(), FAILED, STOPPED, SUCCESSFUL))
			unregisterTo(task);
	}
	
	/**
	 * This method is called whenever a change in {@code Task}'s status is observed.
	 */
	protected abstract void processEvent(TaskStatusChangedEvent event);

}
