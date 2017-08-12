package com.automationtools.web.view;

import com.automationtools.template.Template;

/**
 * Used as <strong><em>view representation</em></strong>
 * of a {@code Template} {@code Data}.
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public abstract class TemplateView {
	
	/**
	 * The {@code Template} that this view component represents.
	 */
	protected Template wrapped;
	
	/**
	 * Creates a new instance of {@code TemplateView} 
	 * which wraps the supplied {@code Template}.
	 */
	public TemplateView(Template wrapped) {
		this.wrapped = wrapped;
	}
	
	/**
	 * Returns the identifier of this {@code TemplateView}.
	 */
	public String getKey() {
		return wrapped.getKey().toString();
	}
	
	/**
	 * Returns the label of this {@code TemplateView} which 
	 * will be displayed in the view component.
	 */
	public abstract String getLabel();
	
}
