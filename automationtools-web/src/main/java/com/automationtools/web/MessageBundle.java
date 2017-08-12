package com.automationtools.web;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletRequest;
import java.util.HashMap;

/**
 * 
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
@SuppressWarnings("rawtypes")
@Named("msg")
public class MessageBundle extends HashMap {

	/** Generated serialVersionUID */
	private static final long serialVersionUID = -741980073018210585L;
	
	@Inject
    private MessageSource messageSource;

    @Override
    public String get(Object key) {
        ServletRequest request = (ServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            return messageSource.getMessage((String) key, null, request.getLocale());
        } catch (NoSuchMessageException e) {
            return "???" + key + "???";
        }
    }
    
}
