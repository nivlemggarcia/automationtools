package com.automationtools.core.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

/**
 * A type of {@code JobListenerSupport} that will listen only to the 
 * specified {@code Job} type. The subclasses will have to specify the 
 * {@linkplain TypeSupportingJobListener#getSupportedType() supported type}.
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public abstract class TypeSupportingJobListener extends JobListenerSupport {
	
	/**
	 * Type of {@code Job} in which this {@code JobListener} will listen to.
	 */
	public abstract Class<? extends Job> getSupportedType();
	
	/**
	 * Check if the job type created for this execution matches the supported 
	 * job type.
	 * 
	 * @param job The instance of the {@code Job} that was created for this execution
	 * 
	 * @return {@code True} only if the job type created for this 
	 * execution matches the supported  job type. Otherwise, {@code False}.
	 */
	protected boolean isAccepted(Job job) {
		/* Ignore other types of Job */
		return getSupportedType().isInstance(job);
	}
	
	@Override
	public final void jobExecutionVetoed(JobExecutionContext context) {
		if(isAccepted(context.getJobInstance()))
			doJobExecutionVetoed(context);
	}
	
	
	/**
	 * <p>
     * Called when supported Job type was about to be executed (an associated {@code Trigger}
     * has occurred), but a {@code TriggerListener} vetoed it's execution.
     * </p>
	 */
	public void doJobExecutionVetoed(JobExecutionContext context) {
		/* Default: do nothing */
	}
	
	@Override
	public final void jobToBeExecuted(JobExecutionContext context) {
		if(isAccepted(context.getJobInstance()))
			doJobToBeExecuted(context);
	}
	
	/**
	 * <p>
     * Called when supported Job type was about to be executed (an associated {@code Trigger}
     * has occurred).
     * </p>
     * 
     * <p>
     * This method will not be invoked if the execution of the supported Job type was vetoed
     * by a {@code TriggerListener}.
     * </p>
	 */
	public void doJobToBeExecuted(JobExecutionContext context) {
		/* Default: do nothing */
	}
	
	@Override
	public final void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		if(isAccepted(context.getJobInstance()))
			doJobWasExecuted(context, jobException);
	}
	
	/**
     * <p>
     * Called when supported Job type has been executed, and be for the associated <code>Trigger</code>'s
     * <code>triggered(xx)</code> method has been called.
     * </p>
     */
	public void doJobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		/* Default: do nothing */
	}

}
