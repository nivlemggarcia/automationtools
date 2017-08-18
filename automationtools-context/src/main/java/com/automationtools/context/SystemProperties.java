package com.automationtools.context;

import static org.springframework.util.Assert.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map.Entry;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class {@code SystemProperties} is a utility class 
 * that contains convenience methods for loading and accessing 
 * {@linkplain System#getProperties() system's properties}
 * and automatically converts the values to the various types. 
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public final class SystemProperties {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemProperties.class);
	
	private SystemProperties() {
	}
	
	/**
	 * Convenience method that will return the value of
	 * a system property that is associated with the 
	 * argument <em>key</em> as {@code File}. 
	 * 
	 * @param key	
	 * 		the key whose associated value is to be returned
	 * 
	 * @return
	 * 		the value of the system property as {@code File}.
	 * 
	 * @throws NullPointerException
	 * 		If the key argument is null or if the property value for 
	 * 		the key specified does not exist.
	 */
	public static File getAsFile(String key) {
		notNull(key, "System property key cannot be null");
		if(getAsString(key) == null)
			throw new NullPointerException("Property value cannot be found: " + key);
		
		return new File(getAsString(key));
	}
	
	/**
	 * Convenience method that will return the value of
	 * a system property that is associated with the 
	 * argument <em>key</em> as {@code String}. 
	 * 
	 * @param key	
	 * 		the key whose associated value is to be returned
	 * 
	 * @return
	 * 		Value of the system property as {@code String}.
	 * 
	 * @throws NullPointerException
	 * 		If the key argument is null.
	 */
	public static String getAsString(String key) {
		notNull(key, "System property key cannot be null");
		return System.getProperty(key);
	}
	
	/**
	 * Convenience method that will return the value of
	 * a system property that is associated with the 
	 * argument <em>key</em> as {@code Integer}. 
	 * 
	 * @param key	
	 * 		the key whose associated value is to be returned
	 * 
	 * @return
	 * 		Value of the system property as {@code Integer}.
	 * 
	 * @throws NullPointerException
	 * 		If the key argument is null.
	 * @throws NumberFormatException
	 * 		if the string does not contain a parsable integer.
	 */
	public static Integer getAsInteger(String key) {
		notNull(key, "System property key cannot be null");
		return Integer.parseInt(getAsString(key));
	}
	
	/**
	 * Convenience method that will return the value of
	 * a system property that is associated with the 
	 * argument <em>key</em> as {@code Double}. 
	 * 
	 * @param key	
	 * 		the key whose associated value is to be returned
	 * 
	 * @return
	 * 		Value of the system property as {@code Double}.
	 * 
	 * @throws NullPointerException
	 * 		If the key argument is null.
	 * @throws NumberFormatException
	 * 		if the string does not contain a parsable integer.
	 */
	public static Double getAsDouble(String key) {
		notNull(key, "System property key cannot be null");
		return Double.parseDouble(getAsString(key));
	}
	
	/**
	 * Convenience method that will return the value of
	 * a system property that is associated with the 
	 * argument <em>key</em> as {@code Boolean}. 
	 * 
	 * @param key	
	 * 		the key whose associated value is to be returned
	 * 
	 * @return
	 * 		Value of the system property as {@code Boolean}. 
	 * 		The Boolean returned represents a true value if the 
	 * 		property value associated with the key argument is not 
	 * 		null and is equal, ignoring case, to the string "true".
	 * 
	 * @throws NullPointerException
	 * 		If the key argument is null.
	 */
	public static Boolean getAsBoolean(String key) {
		notNull(key, "System property key cannot be null");
		return Boolean.valueOf(System.getProperty(key));
	}
	
	/**
	 * Convenience method that will return the value of
	 * a system property that is associated with the 
	 * argument <em>key</em> as {@code DateFormat}. 
	 * 
	 * @param key	
	 * 		the key whose associated value is to be returned
	 * 
	 * @return
	 * 		Value of the system property as {@code DateFormat}. 
	 * 
	 * @throws NullPointerException
	 * 		If the key argument is null.
	 * @throws IllegalArgumentException
	 * 		 if the pattern, derived from the system properties, is invalid
	 */
	public static DateFormat getAsDateFormat(String key) {
		notNull(key, "System property key cannot be null");
		return new SimpleDateFormat(getAsString(key));
	}
	
	/**
	 * Convenience method that will return the value of
	 * a system property that is associated with the 
	 * argument <em>key</em> as {@code Class}. 
	 * 
	 * @param key	
	 * 		the key whose associated value is to be returned
	 * 
	 * @return
	 * 		Value of the system property as {@code Class}. 
	 * @throws ClassNotFoundException 
	 * 
	 * @throws NullPointerException
	 * 		If the key argument is null.
	 * @throws ClassNotFoundException
	 * 		if the class cannot be located
	 */
	public static Class<?> getAsClass(String key) throws ClassNotFoundException {
		notNull(key, "System property key cannot be null");
		return Class.forName(getAsString(key));
	}
	
	/**
	 * Initialize by loading the System properties to the property map
	 * 
	 * @param in
	 * 
	 * @throws IOException
	 * 		 if an error occurred when reading from the input stream.
	 * @throws IllegalArgumentException
	 * 		 if the input stream contains a malformed Unicode escape sequence.
	 */
	private static void doConfigure(InputStream in) throws IOException  {
		logger.info("Loading System properties ...");
		Properties temp = new Properties();
		temp.load(in);
		System.setProperties(temp);
		
		for(Entry<Object, Object> entry : temp.entrySet()) {
			logger.debug("{} -> {}", entry.getKey(), entry.getValue());
		}
		
		logger.info("System properties successfully loaded!");
	}
	
	/**
	 * Configure by loading properties from a resource stream using the filename.
	 * 
	 * @param propertyFilename	
	 * 		Filename of the property file
	 */
	public static void configure(String ... propertyFilename) {
		if(propertyFilename == null)
			throw new IllegalArgumentException("Filename cannot be null");
		
		for(String p : propertyFilename) {
			try {
				InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(p);
				doConfigure(in);
			} catch (Exception e) {
				logger.error("Failed to configure SystemProperties using property '" + p + "'. Skipping ...", e);
			}
		}
	}
	
	/**
	 * Configure by loading properties from a {@code File}. 
	 * 
	 * @param propertyFile	
	 * 		The actual property file
	 */
	public static void configureUsingFile(File ... propertyFile) {
		if(propertyFile == null)
			throw new IllegalArgumentException("File cannot be null");
		
		for(File p : propertyFile) {
			if(!p.exists()) {
				logger.warn("Property file {} does not exist. Skipping ...", p.getAbsolutePath());
				continue;
			}
			
			if(p.isDirectory()) {
				logger.warn("Not a valid property file {}. Skipping ...", p.getAbsolutePath());
				continue;
			}
			
			try {
				InputStream in = new FileInputStream(p);
				doConfigure(in);
			} catch (Exception e) {
				logger.error("Failed to configure SystemProperties using file '" + p.getAbsolutePath() + "'. Skipping ...", e);
			}
		}
	}
	
}
