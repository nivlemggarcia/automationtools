package com.automationtools.exception;

/**
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class ParsingFailedException extends RuntimeException {
	
	/** Generated serialVersionUID */
	private static final long serialVersionUID = 1286088046085375054L;
	
	private static final String _MESSAGE_FORMAT = "Failed with [%s]";
	
	static String format(String message) {
		return String.format(_MESSAGE_FORMAT, message);
	}

	public ParsingFailedException() {
		super();
	}
	
	public ParsingFailedException(String message) {
		super(format(message));
	}
	
	public ParsingFailedException(Throwable t) {
		super(format(t.getMessage()), t);
	}
	
	public ParsingFailedException(String message, Throwable t) {
		super(format(message), t);
	}

}
