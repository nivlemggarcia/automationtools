package com.automationtools.core.scheduler;

import static org.springframework.util.Assert.*;
import static com.automationtools.core.scheduler.Schedule.generateCronExpression;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.quartz.CronScheduleBuilder;
import org.quartz.ScheduleBuilder;

/**
 * Implementation of {@link Schedule} for scheduling on a daily-basis.
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class DailySchedule implements Schedule {
	
	/** Generated serialVersionUID */
	private static final long serialVersionUID = -5909662327249145829L;

	/** 
	 * Default value for repeatInterval. This means repeat everyday. 
	 */
	private static final int _DEFAULT_REPEAT_INTERVAL = 1;
	
	/**
	 * Repeat interval in terms of number of days
	 */
	private int repeatInterval = _DEFAULT_REPEAT_INTERVAL;
	
	/**
	 * Flag for considering only the weekdays. 
	 * When set to <em>true</em>, the repeatInterval will be neglected.
	 */
	private boolean weekdayOnly = false;
	
	/**
	 * Time of the day
	 */
	private Date time;

	@Override
	public ScheduleBuilder<?> build() {
		Calendar c = Calendar.getInstance();
		c.setTime(getTime());
		
		if(isWeekdayOnly()) 
			return CronScheduleBuilder.cronSchedule(
				generateCronExpression("0", String.valueOf(c.get(Calendar.MINUTE)), 
					String.valueOf(c.get(Calendar.HOUR_OF_DAY)), "?", "*", "MON-FRI", "*"));
		else 
			return CronScheduleBuilder.cronSchedule(
				generateCronExpression("0", String.valueOf(c.get(Calendar.MINUTE)), 
					String.valueOf(c.get(Calendar.HOUR_OF_DAY)), "1/" + getRepeatInterval(), "*", "?", "*"));
		
	}
	
	/**
	 * Returns the repeat interval in terms of number of days.
	 * 
	 * @return
	 * 		The repeat interval in terms of number of days
	 */
	public int getRepeatInterval() {
		return repeatInterval;
	}

	/**
	 * Sets the repeat interval in terms of number of days.
	 * 
	 * @param repeatInterval
	 * 		The repeat interval in terms of number of days
	 */
	public void setRepeatInterval(int repeatInterval) {
		state(repeatInterval >= _DEFAULT_REPEAT_INTERVAL, 
				"Interval value cannot be less than " + _DEFAULT_REPEAT_INTERVAL);
		
		this.repeatInterval = repeatInterval;
	}

	/**
	 * Sets the time of the day.
	 * 
	 * @param time
	 * 		Time of the day.
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	
	/**
	 * Returns the time of the day.
	 * 
	 * @return
	 * 		Time of the day.
	 */
	public Date getTime() {
		return time;
	}
	
	protected String getTimeAsString() {
		return new SimpleDateFormat("hh:mm a").format(getTime());
	}
	
	public void setWeekdayOnly(boolean weekdayOnly) {
		this.weekdayOnly = weekdayOnly;
	}
	
	public boolean isWeekdayOnly() {
		return weekdayOnly;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(isWeekdayOnly()) {
			sb.append("Repeat every weekday (Monday - Friday) ");
		} else {
			sb.append("Repeat ");
			sb.append(getRepeatInterval() > 1 ? "every " + getRepeatInterval() + " days " : "everyday ");
		}
		
		sb.append("at " + getTimeAsString() + " ");
		return sb.toString();
	}

}
