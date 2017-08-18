package com.automationtools.core.scheduler;

import org.quartz.ScheduleBuilder;

/**
 * Implementation of {@link Schedule} for scheduling on a monthly-basis.
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class MonthlySchedule implements Schedule {

	/** Generated serialVersionUID */
	private static final long serialVersionUID = 309407918411305522L;

	@Override
	public ScheduleBuilder<?> build() {
		// TODO: Put Implementation 
		throw new UnsupportedOperationException("Monthly scheduling not yet supported");
	}

}
