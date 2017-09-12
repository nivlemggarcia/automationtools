package com.automationtools.core.event.task;

import com.automationtools.core.Task;
import com.automationtools.core.TaskHandlerSupport;

/**
 * {@code TaskExecutionStartedEvent} is fired by {@linkplain TaskHandlerSupport} 
 * in the event when the {@code TaskHandler} finished handling the {@code Task}. 
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public class TaskExecutionEndedEvent {

	/**
	 * The executed task.
	 */
	private Task<?> task;
	
	/**
	 * Timestamp when the execution ended.
	 */
	private long timestamp;
	
	/**
	 * Creates a new instance with the supplied executed {@code Task}.
	 */
	public TaskExecutionEndedEvent(Task<?> task) {
		this.task = task;
		setTimestamp(System.currentTimeMillis());
	}

	/**
	 * Returns the executed task.
	 */
	public Task<?> getTask() {
		return task;
	}
	
	/**
	 * Returns the timestamp when the execution ended.
	 */
	public long getTimestamp() {
		return timestamp;
	}
	
	/**
	 * Sets the timestamp when the execution ended.
	 */
	protected void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
