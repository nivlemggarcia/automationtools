package com.automationtools.report;

/**
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public interface MailContent {
	
	/**
	 * Returns the {@code String}-equivalent of the content
	 * 
	 * @return		
	 * 		The {@code String}-equivalent of the content
	 * 
	 * @throws Exception
	 */
	public String get() throws Exception;
	
	/**
	 * Returns the {@code content-type} of this MailContent
	 * 
	 * @return		
	 * 		The {@code content-type} of the content
	 */
	public String getType(); 
	
}
