package com.automationtools.core;

import static org.springframework.util.Assert.*;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Required;
import com.automationtools.exception.NoSuitableHandlerFoundException;

/**
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public abstract class TaskDispatcherBase implements TaskDispatcher {
	
	/**
	 * Object used for creating new instances of {@code Task}.
	 */
	private TaskHandlerFactory factory;
	
	/**
	 * Change listeners for the dispatched {@code Task}s.
	 */
	private Set<AbstractTaskObserver> taskObservers;
	
	/**
	 * Executes submitted {@link Callable} tasks.
	 * 
	 * @param command	Callable task subject for execution.
	 */
	protected abstract <R> Future<R> execute(Callable<R> command);
	
	/**
	 * 
	 * @param task
	 * @param handler
	 * @return
	 */
	protected <T, R> Callable<R> newCommand(Task<T> task, TaskHandler<T, R> consumer) {
		return new Command<T, R>(task, consumer);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T, R> Future<R> dispatch(Task<T> task) throws NoSuitableHandlerFoundException {
		notNull(task, "Cannot dispatch a null Task");
		state(factory != null, "HandlerFactory cannot be null.");
		
		/* Get appropriate TaskHandler based on the type of data 
		 * that this Task holds. */
		TaskHandler<T, R> consumer = factory.get(task);
		
		/* Start listening to Task events */
		if(hasObservers())
			taskObservers.forEach((o) -> o.registerTo(task));
		
		return execute(newCommand(task, consumer));
	}
	
	/**
	 * Sets the object used for creating new instances of {@code Task}.
	 */
	@Required
	public void setFactory(TaskHandlerFactory factory) {
		notNull(factory, "TaskHandlerFactory cannot be null");
		this.factory = factory;
	}
	
	/**
	 * Returns the object used for creating new instances of {@code Task}.
	 */
	public TaskHandlerFactory getFactory() {
		return factory;
	}
	
	/**
	 * Sets the listeners for the dispatched {@code Task}s.
	 */
	public void setTaskObservers(Set<AbstractTaskObserver> taskObservers) {
		this.taskObservers = taskObservers;
	}
	
	/**
	 * Returns the listeners for the dispatched {@code Task}s.
	 */
	public Set<AbstractTaskObserver> getTaskObservers() {
		return taskObservers;
	}
	
	/**
	 * Checks if there are {@code Task} {@code Observers}.
	 */
	protected boolean hasObservers() {
		return taskObservers != null && !taskObservers.isEmpty();
	}
	
	/**
	 * 
	 * @author Melvin Garcia
	 * @since 1.0.0
	 * @param <T>
	 */
	class Command<T, R> implements Callable<R> {
		
		protected Task<T> task;
		
		protected TaskHandler<T, R> consumer;
		
		public Command(Task<T> task, TaskHandler<T, R> consumer) {
			this.task = task;
			this.consumer = consumer;
		}
		
		@Override
		public R call() throws Exception {
			return consumer.apply(task).getData();
		}

	}
	
}
