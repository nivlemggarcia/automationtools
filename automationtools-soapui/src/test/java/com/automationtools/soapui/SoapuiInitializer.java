package com.automationtools.soapui;

import java.io.File;

import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SoapuiInitializer {
	
	@Value("classpath:logback-test.xml")
	private File logbackconfig;
	
	public void init() {
		DOMConfigurator.configureAndWatch(logbackconfig.getAbsolutePath());
	}

}
