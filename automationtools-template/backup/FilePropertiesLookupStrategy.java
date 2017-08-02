package com.automationtools.properties;

import static com.automationtools.util.FileUtilities.*;
import static org.springframework.util.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import com.automationtools.exception.FailedToLoadResourceException;
import com.automationtools.template.Key;

/**
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 */
public class FilePropertiesLookupStrategy extends AbstractPropertyLookupStrategy {
	
	private static final Logger log = LoggerFactory.getLogger(FilePropertiesLookupStrategy.class);
	
	private static final String _PROPERTY_FILE_SUFFIX = ".properties";
	
	/**
	 * The reference source directory where 
	 * the property files are located.
	 */
	private File sourceDirectory;
	
	/**
	 * Default Constructor.
	 */
	private FilePropertiesLookupStrategy() {
		super(true);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Properties get(Key templatekey) {
		File propertyFile = getPropertyFile(templatekey);
		if(propertyFile == null)
			return null;
		
		log.debug("Property file found for {}", templatekey.raw());
		readLock().lock();
		try {
			Properties properties = new Properties();
			properties.load(Files.newInputStream(propertyFile.toPath()));
			return properties;
		} catch (Exception e) {
			throw new FailedToLoadResourceException(templatekey.raw(), e);
		} finally {
			readLock().unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean delete(Key templatekey) throws Exception {
		File propertyFile = getPropertyFile(templatekey);
		if(propertyFile == null)
			/* Return true as the property file does not exist anyway */
			return true;
		
		writeLock().lock();
		try {
			return Files.deleteIfExists(propertyFile.toPath());
		} finally {
			writeLock().unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean update(Key templatekey, Properties newproperties) throws Exception {
		File propertyFile = getPropertyFile(templatekey);
		writeLock().lock();
		try {
			if(propertyFile == null) {
				/* Property file doesn't exist yet. Create it. */
				propertyFile = new File(sourceDirectory, templatekey.raw() + _PROPERTY_FILE_SUFFIX);
				ensureFileExist(propertyFile);
			}
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			newproperties.store(out, null);
			Files.write(propertyFile.toPath(), out.toByteArray());
			return true;
		} finally {
			writeLock().unlock();
		}
	}
	
	/**
	 * Check if there's a property file for the given template key 
	 * by checking if a file with same base filename exist.
	 * 
	 * @param templatekey
	 * 			Reference {@code Template}.
	 * @return
	 * 			The property file that matches the {@code Template}'s base filename.
	 */
	protected File getPropertyFile(Key templatekey) {
		File[] files = sourceDirectory.listFiles((dir, name) -> name.equalsIgnoreCase(templatekey + _PROPERTY_FILE_SUFFIX));
		if(files.length < 1)
			return null;
		else
			/* The assumption here is that, it is not possible to have 
			 * more than one property file for any given templatekey.
			 * it's like having two files with the same filename within 
			 * the same directory. */
			return files[0];
	}
	
	/**
	 * Sets the reference source directory where 
	 * the property files are located.
	 */
	@Required
	public void setSourceDirectory(File sourceDirectory) {
		notNull(sourceDirectory, "Source directory cannot be null");
		this.sourceDirectory = sourceDirectory;
		
		/* At this point, there's a possibility that source directory does not exist yet */
		ensureFolderExist(sourceDirectory);
	}
	
	/**
	 * Returns the reference source directory where 
	 * the property files are located.
	 */
	public File getSourceDirectory() {
		return sourceDirectory;
	}
	
}
