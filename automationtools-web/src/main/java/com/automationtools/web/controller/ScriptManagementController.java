package com.automationtools.web.controller;

import java.io.Serializable;
import java.util.Collection;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.automationtools.template.Template;
import com.automationtools.template.TemplateRepository;
import com.automationtools.web.MessageBundle;
import com.automationtools.web.ViewScoped;

/**
 * 
 * @author Melvin Garcia
 * @since v.3.0
 */
@Named("manage")
@ViewScoped
public class ScriptManagementController implements Serializable {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 6626645408264476778L;

	private static final Logger log = LoggerFactory.getLogger(ScriptManagementController.class);

	@Inject
	private MessageBundle messageBundle;
	
	@Inject
	private TemplateRepository templateRepository;
	
	private transient Collection<Template> scripts;
	
	public void initialize() {
		try {
			scripts = templateRepository.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Collection<Template> getScripts() {
		return scripts;
	}

}
