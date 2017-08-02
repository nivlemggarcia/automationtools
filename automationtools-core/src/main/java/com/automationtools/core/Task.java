package com.automationtools.core;

import static org.springframework.util.Assert.*;
import java.util.Observable;
import java.util.UUID;
import com.automationtools.util.CreateUuid;

/**
 * A wrapper for an object that is to be passed as an 
 * execution context to a {@linkplain TaskHandler consumer}.
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 *
 * @param <T> The type of the wrapped object.
 */
public class Task<T> extends Observable {
	
	/**
	 * Unique identifier for this {@code Task}.
	 */
	private UUID id;

	/**
	 * Execution Status.
	 */
	private Status status = Status.Default.NOT_STARTED;
	
	/**
	 * Exception thrown during execution.
	 */
	private Exception failureCause;
	
	/**
	 * Amount of time (in millis) that passes from before and 
	 * after the execution.
	 */
	private long timeElapsed;
	
	/**
	 * The wrapped data object
	 */
	private T data;
	
	/**
	 * Constructor that accepts the wrapped data.
	 */
	public Task(T data) {
		setData(data);
		setId(CreateUuid.fromSysdate());
	}
	
	/**
	 * Factory method that wraps the given object as a {@code Task}.
	 * 
	 * @param data	The wrapped {@code Data} object.
	 * @param <T> The type of the wrapped object.
	 * @return		A new instance of {@code Task}.
	 */
	public static <T> Task<T> wrap(T data) {
		return new Task<>(data);
	}
	
	/**
	 * Returns execution status.
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Sets the execution status.
	 */
	protected void setStatus(Status status) {
		Status oldStatus = this.status;
		this.status = status;
		setChanged();
		
		/* Notify listeners as soon as state is changed */
		notifyObservers(oldStatus);
	}

	/**
	 * Returns the amount of time (in millis) that 
	 * passes from before and after the execution.
	 */
	public long getTimeElapsed() {
		return timeElapsed;
	}

	/**
	 * Sets the amount of time (in millis) that 
	 * passes from before and after the execution.
	 */
	protected void setTimeElapsed(long timeElapsed) {
		this.timeElapsed = timeElapsed;
	}

	/**
	 * Returns exception thrown during execution.
	 */
	public Exception getFailureCause() {
		return failureCause;
	}

	/**
	 * Sets the exception thrown during execution.
	 */
	protected void setFailureCause(Exception failureCause) {
		this.failureCause = failureCause;
	}
	
	/**
	 * Returns the wrapped data object.
	 */
	public T getData() {
		return data;
	}
	
	/**
	 * Sets the wrapped data object.
	 */
	protected void setData(T data) {
		notNull(data, "Data cannot be null");
		this.data = data;
	}
	
	/**
	 * Returns the runtime class of the data object.
	 */
	@SuppressWarnings("unchecked")
	public Class<T> getType() {
		return (Class<T>) this.data.getClass();
	}
	
	/**
	 * Returns the unique identifier for this {@code Task}.
	 */
	public UUID getId() {
		return id;
	}
	
	/**
	 * Sets the unique identifier for this {@code Task}.
	 */
	protected void setId(UUID id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		return data.hashCode() ^ id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		
		if(!(obj instanceof Task))
			return false;
		
		Task<?> other = (Task<?>) obj;
		return this.id.equals(other.id) && this.data.equals(other.data);
	}
	
	@Override
	public String toString() {
		return id.toString();
	}
	
}
