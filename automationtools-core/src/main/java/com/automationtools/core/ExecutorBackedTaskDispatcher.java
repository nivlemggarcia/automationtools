package com.automationtools.core;

import static org.springframework.util.Assert.*;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.beans.factory.annotation.Required;

/**
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class ExecutorBackedTaskDispatcher extends TaskDispatcherBase {
	
	/**
	 * The backing {@linkplain Executor} that executes runnable tasks.
	 */
	private ExecutorService executor;
	
	/**
	 * Flag that indicates discriminated logging is enforced.
	 */
	private boolean discriminated;
	
	/**
	 * Default Constructor.
	 */
	public ExecutorBackedTaskDispatcher() {
		super();
	}
	
	@Override
	protected <T, R> Callable<R> newCommand(Task<T> task, TaskHandler<T, R> handler) {
		Callable<R> c = super.newCommand(task, handler);
		if(isDiscriminated())
			c =  new MdcAwareCommand<T, R>(c, task.getId().toString());
		
		return c;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected <R> Future<R> execute(Callable<R> command) {
		state(executor != null, "Executor cannot be null.");
		return executor.submit(command);
	}
	
	/**
	 * Initiates an orderly shutdown in which previously 
	 * submitted tasks are executed, but no new tasks will 
	 * be accepted. Invocation has no additional effect if 
	 * already shut down. 
	 */
	public void shutdown() {
		executor.shutdown();
	}
	
	/**
	 * Attempts to stop all actively executing tasks, halts 
	 * the processing of waiting tasks, and returns a list 
	 * of the tasks that were awaiting execution. 
	 */
	public void shutdownNow() {
		executor.shutdownNow();
	}
	
	/**
	 * Sets the backing {@linkplain ExecutorService executor}.
	 */
	@Required
	public void setExecutor(ExecutorService executor) {
		notNull(executor, "ExecutorService cannot be null");
		this.executor = executor;
	}
	
	/**
	 * Returns the backing {@linkplain ExecutorService executor}.
	 */
	public ExecutorService getExecutor() {
		return executor;
	}
	
	/**
	 * Returns the flag that indicates discriminated logging is enforced.
	 */
	public boolean isDiscriminated() {
		return discriminated;
	}
	
	/**
	 * Sets the flag that indicates discriminated logging is enforced.
	 */
	public void setDiscriminated(boolean discriminated) {
		this.discriminated = discriminated;
	}
	
	/**
	 * {@code MdcAwareCommand} is a type of {@linkplain Callable} that 
	 * performs mapped diagnostic context (MDC) inheritance to discriminate logging
	 * per {@linkplain Task#getId() transaction}.
	 * 
	 * <p>
	 * In multi-thread environment, if the child is created with the 
	 * basic Thread + Runnable way, the child thread will automatically 
	 * inherit parent’s MDC. But if using executor as thread management, 
	 * The MDC inheritance is not warranted.
	 * </p>
	 * 
	 * <p>
	 * This is what <a href="https://logback.qos.ch/manual/mdc.html#managedThreads">
	 * Logback's manual</a> has to say about using MDC in such an environment: 
	 * </p>
	 * 
	 * <blockquote>
	 * <p><i>
	 * A copy of the mapped diagnostic context can not always be 
	 * inherited by worker threads from the initiating thread. 
	 * This is the case when <tt>java.util.concurrent.Executors</tt> 
	 * is used for thread management. For instance, 
	 * <tt>newCachedThreadPool</tt> method creates a <tt>ThreadPoolExecutor</tt> 
	 * and like other thread pooling code, it has intricate 
	 * thread creation logic.
	 * </i></p>
	 * </blockquote>
	 * 
	 * <blockquote>
	 * <p><i>
	 * In such cases, it is recommended that <tt>MDC.getCopyOfContextMap()</tt>
	 * is invoked  on the original (master) thread before submitting 
	 * a task to the executor. When the task runs, as its first action, 
	 * it should invoke <tt>MDC.setContextMapValues()</tt> to associate the 
	 * stored copy of the original MDC values with the new Executor 
	 * managed thread.
	 * </i></p>
	 * </blockquote>
	 * 
	 * @author 	Melvin Garcia
	 */
	class MdcAwareCommand<T, R> implements Callable<R> {
		
		private static final String _KEY = "id";
		
		private Callable<R> wrapped;
		
		private String discriminatingValue;
		
		/**
		 * Reference to the Parent's MDC.
		 */
		private Map<String, String> contextMap;

		public MdcAwareCommand(Callable<R> wrapped, String discriminatingValue) {
			this.wrapped = wrapped;
			this.discriminatingValue = discriminatingValue;
			/* Keep a copy of the parent's MDC. Effectively, inheriting it. */
			contextMap = MDC.getCopyOfContextMap();
		}
		
		@Override
		public R call() throws Exception {
			if(contextMap != null)
				MDC.setContextMap(contextMap);
			
			MDCCloseable closeable = MDC.putCloseable(_KEY, discriminatingValue);
			try {
				return wrapped.call();
			} finally {
				closeable.close();
			}
		}
		
	}
	
}
