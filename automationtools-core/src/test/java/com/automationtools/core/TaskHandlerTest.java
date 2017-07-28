package com.automationtools.core;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.automationtools.core.support.TaskHandlerSupport;
import com.automationtools.exception.ExecutionFailedException;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackageClasses = {
		TaskHandlerSupport.class
})
@ContextConfiguration(classes = {
		TaskHandlerTest.class, 
})
public class TaskHandlerTest {
	
	protected Logger log = LoggerFactory.getLogger(TaskHandlerTest.class);
	
	@Bean
	public TaskHandler<Task<Integer>, Void> createHandlerWithException() {
		return (task) -> {
			log.info("INTEGEEEERRR!!!");
			throw new ExecutionFailedException("Invalid integer");
		};
	}
	
	private TaskHandler<Task<String>, Void> STRING_VOID_CONSUMER = 
			(task) -> {
				log.info("BoINK!");
				return null;
			};
			
	private TaskHandler<Task<String>, Boolean> STRING_BOOLEAN_CONSUMER = 
			(task) -> {
				log.info("BoINK!");
				return true;
			};
	
	@Autowired @Qualifier("createHandlerWithException")
	private TaskHandler<Task<Integer>, Void> CONSUMER_WITH_EXCEPTION =
			(task) -> {
				log.info("INTEGEEEERRR!!!");
				throw new ExecutionFailedException("Invalid integer");
			};
	
	@Test
	public void testVoidConsumer() throws Exception {
		STRING_VOID_CONSUMER.handle(Task.wrap("testHandler!!!"));
	}
	
	@Test
	public void testValueReturningConsumer() throws Exception {
		boolean ans = STRING_BOOLEAN_CONSUMER.handle(Task.wrap("testHandler!!!"));
		Assert.assertTrue(ans == true);
	}
	
	@Test
	public void testVoidConsumerUsingExecutor() throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(() -> STRING_VOID_CONSUMER.handle(Task.wrap("testHandlerUsingExecutor!!!")));
	}
	
	@Test
	public void testValueReturningConsumerUsingExecutor() throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Boolean> ans = executor.submit(() -> 
			STRING_BOOLEAN_CONSUMER.handle(Task.wrap("testHandlerUsingExecutor!!!")));
		Assert.assertTrue(ans.get() == true);
	}
	
	@Test(expected = ExecutionFailedException.class)
	public void testConsumerWithException() throws Exception {
		CONSUMER_WITH_EXCEPTION.handle(Task.wrap(1234567890));
	}

}
