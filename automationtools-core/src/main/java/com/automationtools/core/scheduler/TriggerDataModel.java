package com.automationtools.core.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

/**
 * The class {@code TriggerDataModel} is a ModelBean that represents a {@link Trigger}
 * which will be created by the {@link TriggerBuilder}.
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class TriggerDataModel implements DataModel<Trigger> {
	
	/** Generated serialVersionUID */
	private static final long serialVersionUID = 337230411140122081L;

	/**
	 * {@code Name} identifier of this Trigger.
	 * 
	 * @see TriggerKey
	 * @see	TriggerBuilder#withIdentity(String, String)
	 */
	private String name;

	/**
	 * {@code Group Name} identifier of this Trigger.
	 * 
	 * @see TriggerKey
	 * @see	TriggerBuilder#withIdentity(String, String)
	 */
	private String group;
	
	/**
	 * The flag that sets the {@code Trigger} to start at the current moment. 
	 * 
	 * @see	TriggerBuilder#startNow()
	 */
	private boolean startNow = true;
	
	/**
	 * The {@code Date} when the {@code Trigger} should start. 
	 * 
	 * @see	TriggerBuilder#startAt(Date)
	 */
	private Date startDate;
	
	/**
	 * The flag that sets the {@code Trigger} to run indefinitely. 
	 * 
	 * @see	TriggerBuilder#endAt(Date)
	 */
	private boolean noEnd = true;
	
	/**
	 * The {@code Date} when the {@code Trigger} should end 
	 * regardless of the remaining repeats. 
	 * 
	 * @see	TriggerBuilder#endAt(Date)
	 */
	private Date endDate;
	
	/**
	 * Priority of the {@code Trigger}.
	 *
	 * @see	TriggerBuilder#withPriority(int)
	 */
	private int priority;
	
	/**
	 * Schedule Model bean.
	 * 
	 * @see	HourlySchedule
	 * @see	DailySchedule
	 * @see WeeklySchedule
	 * @see	MonthlySchedule
	 */
	private Schedule schedule;
	
	/**
	 * Builds a {@code Trigger}.
	 */
	@Override
	public Trigger build() {
		if(schedule == null)
			throw new IllegalStateException("Trigger schedule not set");
		
		TriggerBuilder<Trigger> builder = TriggerBuilder.newTrigger();
		
		builder.withIdentity(getName(), getGroup())
				.withSchedule(schedule.build());
		
		if(isStartNow()) 
			/* startNow parameter takes priority 
			 * even if there's a value for startDate */
			builder.startNow();
		else
			builder.startAt(getStartDate());
		
		if(getPriority() > 0) 
			/* Only means that priority is set */
			builder.withPriority(getPriority());
		if(!isNoEnd()) 
			/* noEnd parameter takes priority even 
			 * if there's a value for endDate */
			builder.endAt(getEndDate());
		
		return builder.build();
	}
	
	/**
	 * Returns the {@code Name} identifier of the 
	 * {@code Trigger} that this {@code DataModel} represents.
	 * 
	 * @return
	 * 		Returns the {@code Name} identifier of the {@code Trigger}
	 * 
	 * @see TriggerKey
	 * @see	TriggerBuilder#withIdentity(String, String)
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the {@code Name} identifier of the 
	 * {@code Trigger} that this {@code DataModel} represents.
	 * 
	 * @param 
	 * 		name	The {@code Name} identifier of the {@code Trigger}
	 * 
	 * @see TriggerKey
	 * @see	TriggerBuilder#withIdentity(String, String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the {@code Group Name} identifier of the 
	 * {@code Trigger} that this {@code DataModel} represents.
	 * 
	 * @return
	 * 		Returns the {@code Group Name} identifier of the {@code Trigger}
	 * 
	 * @see TriggerKey
	 * @see	TriggerBuilder#withIdentity(String, String)
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * Sets the {@code Group Name} identifier of the 
	 * {@code Trigger} that this {@code DataModel} represents.
	 * 
	 * @param 
	 * 		group	The {@code Group Name} identifier of the {@code Trigger}
	 * 
	 * @see TriggerKey
	 * @see	TriggerBuilder#withIdentity(String, String)
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * Returns the {@code Date} when the {@code Trigger} should start. 
	 * 
	 * @return
	 * 		The {@code Date} when the {@code Trigger} should start
	 * 
	 * @see	TriggerBuilder#startAt(Date)
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the {@code Date} when the {@code Trigger} should start. 
	 * 
	 * @param
	 * 		startDate	The {@code Date} when the {@code Trigger} should start
	 * 
	 * @see	TriggerBuilder#startAt(Date)
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Returns the {@code Date} when the {@code Trigger} should end 
	 * regardless of the remaining repeats. 
	 *  
	 * @return
	 * 		The {@code Date} when the {@code Trigger} should end
	 * 
	 * @see	TriggerBuilder#endAt(Date)
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the {@code Date} when the {@code Trigger} should end 
	 * regardless of the remaining repeats. 
	 *  
	 * @param
	 * 		endDate		The {@code Date} when the {@code Trigger} should end
	 * 
	 * @see	TriggerBuilder#endAt(Date)
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Returns the priority of the {@code Trigger}
	 *
	 * @return
	 * 		The priority of the {@code Trigger}
	 * 
	 * @see	TriggerBuilder#withPriority(int)
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Sets the priority of the {@code Trigger}
	 *
	 * @param
	 * 		priority	The priority of the {@code Trigger}
	 * 
	 * @see	TriggerBuilder#withPriority(int)
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	/**
	 * Sets the schedule of the {@code Trigger}
	 * @param 
	 * 		schedule	The trigger's schedule
	 */
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	
	/**
	 * Returns the schedule of the {@code Trigger}
	 * @return
	 * 		The schedule of the {@code Trigger}
	 */
	public Schedule getSchedule() {
		return schedule;
	}
	
	/**
	 * Returns the flag that sets the {@code Trigger} to start at the current moment. 
	 * 
	 * @return
	 * 		The flag that sets the {@code Trigger} to start at the current moment
	 * 
	 * @see	TriggerBuilder#startNow()
	 */
	public boolean isStartNow() {
		return startNow;
	}
	
	/**
	 * Sets the flag that sets the {@code Trigger} to start at the current moment. 
	 * 
	 * @param
	 * 		startNow	The flag that sets the {@code Trigger} to start at the current moment. 
	 * 					Set to {@code true} to start the trigger at the current moment. Otherwise, false.
	 * 					Default value is {@code true}.
	 * 
	 * @see	TriggerBuilder#startNow()
	 */
	public void setStartNow(boolean startNow) {
		this.startNow = startNow;
	}
	
	/**
	 * Returns the flag that sets the {@code Trigger} to run indefinitely. 
	 * 
	 * @return
	 * 		The flag that sets the {@code Trigger} to run indefinitely
	 * 
	 * @see	TriggerBuilder#endAt(Date)
	 */
	public boolean isNoEnd() {
		return noEnd;
	}

	/**
	 * Sets the flag that sets the {@code Trigger} to run indefinitely. 
	 * 
	 * @param
	 * 		noEnd		The flag that sets the {@code Trigger} to run indefinitely.
	 * 					Set to {@code true} to run indefinitely. Otherwise, false.
	 * 					Default value is {@code true}.
	 * 
	 * @see	TriggerBuilder#endAt(Date)
	 */
	public void setNoEnd(boolean noEnd) {
		this.noEnd = noEnd;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm a");
		StringBuilder sb = new StringBuilder();
		sb.append("Trigger Details [\n");
		sb.append(" Id:{Group:" + getGroup() + ", Name:" + getName() + "}\n");
		
		if(isStartNow())
			sb.append(" Start: Now\n");
		else 
			sb.append(" StartDate: " + sdf.format(getStartDate()) + "\n");
		
		if(!isNoEnd())
			sb.append(" EndDate:" + sdf.format(getEndDate()) + "\n");
		
		sb.append(" Priority:" + getPriority() + "\n");
		sb.append(" Schedule:" + getSchedule() + "\n");
		sb.append("]\n");
		return sb.toString();
	}

}
