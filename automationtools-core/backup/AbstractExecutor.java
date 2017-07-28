package com.automationtools.core.backup;

import com.automationtools.core.exec.ExecutionContext;
import com.automationtools.core.exec.Executor;
import com.automationtools.core.exec.Status;
import com.automationtools.core.exec.Status.Default;

/**
 * Base class of {@code Executor}.
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 * 
 * @param <T> 	{@linkplain ExecutionContext context's} type
 */

public abstract class AbstractExecutor<T> implements Executor<T> {
	
	/**
	 * {@inheritDoc}
	 */
	public void execute(ExecutionContext<T> arg) {
		try {
			arg.setStatus(Status.Default.IN_PROGRESS);
			doExecute(arg);
			arg.setStatus(Status.Default.SUCCESSFUL);
		} catch (Exception e) {
			arg.setStatus(Status.Default.FAILED);
			arg.setFailureCause(e);
			throw e;
		}
		
		/* Nothing to return */
		return;
		
	}
	
	/**
	 * This is where the actual internal execution is performed.
     * 
	 * @param arg			Execution context
	 */
	abstract void doExecute(ExecutionContext<T> arg);
	
}
