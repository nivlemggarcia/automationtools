package com.automationtools.core.listener;

import java.util.UUID;

import com.automationtools.core.Status;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public class TaskStatusChangedEvent {
	
	private UUID id;
	
	private Status oldStatus;
	
	private Status newStatus;
	
	public TaskStatusChangedEvent() {
		super();
	}

	public TaskStatusChangedEvent(UUID id, Status oldStatus, Status newStatus) {
		super();
		this.id = id;
		this.oldStatus = oldStatus;
		this.newStatus = newStatus;
	}

	public UUID getId() {
		return id;
	}

	public Status getOldStatus() {
		return oldStatus;
	}

	public Status getNewStatus() {
		return newStatus;
	}
	
}
