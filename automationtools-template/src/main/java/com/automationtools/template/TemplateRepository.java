package com.automationtools.template;

import static org.springframework.util.Assert.notNull;
import static org.springframework.util.Assert.state;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * This serves as the base class of all {@code Repository} 
 * implementation that stores and manages {@code Template}s.
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public abstract class TemplateRepository implements Repository<Key, Template> {
	
	/**
	 * Holds the references to all {@code Template} that is 
	 * stored to this repository. This map uses {@linkplain Key}
	 * object to define every {@code Template} instance.
	 */
	private volatile Map<Key, Template> repository;
	
	/**
	 * Replaces the content of this repository with {@code Template} instances from 
	 * {@linkplain #load() the source}. 
	 */
	@Override
	public void reload() {
		this.repository = Collections.unmodifiableMap(load());
	}
	
	/**
	 * Loads all the {@code Template} instances from this {@code TemplateRepository}.
	 */
	protected abstract Map<Key, Template> load();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Template> getAll() throws Exception {
		state(repository != null, "Repository not yet initialized");
		return repository.values();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Template get(Key arg) throws Exception {
		notNull(arg, "Key cannot be null");
		state(repository != null, "Repository not yet initialized");
		return repository.get(arg);
	}
	
}
