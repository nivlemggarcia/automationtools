package com.automationtools.core.scheduler;

import static com.automationtools.core.scheduler.Schedule.generateCronExpression;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;
import org.quartz.CronScheduleBuilder;
import org.quartz.ScheduleBuilder;

/**
 * Implementation of {@link Schedule} for scheduling on a weekly-basis.
 * 
 * @author Melvin Garcia
 * @since v.1.0
 */
public class WeeklySchedule implements Schedule {

	/** Generated serialVersionUID */
	private static final long serialVersionUID = -3339137913942016311L;
	
	enum DaysOfWeek {
		
		MONDAY("MON"),
		
		TUESDAY("TUE"),
		
		WEDNESDAY("WED"),
		
		THURSDAY("THU"),
		
		FRIDAY("FRI"),
		
		SATURDAY("SAT"),
		
		SUNDAY("SUN"),
		
		;
		
		private String text;
		
		private DaysOfWeek(String text) {
			this.text = text;
		}
		
		@Override
		public String toString() {
			return this.text;
		}
	}
	
	/**
	 * Flag for scheduling every Monday
	 */
	private boolean monday;
	
	/**
	 * Flag for scheduling every Tuesday
	 */
	private boolean tuesday;
	
	/**
	 * Flag for scheduling every Wednesday
	 */
	private boolean wednesday;
	
	/**
	 * Flag for scheduling every Thursday
	 */
	private boolean thursday;
	
	/**
	 * Flag for scheduling every Friday
	 */
	private boolean friday;
	
	/**
	 * Flag for scheduling every Saturday
	 */
	private boolean saturday;
	
	/**
	 * Flag for scheduling every Sunday
	 */
	private boolean sunday;
	
	/**
	 * Time of the day
	 */
	private Date time;
	
	@Override
	public ScheduleBuilder<?> build() {
		/* Enabled Days of the week */
		Set<DaysOfWeek> daysOfWeek = EnumSet.noneOf(DaysOfWeek.class);
		if(isMonday())
			daysOfWeek.add(DaysOfWeek.MONDAY);
		if(isTuesday())
			daysOfWeek.add(DaysOfWeek.TUESDAY);
		if(isWednesday())
			daysOfWeek.add(DaysOfWeek.WEDNESDAY);
		if(isThursday())
			daysOfWeek.add(DaysOfWeek.THURSDAY);
		if(isFriday())
			daysOfWeek.add(DaysOfWeek.FRIDAY);
		if(isSaturday())
			daysOfWeek.add(DaysOfWeek.SATURDAY);
		if(isSunday())
			daysOfWeek.add(DaysOfWeek.SUNDAY);
		
		Calendar c = Calendar.getInstance();
		c.setTime(getTime());
		
		StringBuilder buf = new StringBuilder();
		boolean first = true;
		
		/* Using EnumSet enables AutoSorting based on its position in its enum 
		 * declaration, where the initial constant is assigned an ordinal of zero */
        for(DaysOfWeek d : daysOfWeek) {
            String val = d.toString();
            if (!first) 
                buf.append(",");
            
            buf.append(val);
            first = false;
        }
        
        return CronScheduleBuilder.cronSchedule(generateCronExpression("0", String.valueOf(c.get(Calendar.MINUTE)), 
					String.valueOf(c.get(Calendar.HOUR_OF_DAY)), "?", "*", buf.toString(), "*"));
	}

	public boolean isMonday() {
		return monday;
	}

	public void setMonday(boolean monday) {
		this.monday = monday;
	}

	public boolean isTuesday() {
		return tuesday;
	}

	public void setTuesday(boolean tuesday) {
		this.tuesday = tuesday;
	}

	public boolean isWednesday() {
		return wednesday;
	}

	public void setWednesday(boolean wednesday) {
		this.wednesday = wednesday;
	}

	public boolean isThursday() {
		return thursday;
	}

	public void setThursday(boolean thursday) {
		this.thursday = thursday;
	}

	public boolean isFriday() {
		return friday;
	}

	public void setFriday(boolean friday) {
		this.friday = friday;
	}

	public boolean isSaturday() {
		return saturday;
	}

	public void setSaturday(boolean saturday) {
		this.saturday = saturday;
	}

	public boolean isSunday() {
		return sunday;
	}

	public void setSunday(boolean sunday) {
		this.sunday = sunday;
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

	/**
	 * Sets the time of the day.
	 * 
	 * @param time
	 * 		Time of the day.
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	
}
