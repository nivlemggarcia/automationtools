package com.automationtools.template;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.springframework.beans.factory.annotation.Required;
import com.automationtools.context.ReadWriteLockSupport;
import com.automationtools.core.Data;

/**
 * This serves as the base class of all {@code Repository} 
 * implementation that stores and manages {@code Template}s.
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public abstract class TemplateRepository extends ReadWriteLockSupport implements Repository<Key, Template> {
	
	/**
	 * 
	 */
	protected Parser<? extends Data> parser;
	
	/**
	 * Default Constructor. Initializes the lock with 
	 * {@code ReentrantReadWriteLock} that uses 
	 * {@linkplain ReentrantReadWriteLock#isFair() fair ordering policy}.
	 */
	public TemplateRepository() {
		super(true);
	}
	
	/**
	 * 
	 * @param parser
	 */
	@Required
	public void setParser(Parser<? extends Data> parser) {
		this.parser = parser;
	}
	
}
