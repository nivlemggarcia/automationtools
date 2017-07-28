package com.automationtools.template;

import java.io.IOException;
import com.automationtools.context.Data;

/**
 * A parser that translates a byte array to {@code Data}.
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public interface DataParser {
	
	/**
	 * Translate a byte array to {@code RawData}.
	 */
	public Data parse(byte[] b) throws IOException;

}
