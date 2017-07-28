package com.automationtools.core.scheduler;

import static org.springframework.util.Assert.*;
import static com.automationtools.core.scheduler.Schedule.generateCronExpression;
import java.util.Calendar;
import org.quartz.CronScheduleBuilder;
import org.quartz.ScheduleBuilder;

/**
 * Implementation of {@link Schedule} for scheduling on an hourly-basis.
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class HourlySchedule implements Schedule {
	
	/** Generated serialVersionUID */
	private static final long serialVersionUID = -3041129047947731273L;

	/** Default value for repeatInterval. This mean repeat every hour. */
	private static final int _DEFAULT_REPEAT_INTERVAL = 1;
	
	private int repeatInterval = _DEFAULT_REPEAT_INTERVAL;
	
	@Override
	public ScheduleBuilder<?> build() {
		Calendar c = Calendar.getInstance();
		return CronScheduleBuilder.cronSchedule(
			generateCronExpression("0", String.valueOf(c.get(Calendar.MINUTE)), 
					"0/" + getRepeatInterval(), "1/1", "*", "?", "*"));
	}

	public int getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(int repeatInterval) {
		state(repeatInterval >= _DEFAULT_REPEAT_INTERVAL, 
				"Interval value cannot be less than " + _DEFAULT_REPEAT_INTERVAL);
		
		this.repeatInterval = repeatInterval;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Repeat every " + getRepeatInterval() + " ");
		sb.append(getRepeatInterval() > 1 ? "hours " : "hour ");
		return sb.toString();
	}
	
}
