package com.automationtools.core;

import static com.automationtools.util.Utilities.*;
import static com.automationtools.core.Status.Default.*;
import java.util.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.automationtools.core.event.TaskStatusChangedEvent;

/**
 * The class {@code TaskStatusListener} is a concrete implementation 
 * of {@linkplain TaskObserver} that listens to changes of {@code Status} 
 * of {@code Task} objects. 
 * 
 * <p>In this implementation, whenever a change of status is observed, 
 * the details will simply be logged.</p>
 * 
 * <p>The subclasses of this class will have to implement their own 
 * {@linkplain #processEvent(TaskStatusChangedEvent)}</p>
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public class TaskStatusObserver extends TaskObserver {
	
	private static final Logger log = LoggerFactory.getLogger(TaskStatusObserver.class);
	
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
	 * The default behavior is to log the event details.
	 */
	protected void processEvent(TaskStatusChangedEvent event) {
		log.debug(event.toString());
	}

}
