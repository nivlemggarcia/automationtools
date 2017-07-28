package com.automationtools.report;

import java.util.Properties;

import org.junit.Test;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.ServerConfig;

public class EmailTest {
	
	@Test
	public void testSendEmail() {
		Email email = new EmailBuilder()
			    .from(null, "donotreply@hotvalidemail.com")
			    .to(null, "nivlemggarcia@gmail.com")
			    .to(null, "melvin.garcia@sprint.com")
			    .subject("My Bakery is finally open!")
			    .textHTML("<html><body>open <a href=\"cid:file.txt\">file</a></body></html>")
			    .addAttachment("file.txt", "BLAH!".getBytes(), "text/plain")
			    .build();
		
		Properties p = new Properties();
		p.setProperty("mail.smtp.auth", "true");
		p.setProperty("mail.smtp.starttls.enable", "true");
		
		Mailer mailer = new Mailer(new ServerConfig("smtp.gmail.com", 587, "nivlemggarcia", "nivleMG!0421"));
		mailer.applyProperties(p);
		mailer.setDebug(true);
		mailer.setTransportModeLoggingOnly(true);
		mailer.sendMail(email);
	}

}
