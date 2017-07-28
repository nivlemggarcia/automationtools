package com.automationtools.core;

import static reactor.bus.selector.Selectors.$;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.Environment;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
		SimpleConsumerTest.class, 
})
@Configuration
public class SimpleConsumerTest {
	
	private static final Logger log = LoggerFactory.getLogger(SimpleConsumerTest.class);

	@Bean
	Environment env() {
		return Environment.initializeIfEmpty().assignErrorJournal();
	}

	@Bean
	EventBus createEventBus(Environment env) {
		return EventBus.create(env, Environment.THREAD_POOL);
	}
	
	@Bean
	Consumer<Event<SimpleContext>> createConsumer(EventBus bus) {
		Consumer<Event<SimpleContext>> c = (t) -> log.info(t.getData().toString());
		bus.on($("simpleContext"), c);
		return c;
	}
	
	@Autowired
	private EventBus bus;
	
	@Test
	public void testEventBusNotNull() {
		Assert.assertNotNull(bus);
	}
	
	@Test
	public void testFireSimpleContextEvent() {
		log.info("testFireSimpleContextEvent");
		bus.notify("simpleContext", Event.wrap(new SimpleContext("BLAH!")));
	}
	
	class SimpleContext {

		private String name;
		
		public SimpleContext(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
		
		@Override
		public String toString() {
			return getName();
		}
		
	}

}
