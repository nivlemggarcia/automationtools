package com.automationtools.soapui;

import java.util.Collection;
import java.util.concurrent.Future;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.automationtools.core.TaskDispatcher;
import com.automationtools.core.Task;
import com.automationtools.core.support.TaskHandlerSupport;
import com.automationtools.template.Parser;
import com.automationtools.template.Template;
import com.automationtools.template.TemplateRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@EnableAspectJAutoProxy
@ImportResource("classpath:soapuidatahandlertest-config.xml")
@ComponentScan(basePackageClasses = {
		TaskHandlerSupport.class,
		SoapuiData.class,
		SoapuiTaskHandler.class,
		SoapuiInitializer.class
})
@ContextConfiguration(classes = {
		SoapuiTaskHandlerTest.class, 
})
public class SoapuiTaskHandlerTest {
	
	private static final Logger log = LoggerFactory.getLogger(SoapuiTaskHandlerTest.class);
	
	@Autowired @Qualifier("fileSystemTemplateRepository")
	private TemplateRepository fileSystemTemplateRepository;
	
	@Autowired @Qualifier("dispatcher")
	private TaskDispatcher dispatcher;
	
	@Bean
	public Parser<SoapuiData> createParser() {
		return new SoapuiDataParser();
	}
	
	@Test
	public void testHandler() throws Exception {
		Collection<Template> templates = null;
		try {
			templates = fileSystemTemplateRepository.getAll();
			if(templates == null || templates.isEmpty())
				Assert.fail("Repository is empty");
		} catch (Exception e) {
			log.error("Error with " + e.getMessage(), e);
			Assert.fail(e.getMessage());
		}
		
		Template t = templates.iterator().next();
		Future<Collection<String>> future = dispatcher.dispatch(Task.wrap(t.getData().copy()));
		Collection<String> result = future.get();
		if(result == null || result.isEmpty())
			log.info("Result is empty!");
		
		for(String r : result) {
			log.info(r);
		}
		
		Thread.sleep(10000L);
	}

}
