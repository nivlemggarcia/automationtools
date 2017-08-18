package com.automationtools.core.event;

import java.util.UUID;

import com.automationtools.core.Status;
import com.automationtools.core.Task;
import com.automationtools.core.TaskStatusObserver;

/**
 * {@code TaskStatusChangedEvent} a event-type object that 
 * is fired by the {@linkplain TaskStatusObserver} whenever 
 * a change in {@code Status} in any of the {@code Task}s 
 * has been observed.
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public class TaskStatusChangedEvent {
	
	/**
	 * The {@linkplain Task#getId() task id} 
	 * where this event originated.
	 */
	private UUID id;
	
	/**
	 * The old {@code Status}.
	 */
	private Status oldStatus;
	
	/**
	 * The new {@code Status}.
	 */
	private Status newStatus;
	
	/**
	 * Creates new {@code TaskStatusChangedEvent} with the supplied arguments. 
	 * 
	 * @param id
	 * 			The {@linkplain Task#getId() task id} 
	 * 			where this event originated.
	 * @param oldStatus
	 * 			The old {@code Status}.
	 * @param newStatus
	 * 			The new {@code Status}.
	 */
	public TaskStatusChangedEvent(UUID id, Status oldStatus, Status newStatus) {
		super();
		this.id = id;
		this.oldStatus = oldStatus;
		this.newStatus = newStatus;
	}

	/**
	 * Returns the {@linkplain Task#getId() task id} 
	 * where this event originated.
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * Returns the old {@code Status}.
	 */
	public Status getOldStatus() {
		return oldStatus;
	}

	/**
	 * Returns the new {@code Status}.
	 */
	public Status getNewStatus() {
		return newStatus;
	}
	
	@Override
	public String toString() {
		return String.format("Task %s status has changed: %s -> %s", 
				getId().toString(), 
				getOldStatus(), 
				getNewStatus());
	}
	
}
