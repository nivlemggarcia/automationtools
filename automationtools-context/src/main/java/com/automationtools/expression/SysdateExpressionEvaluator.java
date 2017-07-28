package com.automationtools.expression;

import static org.springframework.util.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 
 * 
 * @author Melvin Garcia
 * @since v.3.0
 */
public class SysdateExpressionEvaluator implements ExpressionEvaluator {
	
	private String format;

	/**
	 * Returns the current date using the given format.
	 * 
	 * @param format	Date format that will be used
	 * @return	current date using the given format.
	 */
	public SysdateExpressionEvaluator format(String format) {
		this.format = format;
		return this;
	}

	/**
	 * Returns the current date using the given format.
	 * 
	 * @return	current date using the given format.
	 */
	@Override
	public String evaluate() {
		state(format != null, "Date format cannot be null");
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar now = Calendar.getInstance();
		return sdf.format(now.getTime());
	}
	
}
