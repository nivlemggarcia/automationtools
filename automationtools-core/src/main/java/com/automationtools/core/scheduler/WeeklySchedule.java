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
 * @author 	Melvin Garcia
 * @since 	1.0.0
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
	
	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * Returns <em>true</em> if scheduled every Monday, otherwise false.
	 */
	public boolean isMonday() {
		return monday;
	}

	/**
	 * Sets the flag for scheduling every Monday
	 */
	public void setMonday(boolean monday) {
		this.monday = monday;
	}

	/**
	 * Returns <em>true</em> if scheduled every Tuesday, otherwise false.
	 */
	public boolean isTuesday() {
		return tuesday;
	}

	/**
	 * Sets the flag for scheduling every Tuesday
	 */
	public void setTuesday(boolean tuesday) {
		this.tuesday = tuesday;
	}

	/**
	 * Returns <em>true</em> if scheduled every Wednesday, otherwise false.
	 */
	public boolean isWednesday() {
		return wednesday;
	}

	/**
	 * Sets the flag for scheduling every Wednesday
	 */
	public void setWednesday(boolean wednesday) {
		this.wednesday = wednesday;
	}

	/**
	 * Returns <em>true</em> if scheduled every Thursday, otherwise false.
	 */
	public boolean isThursday() {
		return thursday;
	}

	/**
	 * Sets the flag for scheduling every Thursday
	 */
	public void setThursday(boolean thursday) {
		this.thursday = thursday;
	}

	/**
	 * Returns <em>true</em> if scheduled every Friday, otherwise false.
	 */
	public boolean isFriday() {
		return friday;
	}

	/**
	 * Sets the flag for scheduling every Friday
	 */
	public void setFriday(boolean friday) {
		this.friday = friday;
	}

	/**
	 * Returns <em>true</em> if scheduled every Saturday, otherwise false.
	 */
	public boolean isSaturday() {
		return saturday;
	}

	/**
	 * Sets the flag for scheduling every Saturday
	 */
	public void setSaturday(boolean saturday) {
		this.saturday = saturday;
	}

	/**
	 * Returns <em>true</em> if scheduled every Sunday, otherwise false.
	 */
	public boolean isSunday() {
		return sunday;
	}

	/**
	 * Sets the flag for scheduling every Sunday
	 */
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
