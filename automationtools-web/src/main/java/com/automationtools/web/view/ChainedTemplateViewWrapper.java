package com.automationtools.web.view;

import static org.springframework.util.Assert.notNull;
import java.util.Arrays;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.automationtools.exception.NoSuitableWrapperFoundException;
import com.automationtools.template.Template;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public class ChainedTemplateViewWrapper implements TemplateViewWrapper {
	
	private static final Logger log = LoggerFactory.getLogger(ChainedTemplateViewWrapper.class);
	
	private TemplateViewWrapper wrapped;
	
	private TemplateViewWrapper next;
	
	public ChainedTemplateViewWrapper(TemplateViewWrapper wrapped, TemplateViewWrapper next) {
		this.wrapped = wrapped;
		this.next = next;
	}

	@Override
	public TemplateView wrap(Template t) {
		try {
			TemplateView view = wrapped.wrap(t);
			return view == null ? next.wrap(t) : view;
		} catch (Exception e) {
			if(next != null) {
				log.error("Error occured while parsing. Skipping to next TemplateViewWrapper ...", e);
				return next.wrap(t);
			} else 
				throw new NoSuitableWrapperFoundException("Could not find appropriate TemplateViewWrapper");
		}
	}
	
	/**
	 * Factory method that creates a {@code ChainedTemplateViewWrapper} from {@linkplain Iterable}. 
	 */
	public static TemplateViewWrapper create(Iterable<TemplateViewWrapper> wrappers) {
		notNull(wrappers, "Parsers cannot be null");
		Iterator<TemplateViewWrapper> it = wrappers.iterator();
		if(it.hasNext()) {
			ChainedTemplateViewWrapper wrapper = new ChainedTemplateViewWrapper(it.next(), null);
			while(it.hasNext()) {
				ChainedTemplateViewWrapper next = new ChainedTemplateViewWrapper(it.next(), wrapper);
				wrapper = next;
			}
			
			return wrapper;
		}
		
		return null;
	}
	
	/**
	 * Factory method that creates a {@code ChainedTemplateViewWrapper} using the 
	 * supplied varargs. 
	 */
	public static TemplateViewWrapper create(TemplateViewWrapper ... wrappers) {
		return create(Arrays.asList(wrappers));
	}

}
