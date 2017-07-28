package com.automationtools.report;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImmutableMailTest {
	
	private Logger log = LoggerFactory.getLogger(ImmutableMailTest.class);
	
	@Test
	public void testSendEmptyMail() {
		try {
			Mail m = ImmutableMail.builder()
					.from(() -> new InternetAddress("nivlemggarcia@gmail.com", true))
					.to(() -> new InternetAddress[] {new InternetAddress("nivlemggarcia@gmail.com", true)})
					.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
