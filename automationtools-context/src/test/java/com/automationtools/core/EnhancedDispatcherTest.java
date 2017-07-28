package com.automationtools.core;

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
import com.automationtools.core.support.TaskHandlerSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@EnableAspectJAutoProxy
@ImportResource("classpath:enhanceddispatchertest-config.xml")
@ContextConfiguration(classes = {EnhancedDispatcherTest.class})
@ComponentScan(basePackageClasses = {
		TaskHandlerSupport.class,
})
public class EnhancedDispatcherTest {
	
	private static final Logger log = LoggerFactory.getLogger(EnhancedDispatcherTest.class);
	
	@Bean
	public TaskHandler<Data, Void> createDataHandler() {
		return (data) -> {
			log.info(data + " BANG!!!"); 
			return null;
		};
	}
	
	@Autowired @Qualifier("dispatcher")
	private TaskDispatcher dispatcher;
	
	@Test
	public void testEnhandedHandling() {
		try {
			Data data = Data.create("'Date now is: ${#SYSDATE.format('MM/dd/yyyy HH:mm:ss.s').evaluate()}'");
			dispatcher.dispatch(Task.wrap(data));
			Thread.sleep(1000L);
		} catch (Throwable e) {
			Assert.fail(e.getMessage());
		}
	}
	
}
