package com.automationtools.template.impl;

import static com.automationtools.util.FileUtilities.ensureFolderExist;
import static org.apache.commons.io.FilenameUtils.*;
import java.io.File;
import java.io.FileFilter;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;
import com.automationtools.context.Data;
import com.automationtools.context.ParameterizedData;
import com.automationtools.template.Key;
import com.automationtools.template.Repository;
import com.automationtools.template.Template;
import com.automationtools.template.TemplateRepository;

/**
 * The class {@code FileSystemTemplateRespository} is a concrete implementation
 * of {@code Repository} that stores and manages {@code Template}s from a
 * specific location in the file system.
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 * @see		Repository
 * @see		TemplateRepository
 */
public class FileSystemTemplateRepository extends TemplateRepository {
	
	private static final Logger log = LoggerFactory.getLogger(FileSystemTemplateRepository.class);
	
	/**
	 * The reference to source directory where 
	 * <strong>raw</strong> {@code Template} files is stored.
	 */
	private File sourceDirectory;
	
	/**
	 * {@code FileFilter} used for filtering {@code Template} files.
	 */
	private FileFilter filter;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Template<?>> getAll() throws Exception {
		Assert.state(sourceDirectory != null, "Source directory is null.");
		File[] files = sourceDirectory.listFiles(filter);
		if(files.length < 1) {
			log.warn("No files found");
			return null;
		}

		log.info("Initializing ...");
		Set<Template<?>> templates = new HashSet<>();
		readLock().lock();
		try {
			for(File templatefile : files) {
				FileTemplateKey key = new FileTemplateKey(templatefile);
				try {
					/* Parse the raw data */
					Data raw = getParser().parse(Files.readAllBytes(templatefile.toPath()));
					Properties properties = null;
					if(propertiesLookup != null)
						if((properties = propertiesLookup.get(key)) != null)
							/* Wrap the raw data if properties are found */
							raw = new ParameterizedData(raw, properties);
					
					/* Construct the template */
					Template<?> template = new Template<>(raw);
					template.setKey(key);
					template.setSource(this);
					if(!templates.add(template))
						log.debug("Skipped {} [ Possible duplicate ]", templatefile.getName());
					else
						log.debug("Loaded {} as {}", templatefile.getName(), template.getDataType().getSimpleName());
				} catch (Exception e) {
					log.error("Error occurred while parsing file '" + templatefile.getName() + "'", e);
				}
			}
			return templates;
		} finally {
			readLock().unlock();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean delete(Template<?> arg) throws Exception {
		writeLock().lock();
		try {
			/* Delete the template file */
			File f = ((FileTemplateKey) arg.getKey()).getTemplateFile();
			boolean deleted = Files.deleteIfExists(f.toPath());
			
			if(propertiesLookup != null)
				/* Delete the properties file, if it exist */
				deleted &= propertiesLookup.delete(arg.getKey());
			
			return deleted;
		} finally {
			writeLock().unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean update(Template<?> arg) throws Exception {
		writeLock().lock();
		try {
			boolean updated = false;
			/* Update the template file using the FileTemplateKey */
			File f = ((FileTemplateKey) arg.getKey()).getTemplateFile();
			Files.write(f.toPath(), arg.getData().getContent());
			/* Signifies file has been successfully updated */
			updated = true;
			
			/* Update the properties. Delegates the actual update process to
			 * the lookup strategy implementation where these properties came from */
			if((arg.getData() instanceof ParameterizedData) && propertiesLookup != null) {
				ParameterizedData data = (ParameterizedData) arg.getData();
				Properties p = new Properties();
				for(Entry<String, String> entry : data.getParameters().entrySet())
					p.put(entry.getKey(), entry.getValue());
				
				updated &= propertiesLookup.update(arg.getKey(), p);
			}
			
			return updated;
		} finally {
			writeLock().unlock();
		}
	}
	
	/**
	 * Sets the {@code FileFilter} used for 
	 * filtering {@code Template} files.
	 */
	public void setFilter(FileFilter filter) {
		this.filter = filter;
	}
	
	/**
	 * Returns the {@code FileFilter} used for 
	 * filtering {@code Template} files.
	 */
	public FileFilter getFilter() {
		return filter;
	}
	
	/**
	 * Sets the reference to source directory where 
	 * <strong>raw</strong> {@code Template} files is stored.
	 * @param sourceDirectory
	 */
	@Required
	public void setSourceDirectory(File sourceDirectory) {
		Assert.notNull(sourceDirectory);
		this.sourceDirectory = sourceDirectory;
		
		/* At this point, there's a possibility that source directory does not exist yet */
		ensureFolderExist(sourceDirectory);
	}
	
	/**
	 * Returns the reference to source directory where 
	 * <strong>raw</strong> {@code Template} files is stored.
	 * @param sourceDirectory
	 */
	public File getSourceDirectory() {
		return sourceDirectory;
	}
	
	/**
	 * The {@linkplain Key} implementation that will be used 
	 * for {@code Template} instances loaded from specific 
	 * location in the file system.
	 * 
	 * <p>
	 * This {@code Key} uses the file's basename as the 
	 * {@linkplain Key#getRaw() identifier}.
	 * </p>
	 * 
	 * @author Melvin Garcia
	 */
	class FileTemplateKey extends UUIDEncodedKey {

		/** Generated serialVersionUID */
		private static final long serialVersionUID = 1670566909908037357L;
		
		private File templateFile;
		
		public FileTemplateKey(File templateFile) {
			this.templateFile = templateFile;
		}
		
		@Override
		public String getRaw() {
			return getBaseName(templateFile.getName());
		}
		
		public File getTemplateFile() {
			return templateFile;
		}
		
		@Override
		public int hashCode() {
			return templateFile.hashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj == null)
				return false;
			
			if(!(obj instanceof FileTemplateKey))
				return false;
			
			if(!super.equals(obj))
				return false;
			
			FileTemplateKey other = (FileTemplateKey) obj;
			/* Checks if both are referenced to the same template file */
			return this.getTemplateFile().equals(other.getTemplateFile());
		}
		
		@Override
		public String toString() {
			return getRaw();
		}
	}

}
