package com.automationtools.template;

import static org.springframework.util.Assert.*;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Required;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public class DefaultPropertiesLookupStrategy implements PropertiesLookupStrategy {
	
	/**
	 * 
	 */
	private volatile Map<Key, PropertiesLookupStrategy> keyToSource;
	
	/**
	 * 
	 */
	private Set<PropertiesLookupStrategy> sources;
	
	/**
	 * 
	 */
	private PropertiesLookupStrategy defaultLookupStrategy;
	
	/**
	 * 
	 */
	public DefaultPropertiesLookupStrategy() {
		keyToSource = new ConcurrentHashMap<>();
		defaultLookupStrategy = new NullPropertiesLookupStrategy();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Properties get(Key templatekey) {
		state(sources != null, "sources field is required and must not be null.");
		for(PropertiesLookupStrategy source : sources) {
			Properties properties = source.get(templatekey);
			if(properties == null)
				continue;
			
			/* Store the template-to-propertylookup for later use */
			if(!keyToSource.containsKey(templatekey))
				keyToSource.put(templatekey, source);
			return properties;
		}
		
		/* Cannot find properties from any of the sources */
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean delete(Key templatekey) throws Exception {
		PropertiesLookupStrategy source = keyToSource.remove(templatekey);
		if(source == null)
			/* It doesn't exist anyway */
			return true;
		
		return source.delete(templatekey);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean update(Key templatekey, Properties newproperties) throws Exception {
		PropertiesLookupStrategy source = null;
		Properties p = get(templatekey);
		if(p == null) {
			/* In case properties is nowhere to be found from any of the sources,
			 * create a new one using the default lookup strategy. */
			keyToSource.put(templatekey, defaultLookupStrategy);
			source = defaultLookupStrategy;
		} else
			source = keyToSource.get(templatekey);
		
		return source.update(templatekey, newproperties);
	}
	
	/**
	 * 
	 */
	public Set<PropertiesLookupStrategy> getSources() {
		return sources;
	}
	
	/**
	 * 
	 */
	@Required
	public void setSources(Set<PropertiesLookupStrategy> sources) {
		notNull(sources, "LookupStrategies cannot be null");
		this.sources = sources;
	}

	/**
	 * 
	 */
	public PropertiesLookupStrategy getDefaultLookupStrategy() {
		return defaultLookupStrategy;
	}
	
	/**
	 * 
	 */
	public void setDefaultLookupStrategy(PropertiesLookupStrategy defaultLookupStrategy) {
		if(defaultLookupStrategy == null)
			this.defaultLookupStrategy = new NullPropertiesLookupStrategy();
		else
			this.defaultLookupStrategy = defaultLookupStrategy;
	}
	
}
