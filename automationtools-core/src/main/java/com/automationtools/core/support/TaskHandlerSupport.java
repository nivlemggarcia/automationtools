package com.automationtools.core.support;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.automationtools.core.Status;
import com.automationtools.core.Task;

/**
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 */
@Component
@Aspect
public class TaskHandlerSupport {
	
	private static final Logger log = LoggerFactory.getLogger(TaskHandlerSupport.class);
	
	@Around("execution(* com.automationtools.core.TaskHandler+.apply(..)) && args(arg)")
	public Object around(ProceedingJoinPoint pjp, Task<?> arg) throws Throwable {
		log.info("Execution Started [{}]", arg.getId().toString());
		
		Timer timer = new Timer();
		timer.start();
		
		try {
			arg.setStatus(Status.Default.IN_PROGRESS);
			Object result = pjp.proceed();
			arg.setStatus(Status.Default.SUCCESSFUL);
			return result;
		} catch (Exception e) {
			arg.setStatus(Status.Default.FAILED);
			arg.setFailureCause(e);
			throw e;
		} finally {
			timer.stop();
			
			/* Compute time taken to execute */
			long elapsedTime = timer.getElapsedTime();
			arg.setTimeElapsed(elapsedTime);
			long minutes = MILLISECONDS.toMinutes(elapsedTime);
			long seconds = MILLISECONDS.toSeconds(elapsedTime) - MINUTES.toSeconds(minutes);
			String timelapsed = String.format("%d %s, %d %s", 
					minutes, (minutes > 1 ? "minutes" : "minute"), 
					seconds, (seconds > 1 ? "seconds" : "second"));
			
			/* Compute the log message */
			log.info("Execution {} [{}]. Time Elapsed: {}. [{} millis]", 
					arg.getStatus(), arg.getId().toString(), timelapsed, elapsedTime);
		}
		
	}
	
	/**
	 * Used for computing the amount of time (in millis) 
	 * that passes from before and after the execution.
	 * 
	 * @author Melvin Garcia
	 * @since 1.0.0
	 */
	protected class Timer {
		
		private long startTime = 0;
		private long stopTime = 0;
		private long elapsedTime = 0;
		
		public void start() {
			startTime = System.currentTimeMillis();
		}

		public void stop() {
			stopTime = System.currentTimeMillis();
			updateElapsedTime();
		}

		public long getStartTime() {
			return startTime;
		}

		public long getStopTime() {
			return stopTime;
		}

		public long getElapsedTime() {
			return elapsedTime;
		}

		public void reset() {
			startTime = 0;
			stopTime = 0;
			elapsedTime = 0;
		}
		
		private void updateElapsedTime() {
			elapsedTime = elapsedTime + (stopTime - startTime);
			stopTime = 0;
			startTime = 0;
		}
		
	}
	
}
