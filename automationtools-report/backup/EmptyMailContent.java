package com.automationtools.report;

/**
 * 
 * @author Melvin Garcia
 * @since v.3.0
 */
public class EmptyMailContent implements MailContent {

	@Override
	public String get() throws Exception {
		return "";
	}

	@Override
	public String getType() {
		return "text/plain";
	}

}
