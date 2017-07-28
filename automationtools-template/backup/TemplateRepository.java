package com.automationtools.template;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;
import com.automationtools.context.ReadWriteLockSupport;
import com.automationtools.template.impl.NullPropertiesLookupStrategy;

/**
 * This serves as the base class of all repositories that stores and manages {@code Template}s.
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 */
public abstract class TemplateRepository extends ReadWriteLockSupport implements Repository<Template<?>> {
	
	/**
	 * Repository that holds key-value pair properties.
	 */
	protected PropertiesLookupStrategy propertiesLookup;
	
	/**
	 * Parser that converts a byte array to {@code RawData}.
	 */
	private DataParser parser;
	
	/**
	 * Default Constructor. In here, the following setting is used:
	 * <ul>
	 * 	<li>Default {@code PropertiesLookupStrategy}</li>
	 * 	<li>Initializes the lock with {@code ReentrantReadWriteLock} that uses 
	 * 	{@linkplain ReentrantReadWriteLock#isFair() fair ordering policy}.</li>
	 * </ul>
	 */
	public TemplateRepository() {
		super(true);
		propertiesLookup = new NullPropertiesLookupStrategy();
	}
	
	/**
	 * Returns the repository that holds key-value pair properties.
	 */
	public PropertiesLookupStrategy getPropertiesLookup() {
		return propertiesLookup;
	}
	
	/**
	 * Sets the repository that holds key-value pair properties.
	 */
	public void setPropertiesLookup(PropertiesLookupStrategy propertiesLookup) {
		if(propertiesLookup == null)
			this.propertiesLookup = new NullPropertiesLookupStrategy();
		else
			this.propertiesLookup = propertiesLookup;
	}
	
	/**
	 * Sets the parser that converts a byte array to {@code RawData}.
	 */
	@Required
	public void setParser(DataParser parser) {
		Assert.notNull(parser);
		this.parser = parser;
	}
	
	/**
	 * Returns the parser that converts a byte array to {@code RawData}.
	 */
	public DataParser getParser() {
		return parser;
	}
	
}
