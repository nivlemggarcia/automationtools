package com.automationtools.core.event.task;

import com.automationtools.core.Task;
import com.automationtools.core.TaskHandlerSupport;

/**
 * {@code TaskExecutionStartedEvent} is fired by {@linkplain TaskHandlerSupport} 
 * in the event when the {@code TaskHandler} started handling the {@code Task}. 
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public class TaskExecutionStartedEvent {
	
	/**
	 * The task that is subjected to execution.
	 */
	private Task<?> task;
	
	/**
	 * Timestamp when the execution started.
	 */
	private long timestamp;
	
	/**
	 * Creates a new instance with the supplied {@code Task}.
	 */
	public TaskExecutionStartedEvent(Task<?> task) {
		this.task = task;
		setTimestamp(System.currentTimeMillis());
	}

	/**
	 * Returns the task that is subjected to execution.
	 */
	public Task<?> getTask() {
		return task;
	}
	
	/**
	 * Returns the timestamp when the execution started.
	 */
	public long getTimestamp() {
		return timestamp;
	}
	
	/**
	 * Sets the timestamp when the execution started.
	 */
	protected void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
