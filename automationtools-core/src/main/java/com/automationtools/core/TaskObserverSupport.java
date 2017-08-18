package com.automationtools.core;

import java.util.Set;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;

/**
 * Provides support for registering {@linkplain TaskObserver task observers}.
 * These observers will be registered in the event when a {@code Task} 
 * is submitted for {@linkplain TaskDispatcher#dispatch(Task) dispatch}. 
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
@Aspect
public class TaskObserverSupport {
	
	/**
	 * Flag that indicates whether to enable the registration of 
	 * all the {@code TaskObserver}s.
	 */
	@Value("${core.tasklistener.enable}")
	private boolean enabled;
	
	/**
	 * {@code TaskObserver}s that will registered to the dispatched {@code Task}s.
	 */
	private Set<TaskObserver> taskObservers;
	
	/**
	 * Intercepts {@linkplain TaskDispatcher#dispatch(Task)} to 
	 * register the {@linkplain #getTaskObservers() task observers} 
	 * before actual execution.
	 */
	@Around("execution(* com.automationtools.core.TaskDispatcher+.dispatch(..)) && args(task)")
	public Object around(ProceedingJoinPoint pjp, Task<?> task) throws Throwable {
		if(enabled && hasObservers())
			/* Start listening to Task events */
			taskObservers.forEach((o) -> o.registerTo(task));
			
		return pjp.proceed();
	}
	
	/**
	 * Sets the observers for the dispatched {@code Task}s.
	 */
	public void setTaskObservers(Set<TaskObserver> taskObservers) {
		this.taskObservers = taskObservers;
	}
	
	/**
	 * Returns the observers for the dispatched {@code Task}s.
	 */
	public Set<TaskObserver> getTaskObservers() {
		return taskObservers;
	}
	
	/**
	 * Checks if there are {@code Task} {@code Observers}.
	 */
	protected boolean hasObservers() {
		return taskObservers != null && !taskObservers.isEmpty();
	}
	
	/**
	 * Sets the flag that indicates whether to enable the registration of 
	 * all the {@code TaskObserver}s.
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * Returns the flag that indicates whether to enable the registration of 
	 * all the {@code TaskObserver}s.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
