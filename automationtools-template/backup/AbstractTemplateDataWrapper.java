package com.automationtools.core.backup;

import java.util.Properties;

import com.automationtools.core.TemplateData;

/**
 * Base wrapper class of {@code TemplateData}. 
 * 
 * <p>
 * Subclasses of the class {@code AbstractTemplateDataWrapper} can parse or
 * transform the {@linkplain TemplateData#getContent() content} of the wrapped 
 * {@code TemplateData} into a different form of data. 
 * </p>
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 */
public abstract class AbstractTemplateDataWrapper extends TemplateData {
	
	/**
	 * Wrapped {@code TemplateData}
	 */
	private TemplateData wrapped;
	
	/**
	 * Constructor that accepts wrapped {@code TemplateData}
	 */
	public AbstractTemplateDataWrapper(TemplateData wrapped) {
		super();
		this.wrapped = wrapped;
	}
	
	/**
	 * Returns the wrapped {@code TemplateData}
	 */
	protected TemplateData getWrapped() {
		return wrapped;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public byte[] getContent() {
		return wrapped.getContent();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setContent(byte[] content) {
		wrapped.setContent(content);
	}

	/**
	 * {@inheritDoc}
	 */
	public Properties getMetadata() {
		return wrapped.getMetadata();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMetadata(Properties metadata) {
		wrapped.setMetadata(metadata);
	}
	
}
