package com.automationtools.properties;

import java.util.Properties;

import com.automationtools.template.Key;

/**
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 */
public class NullPropertiesLookupStrategy implements PropertiesLookupStrategy {
	
	@Override
	public Properties get(Key templatekey) {
		return null;
	}

	@Override
	public boolean delete(Key templatekey) throws Exception {
		return true;
	}

	@Override
	public boolean update(Key templatekey, Properties newproperties) throws Exception {
		return true;
	}

}
