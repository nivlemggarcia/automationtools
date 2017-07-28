package com.automationtools.report;

import javax.mail.BodyPart;

/**
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
@FunctionalInterface
public interface MailAttachment {

	/**
	 * Creates a {@link BodyPart} instance that represents this attachment.
	 * 
	 * @return
	 * 		The {@code BodyPart} instance that represents this attachment
	 * 
	 * @throws Exception
	 */
	public BodyPart toBodyPart() throws Exception;
	
}
