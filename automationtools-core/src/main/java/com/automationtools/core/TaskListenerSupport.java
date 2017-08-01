package com.automationtools.core;

import java.util.Set;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import com.automationtools.core.listener.AbstractTaskListener;

@Aspect
public class TaskListenerSupport {
	
	@Value("${core.tasklistener.enable}")
	private boolean enabled;
	
	/**
	 * Change listeners for the dispatched {@code Task}s.
	 */
	private Set<AbstractTaskListener> taskObservers;
	
	@Around("execution(* com.automationtools.core.TaskDispatcher+.dispatch(..)) && args(task)")
	public Object around(ProceedingJoinPoint pjp, Task<?> task) throws Throwable {
		if(enabled && hasObservers())
			/* Start listening to Task events */
			taskObservers.forEach((o) -> o.registerTo(task));
			
		return pjp.proceed();
	}
	
	/**
	 * Sets the listeners for the dispatched {@code Task}s.
	 */
	public void setTaskObservers(Set<AbstractTaskListener> taskObservers) {
		this.taskObservers = taskObservers;
	}
	
	/**
	 * Returns the listeners for the dispatched {@code Task}s.
	 */
	public Set<AbstractTaskListener> getTaskObservers() {
		return taskObservers;
	}
	
	/**
	 * Checks if there are {@code Task} {@code Observers}.
	 */
	protected boolean hasObservers() {
		return taskObservers != null && !taskObservers.isEmpty();
	}
	
}
