package com.automationtools.selenium;

import com.automationtools.exception.ParsingFailedException;
import com.automationtools.template.Parser;

/**
 * The class {@code SeleniumDataParser} translating 
 * any given Selenium TestCase in a form of a byte array in to a 
 * {@code SeleniumData}.
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public class SeleniumDataParser implements Parser<SeleniumData> {

	/**
	 * Translates the given byte array into {@code SeleniumData} type.
	 * 
	 * @param arg	The source.
	 * @return 		{@code SeleniumData} object.
	 */
	@Override
	public SeleniumData parse(byte[] arg) throws ParsingFailedException {
		return null;
	}

}
