package com.automationtools.core.scheduler;

import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Map;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

/**
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class JobDataModel<T extends Job> implements DataModel<JobDetail> {

	/** Generated serialVersionUID */
	private static final long serialVersionUID = 7979918256789346608L;

	private String name;

	private String group;

	private String description;

	private Map<String, Object> dataMap;

	private boolean recoverable;

	private boolean durable;
	
	private Class<T> type;
	
	public static <T extends Job> JobDataModel<T> create(Class<T> type) {
		return new JobDataModel<T>(type);
	}
	
	private JobDataModel(Class<T> type) {
		this.type = type;
	}

	@Override
	public JobDetail build() {
		JobBuilder builder = JobBuilder.newJob();
		
		/* Set Mandatory fields */
		builder.withIdentity(getName(), getGroup())
				.ofType(getType())
				.requestRecovery(isRecoverable())
				.storeDurably(isDurable())
				.usingJobData(new JobDataMap(getDataMap()));
		
		/* Conditionally set optional fields */
		if(getDescription() != null && !getDescription().isEmpty())
			builder.withDescription(getDescription());

		return builder.build();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Class<T> getType() {
		return type;
	}

	public Map<String, Object> getDataMap() {
		if(dataMap == null)
			dataMap = new HashMap<String, Object>();
		
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public boolean isRecoverable() {
		return recoverable;
	}

	public void setRecoverable(boolean recoverable) {
		this.recoverable = recoverable;
	}

	public boolean isDurable() {
		return durable;
	}

	public void setDurable(boolean durable) {
		this.durable = durable;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Job Details [\n");
		sb.append(" Id:{Group:" + getGroup() + ", Name:" + getName() + "}\n");
		
		if(getDescription() != null && !getDescription().isEmpty())
			sb.append(" Description:" + getDescription() + "\n");
		
		sb.append(" Class:" + getType().getSimpleName() + "\n");
		
		if(getDataMap() != null && !getDataMap().isEmpty()) {
			sb.append(" DataMap {\n");
			for(Entry<String, Object> e : getDataMap().entrySet())
				sb.append("  " + e.getKey() + " -> " + e.getValue());
			sb.append("\n }\n");
		}
		
		sb.append(" Recoverable:" + isRecoverable() + "\n");
		sb.append(" Durable:" + isDurable() + "\n");
		sb.append("]\n");
		return sb.toString();
	}

}
