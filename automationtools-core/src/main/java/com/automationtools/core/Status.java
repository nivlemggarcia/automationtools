package com.automationtools.core;


/**
 * Possible states of an {@code AbstractExecutor}
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public interface Status {
	
	/**
	 * Returns the name that identifies this {@code Status}
	 */
	public String getId();
	
	/**
	 * Returns the <strong>unique</strong> code for this {@code Status}
	 */
	public int getCode();
	
	/**
	 * Default implementations of {@code Status}
	 * 
	 * @author 	Melvin Garcia
	 * @since 	1.0.0
	 */
	public enum Default implements Status {
		
		NOT_STARTED("Not Started", 1),
		
		IN_QUEUE("In Queue", 2),
		
		IN_PROGRESS("In Progress", 3),
		
		STOPPED("Stopped", 4),
		
		FAILED("Failed", -1),
		
		SUCCESSFUL("Successful", 0),
		
		;
		
		private String name;
		
		private int code;
		
		private Default(String name, int code) {
			this.name = name;
			this.code = code;
		}
		
		@Override
		public String getId() {
			return name;
		}
		
		@Override
		public int getCode() {
			return code;
		}
		
		public static Status get(int code) {
			for(Status status : values())
				if(status.getCode() == code)
					return status;
			
			throw new IllegalArgumentException("No Status found with code " + code);
		}
		
		public static Status get(String id) {
			for(Status status : values())
				if(status.getId().equalsIgnoreCase(id))
					return status;
			
			throw new IllegalArgumentException("No Status found with id " + id);
		}
		
		@Override
		public String toString() {
			return name;
		}
		
	}
	
}
