package com.automationtools.core.event;

import java.util.concurrent.Future;

import com.automationtools.core.Task;
import com.automationtools.core.TaskDispatcher;

/**
 * {@code TaskDispatchedEvent} is fired in the event when a {@code Task} 
 * is submitted for {@linkplain TaskDispatcher#dispatch(Task) dispatch}. 
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public class TaskDispatchedEvent {
	
	/**
	 * The dispatched task.
	 */
	private Task<?> task;
	
	/**
	 * The {@code Future} that holds the result
	 * of the dispatched task.
	 */
	private Future<?> future;

	/**
	 * Creates a new instance of {@code TaskDispatchedEvent} 
	 * by supplying the dispatched {@code Task}.
	 * 
	 * @param task		The dispatched task.
	 */
	public TaskDispatchedEvent(Task<?> task, Future<?> future) {
		this.task = task;
		this.future = future;
	}
	
	/**
	 * Returns the dispatched {@code Task}.
	 */
	public Task<?> getTask() {
		return task;
	}
	
	/**
	 * Returns the {@code Future} that holds the result
	 * of the dispatched task.
	 */
	public Future<?> getFuture() {
		return future;
	}
	
}
