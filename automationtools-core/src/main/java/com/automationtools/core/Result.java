package com.automationtools.core;

import java.util.UUID;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 * @param <T>
 */
public class Result<T> {
	
	private T data;
	
	private UUID fromTask;
	
	public Result(UUID fromTask, T data) {
		super();
		this.fromTask = fromTask;
		this.data = data;
	}
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public UUID getFromTask() {
		return fromTask;
	}

	public void setFromTask(UUID fromTask) {
		this.fromTask = fromTask;
	}
	
}
