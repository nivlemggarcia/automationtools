package com.automationtools.template;

import static org.springframework.util.Assert.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

/**
 * The class {@code DefaultTemplateRepository} is a concrete implementation
 * of {@linkplain Repository} that manages {@code Template} instances
 * from {@linkplain #getSources() various sources}. 
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 */
public class DefaultTemplateRepository implements Repository<Key, Template>, Observer {
	
	private static final Logger log = LoggerFactory.getLogger(DefaultTemplateRepository.class);
	
	/**
	 * Holds the references to all {@code Template}. 
	 * This map uses {@linkplain Key} object to define 
	 * every {@code Template} instance.
	 */
	private volatile Map<Key, Template> repository;
	
	/**
	 * The sources where this repository gets all 
	 * {@code Template} <strong>raw</strong> data.
	 */
	private Set<TemplateRepository> sources;
	
	/**
	 * The default {@code TemplateRepository} where 
	 * new {@code Template} will be stored.
	 */
	private TemplateRepository defaultSource;
	
	/**
	 * Reloads this {@code Repository} whenever a change in state in any of the 
	 * referenced {@linkplain #getSources() source repository} has been observed.
	 */
	@Override
	public void update(Observable o, Object arg) {
		log.debug("Change detected from {}. Reloading repository ...", o.getClass().getSimpleName());
		reload();
	}

	/**
	 * Replaces the content of this repository with {@code Template} instances from 
	 * {@linkplain #loadTemplateFromSources() various sources}. 
	 */
	@Override
	public void reload() {
		this.repository = Collections.unmodifiableMap(loadTemplateFromSources());
	}
	
	/**
	 * Loads all the {@code Template} instances from
	 * {@linkplain DefaultTemplateRepository#getSources() various sources}
	 */
	protected Map<Key, Template> loadTemplateFromSources() {
		Map<Key, Template> templates = new HashMap<>();
		for(TemplateRepository source : sources) {
			try {
				/* Gets a fresh copy of all contents */
				source.reload();
				Collection<Template> t = source.getAll();
				if(t == null || t.isEmpty()) {
					log.debug("Skipping empty source '{}'", source.getClass().getSimpleName());
					continue;
				}
				
				log.debug("Source: {}, Templates found: {}", source.getClass().getSimpleName(), t.size());
				for(Template template : t) {
					Template old = templates.put(template.getKey(), template);
					if(old != null)
						log.debug("{} has been replaced by {}", old.getKey(), template.getKey());
				}
			} catch (Exception e) {
				log.warn("Failed to load tests from " + source.toString() + ". Skipping ...", e);
			}
		}
		
		log.debug("Total Templates found: {}", templates.size());
		return templates;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Template> getAll() throws Exception {
		state(repository != null, "Repository not yet initialized");
		return repository.values();
	}
	
	/**
	 * Returns a {@linkplain Template} instance based on
	 * the {@code Key} provided.
	 * 
	 * @param key	Reference {@code Key}
	 */
	@Override
	public Template get(Key key) {
		state(repository != null, "Repository not yet initialized");
		return repository.get(key);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean delete(Template arg) throws Exception {
		/* 
		 * Delegates delete function to Template class.
		 * Template class has a handle of which TemplateRepository 
		 * it came from which implements a specific way on how to remove 
		 * resources.
		 */
		return arg.delete();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean update(Template arg) throws Exception {
		/* 
		 * Delegates update function to Template class.
		 * Template class has a handle of which TemplateRepository 
		 * it came from which implements a specific way on how to update 
		 * resources.
		 */
		return arg.update();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Key create(String name, Template arg) throws Exception {
		state(!sources.isEmpty(), "TemplateRepositories cannot be empty");
		if(defaultSource == null)
			/* If not defined, then get the first from the 
			 * registered sources and use that as default */
			defaultSource = sources.iterator().next();
		
		return defaultSource.create(name, arg);
	}
	
	/**
	 * Sets the sources where all the {@code Template}
	 * <strong>raw</strong> data are stored.
	 */
	@Required
	public void setSources(Set<TemplateRepository> sources) {
		notNull(sources, "TemplateRepositories cannot be null");
		state(!sources.isEmpty(), "TemplateRepositories cannot be empty");
		this.sources = sources;
		/* Listens to all referenced repositories */
		this.sources.forEach((s) -> s.addObserver(this));
	}
	
	/**
	 * Returns the sources where all the {@code Template}
	 * <strong>raw</strong> data are stored.
	 */
	public Set<TemplateRepository> getSources() {
		return sources;
	}

	/**
	 * Returns the references to all {@code Template} that is 
	 * stored to this repository in a form of a {@linkplain Map}.
	 */
	public Map<Key, Template> getRepository() {
		return repository;
	}
	
	/**
	 * Sets the default source where created 
	 * {@code Template}s will be stored.
	 */
	public void setDefaultSource(TemplateRepository defaultSource) {
		this.defaultSource = defaultSource;
	}
	
	/**
	 * Returns the default source where created 
	 * {@code Template}s will be stored.
	 */
	public TemplateRepository getDefaultSource() {
		return defaultSource;
	}
	
}
