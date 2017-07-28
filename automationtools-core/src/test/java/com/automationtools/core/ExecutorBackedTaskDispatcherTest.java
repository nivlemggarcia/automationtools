package com.automationtools.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.automationtools.core.support.TaskHandlerSupport;
import com.automationtools.exception.NoSuitableHandlerFoundException;
import com.automationtools.util.CreateUuid;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@EnableAspectJAutoProxy
@ImportResource("classpath:executorbackedtaskdispatchertest-config.xml")
@ContextConfiguration(classes = {ExecutorBackedTaskDispatcherTest.class})
@ComponentScan(basePackageClasses = {
		TaskHandlerSupport.class,
})
public class ExecutorBackedTaskDispatcherTest {
	
	private static final Logger log = LoggerFactory.getLogger(ExecutorBackedTaskDispatcherTest.class);
	
	@Bean
	public TaskHandlerFactory createHandlerFactory() {
		Map<Class<?>, TaskHandler<?, ?>> mapping = new HashMap<>();
		mapping.put(String.class, (String data) -> {
			log.info(data + " BANG!!!");
			return "boink!";
		});
		
		return new TaskHandlerFactory() {
			@SuppressWarnings("unchecked")
			@Override
			public <T, R> TaskHandler<T, R> get(Task<T> task) throws NoSuitableHandlerFoundException {
				TaskHandler<T, R> handler = (TaskHandler<T, R>) mapping.get(task.getType());
				if(handler != null)
					return handler;
				
				throw new NoSuitableHandlerFoundException(task.getType());
			}
		};
	}
	
	
	@Autowired @Qualifier("dispatcher")
	private TaskDispatcher dispatcher;
	
	@Autowired @Qualifier("createHandlerFactory")
	private TaskHandlerFactory lookup;
	
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	private final static UUID sharedTaskId = CreateUuid.fromTimestamp(System.currentTimeMillis());
	
	class NoHandlerData {

	}
	
	@Test
	public void testAutowiredDispatcher() {
		try {
			Task<String> t = Task.wrap(new String("testAutowiredDispatcher"));
			dispatcher.dispatch(t);
			Thread.sleep(1000L);
		} catch (Throwable e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test(expected = NoSuitableHandlerFoundException.class)
	public void testAutowiredDispatcherWithNoSuitableHandler() throws NoSuitableHandlerFoundException {
		Task<NoHandlerData> t = Task.wrap(new NoHandlerData());
		dispatcher.dispatch(t);
		Assert.fail("Expecting to throw NoSuitableHandlerFoundException");
	}
	
	@Test
	public void testDispatcherUsingSharedTaskId() throws NoSuitableHandlerFoundException {
		ExecutorBackedTaskDispatcher dispatcher = new ExecutorBackedTaskDispatcher();
		dispatcher.setExecutor(executor);
		dispatcher.setFactory(lookup);
		dispatcher.setDiscriminated(true);
		try {
			Task<String> t = Task.wrap("testDispatcherUsingSharedTaskId");
			t.setId(sharedTaskId);
			dispatcher.dispatch(t);
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			// ignore 
		}
	}
	
	@Test
	public void testDiscriminatingDispatcher() throws NoSuitableHandlerFoundException, ExecutionException {
		ExecutorBackedTaskDispatcher dispatcher = new ExecutorBackedTaskDispatcher();
		dispatcher.setExecutor(executor);
		dispatcher.setFactory(lookup);
		dispatcher.setDiscriminated(true);
		try {
			Task<String> t = Task.wrap("testDiscriminatingDispatcher");
			t.setId(sharedTaskId);
			Future<String> f = dispatcher.dispatch(t);
			log.info(f.get());
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			// ignore 
		}
	}
	
	@Test
	public void testNonDiscriminatingDispatcher() throws NoSuitableHandlerFoundException {
		ExecutorBackedTaskDispatcher dispatcher = new ExecutorBackedTaskDispatcher();
		dispatcher.setExecutor(executor);
		dispatcher.setFactory(lookup);
		Task<String> t = Task.wrap("testNonDiscriminatingDispatcher");
		try {
			dispatcher.dispatch(t);
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			// ignore 
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDispatcherWithNullExecutor() throws NoSuitableHandlerFoundException {
		ExecutorBackedTaskDispatcher dispatcher = new ExecutorBackedTaskDispatcher();
		dispatcher.setExecutor(null);
		dispatcher.setFactory(lookup);
		Task<String> t = Task.wrap("testDispatcherWithNullExecutor");
		try {
			dispatcher.dispatch(t);
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			// ignore 
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDispatcherWithNullLookup() throws NoSuitableHandlerFoundException {
		ExecutorBackedTaskDispatcher dispatcher = new ExecutorBackedTaskDispatcher();
		dispatcher.setExecutor(executor);
		dispatcher.setFactory(null);
		Task<String> t = Task.wrap("testDispatcherWithNullLookup");
		try {
			dispatcher.dispatch(t);
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			// ignore 
		}
	}

}
