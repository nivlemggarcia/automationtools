package com.automationtools.core;

import static org.springframework.util.Assert.*;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.automationtools.exception.ExecutionFailedException;

/**
 * The class {@code DelayedHandler} is a decorator class for 
 * {@code Handler} type.
 * 
 * <p>
 * This class is used to put delays before the actual execution 
 * of the wrapped {@code Handler} instance.
 * <p>
 *
 * @param <T> 	Execution context type
 * 
 * @author 	Melvin Garcia
 * @see		ExecutorDecorator
 * @since 	1.0.0
 */
public class DelayedHandler<T> extends HandlerDecorator<T> {
	
	private static final Logger log = LoggerFactory.getLogger(DelayedHandler.class);
	
	/**
	 * Time unit value of the delay
	 */
	private TimeUnit unit = TimeUnit.SECONDS;
	
	/**
	 * Numerical value of the delay
	 */
	private long delay;
	
	/**
	 * Constructor that accepts the wrapped {@code Executor}.
	 */
	public DelayedHandler(Handler<T> wrapped) {
		super(wrapped);
	}
	
	/**
	 * Constructor that accepts the wrapped {@code Executor} 
	 * and the amount of delay.
	 * 
	 * @param wrapped		The wrapped {@code Executor}.
	 * @param unit			The given {@linkplain TimeUnit unit} for the delay.
	 * @param delay			The numerical value of the delay.
	 */
	public DelayedHandler(Handler<T> wrapped, TimeUnit unit, long delay) {
		super(wrapped);
		this.unit = unit;
		this.delay = delay;
	}
	
	@Override
	protected void beforeHandle(Task<T> arg) throws ExecutionFailedException {
		/* Add delay before execution */
		if(delay > 0) {
			log.info("Time Delay is {} {}/s. Waiting ...", getDelay(), unit.toString());
			try {
				unit.sleep(delay);
			} catch (InterruptedException e) {
				throw new ExecutionFailedException(e);
			}
		}
		
		super.beforeHandle(arg);
	}
	
	/**
	 * Returns the numerical value of the delay
	 * 
	 * @return
	 * 		The numerical value of the delay
	 */
	public long getDelay() {
		return delay;
	}
	
	/**
	 * Sets the numerical value of the delay
	 * 
	 * @param delay
	 * 		The numerical value of the delay
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}
	
	/**
	 * Sets the time unit value of the delay
	 * 
	 * @param unit
	 * 		The time unit value of the delay
	 */
	public void setUnit(TimeUnit unit) {
		notNull(unit);
		this.unit = unit;
	}
	
	/**
	 * Returns the time unit value of the delay
	 * 
	 * @return
	 * 		The time unit value of the delay
	 */
	public TimeUnit getUnit() {
		return unit;
	}
	
}
