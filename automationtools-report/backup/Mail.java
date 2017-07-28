package com.automationtools.report;

import static org.springframework.util.Assert.*;
import java.util.Calendar;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public abstract class Mail {
	
	private static final Logger log = LoggerFactory.getLogger(Mail.class);
	
	/**
	 * 
	 * @return
	 */
	public abstract Session getSession();
	
	/**
	 * 
	 * @return
	 */
	public abstract Address getFrom();
	
	/**
	 * 
	 * @return
	 */
	public abstract Address[] getTo();
	
	/**
	 * 
	 * @return
	 */
	public abstract Address[] getCc();
	
	/**
	 * 
	 * @return
	 */
	public abstract Address[] getBcc();
	
	/**
	 * 
	 * @return
	 */
	public abstract String getSubject();
	
	/**
	 * 
	 * @return
	 */
	public abstract Multipart getContent();
	
	/**
	 * 
	 * @throws Exception
	 */
	public void send() throws Exception {
		/* At the least, these fields should be provided */
		state(getContent() != null, "Mail content cannot be null");
		state(getFrom() != null, "Sender cannot be null");
		state(getTo() != null && getTo().length > 0, "Recipient cannot be null or empty");
		state(getSubject() != null && !getSubject().isEmpty(), "Mail subject cannot be null or empty");
		state(getSession() != null, "Session cannot be null");
		
		Message message = null;
		try {
			log.info("Creating mail content ...");
			message = new MimeMessage(getSession());
			
			/* Setting headers */
			message.setFrom(getFrom());
			message.setRecipients(Message.RecipientType.TO, getTo());
			message.setRecipients(Message.RecipientType.CC, getCc());
			message.setRecipients(Message.RecipientType.BCC, getBcc());
			message.setSubject(getSubject());
			message.setSentDate(Calendar.getInstance().getTime());
			
			/* Setting mail body */
			Multipart messageContent = getContent();
			message.setContent(messageContent);
		} catch (Exception e) {
			log.error("Error occurred while building mail content", e);
			throw e;
		}
		
		try {
			log.info("Sending mail ...");
			Transport.send(message);
			log.info("Mail Sent!");
		} catch (Exception e) {
			log.error("Error occurred while sending mail", e);
			throw e;
		}
	}
	
}
