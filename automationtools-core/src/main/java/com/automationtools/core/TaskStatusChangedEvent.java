package com.automationtools.core;

import java.util.UUID;

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
