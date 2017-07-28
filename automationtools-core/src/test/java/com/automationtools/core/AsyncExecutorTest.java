package com.automationtools.core;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@EnableAsync
@ContextConfiguration(classes = {
		AsyncExecutorTest.class, 
})
public class AsyncExecutorTest {
	
	private static final Logger log = LoggerFactory.getLogger(AsyncExecutorTest.class);
	
	@Autowired
	private SimpleService service;
	
	@Service
	class SimpleService {
		
		@Async
		public Future<String> process(String arg) {
			String name = Thread.currentThread().getName();
			String key = MDC.getCopyOfContextMap().get("key");
			return new AsyncResult<String>(name + " -- " + arg + " -- " + key);
		}
	}

	@Bean
	 public Executor getExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
//        return new MdcAwareExecutor(executor);
		return Executors.newCachedThreadPool();
    }
	
	class MdcAwareExecutor implements Executor {
		Executor executor;
		
		public MdcAwareExecutor(Executor executor) {
			this.executor = executor;
		}

		@Override
		public void execute(Runnable command) {
			executor.execute(new MdcAwareRunnable(command));
		}
		
	}
	
	class MdcAwareRunnable implements Runnable {
		Map<String, String> copy;
		Runnable command;
		
		public MdcAwareRunnable(Runnable command) {
			copy = MDC.getCopyOfContextMap();
			this.command = command;
		}

		@Override
		public void run() {
			if(copy != null)
				MDC.setContextMap(copy);
			
			command.run();
		}
	}
	
	@Test
	public void testSimpleRunnable() throws InterruptedException, ExecutionException {
		MDCCloseable closeable = MDC.putCloseable("key", UUID.randomUUID().toString());
		for (int i = 0; i < 10; i++) {
			Future<String> f = service.process("BOINK! " + i);
			log.info(f.get());
			Thread.sleep(500L);
		}
		closeable.close();
	}
	 
}
