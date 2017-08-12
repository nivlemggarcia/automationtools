package com.automationtools.exception;

import com.automationtools.web.view.ChainedTemplateViewWrapper;

/**
 * The class {@code NoSuitableWrapperFoundException} is thrown in the event 
 * when a wrapper factory could not find the appropriate wrapper class for 
 * a particular object.
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 * @see		ChainedTemplateViewWrapper
 */
public class NoSuitableWrapperFoundException extends RuntimeException {

	/** Generated serialVersionUID */
	private static final long serialVersionUID = 6253423062851562947L;

	public NoSuitableWrapperFoundException(String message) {
		super(message);
	}
	
}
