package com.automationtools.core;

import java.util.concurrent.Future;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Required;

import com.automationtools.core.event.TaskDispatchedEvent;

import reactor.bus.Event;
import reactor.bus.EventBus;

/**
 * Broadcasts the event {@code TaskDispatchedEvent} in the event that
 * a {@code Task} was dispatched by the {@code TaskDispatcher}. 
 * 
 * <p>
 * Reactor's {@code EventBus} is utilized here to accept the event and 
 * notify all the registered {@code TaskDispatchedEvent} {@code Consumer}s
 * in a <em>fire-and-forget</em> manner.
 * </p>
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
@Aspect
public class TaskDispatchedEventProducer {
	
	/**
	 * The reactor event gateway used for broadcasting events.
	 */
	private EventBus eventBus;
	
	/**
	 * Intercepts {@linkplain TaskDispatcher#dispatch(Task)} to 
	 * fire {@code TaskDispatchedEvent}.
	 * 
	 * @param task			The referenced task
	 */
	@Around("execution(* com.automationtools.core.TaskDispatcher+.dispatch(..)) && args(task)")
	public Object beforeDispatch(ProceedingJoinPoint pjp, Task<?> task) throws Throwable {
		Object returnObj = pjp.proceed();
		eventBus.notify(TaskDispatchedEvent.class, Event.wrap(new TaskDispatchedEvent(task, (Future<?>) returnObj)));
		return returnObj;
	}
	
	/**
	 * Sets the reactor event gateway used for broadcasting events.
	 */
	@Required
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}
	
	/**
	 * Returns the reactor event gateway used for broadcasting events.
	 */
	public EventBus getEventBus() {
		return eventBus;
	}
	
}
