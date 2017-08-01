package com.automationtools.template;

import static com.automationtools.util.FileUtilities.*;
import static org.apache.commons.io.FilenameUtils.*;
import static org.springframework.util.Assert.*;
import static org.apache.commons.io.FileUtils.*;
import java.io.File;
import java.io.FileFilter;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import com.automationtools.core.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	 * Loads all the {@code Template} instances from the {@linkplain #getSourceDirectory() source directory}.
	 */
	protected Map<Key, Template> loadTemplates() {
		state(sourceDirectory != null, "Source directory is null.");
		File[] files = sourceDirectory.listFiles(filter);
		if(files.length < 1) {
			log.warn("No files found");
			return null;
		}
		
		log.info("Initializing ...");
		Map<Key, Template> templates = new HashMap<>();
		readLock().lock();
		try {
			for(File templatefile : files) {
				FileTemplateKey key = new FileTemplateKey(templatefile);
				try {
					/* Parse the raw data */
					Data raw = parser.parse(Files.readAllBytes(templatefile.toPath()));
					
					/* Construct the template */
					Template template = Template.wrap(raw);
					template.setKey(key);
					template.setSource(this);
					Template replaced = templates.put(key, template);
					if(replaced != null)
						log.debug("Replaced {} [ Possible duplicate ]", replaced.getKey());
					else
						log.debug("Loaded {} as {}", templatefile.getName(), raw.getClass().getSimpleName());
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
	public boolean delete(Template arg) throws Exception {
		writeLock().lock();
		try {
			/* Delete the template file */
			File f = ((FileTemplateKey) arg.getKey()).getTemplateFile();
			return Files.deleteIfExists(f.toPath());
		} finally {
			writeLock().unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean update(Template arg) throws Exception {
		writeLock().lock();
		try {
			/* Update the template file using the FileTemplateKey */
			File f = ((FileTemplateKey) arg.getKey()).getTemplateFile();
			Files.write(f.toPath(), arg.getData().getContent());
			/* Signifies file has been successfully updated */
			return true;
		} finally {
			writeLock().unlock();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Key create(String name, Template arg) throws Exception {
		writeLock().lock();
		try {
			arg.setSource(this);
			/* Create a text file -- default file format */
			File f = new File(sourceDirectory, name + ".txt");
			ensureFileExist(f);
			writeByteArrayToFile(f, arg.getData().getContent());
			FileTemplateKey key = new FileTemplateKey(f);
			arg.setKey(key);
			return key;
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
		notNull(sourceDirectory, "Source directory cannot be null");
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
	 * as identifier for {@code Template} instances which are 
	 * created based on a template file loaded from specific 
	 * location in the file system.
	 * 
	 * <p>
	 * This {@code Key} uses the file's base name as the 
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
		public String raw() {
			return getBaseName(templateFile.getName());
		}
		
		@JsonIgnore
		public File getTemplateFile() {
			return templateFile;
		}
		
	}

}
