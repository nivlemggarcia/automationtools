package com.automationtools.web;

import static com.automationtools.util.PreCondition.*;
import java.nio.charset.Charset;
import java.util.Collection;
import javax.inject.Inject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.automationtools.core.Data;
import com.automationtools.template.Key;
import com.automationtools.template.Repository;
import com.automationtools.template.Template;
import com.automationtools.web.exception.ApplicationException;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
@RestController
@RequestMapping("/scripts")
public class ScriptTemplateProviderService {
	
	private static final Logger log = LoggerFactory.getLogger(ScriptTemplateProviderService.class);
	
	/**
	 * 
	 */
	@Inject
	private Repository<Key, Template> scripts;
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, 
		produces = {
			MediaType.APPLICATION_JSON_VALUE, 
			MediaType.APPLICATION_XML_VALUE
		}
	)
	@ResponseBody
	public Collection<Template> getAll() {
		Collection<Template> templates = null;
		try {
			scripts.reload();
			templates = scripts.getAll();
		} catch (Exception e) {
			log.error("Failed to fetch templates", e);
			throw new ApplicationException();
		}
		
		return notNull(templates);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, 
		produces = {
			MediaType.APPLICATION_JSON_VALUE, 
			MediaType.APPLICATION_XML_VALUE
		}
	)
	@ResponseBody
	public Template get(@PathVariable String id) {
		Template template = null;
		try {
			scripts.reload();
			template = scripts.get(Key.create(id));
		} catch (Exception e) {
			log.error("Failed to fetch template with id " + id, e);
			throw new ApplicationException();
		}
		
		return notNull(template);
	}
	
	/**
	 * 
	 * @param id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable String id) {
		try {
			Template template = scripts.get(Key.create(id));
			template.delete();
		} catch (Exception e) {
			log.error("Failed to delete template with id " + id, e);
			throw new ApplicationException();
		}
	}
	
	/**
	 * 
	 * @param id
	 * @param data
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable String id, @RequestBody Data data) {
		try {
			Template template = scripts.get(Key.create(id));
			template.setData(data);
			template.update();
		} catch (Exception e) {
			log.error("Failed to update template with id " + id, e);
			throw new ApplicationException();
		}
	}
	
	/**
	 * 
	 * @param id
	 * @param raw
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, 
		consumes = {
			MediaType.TEXT_PLAIN_VALUE, 
			MediaType.TEXT_XML_VALUE
		}
	)
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable String id, @RequestBody String raw) {
		try {
			Data data = Data.create(raw);
			Template template = scripts.get(Key.create(id));
			template.setData(data);
			template.update();
		} catch (Exception e) {
			log.error("Failed to update template with id " + id, e);
			throw new ApplicationException();
		}
	}
	
	/**
	 * 
	 * @param id
	 * @param charset
	 * @param raw
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable String id, @RequestParam("charset") String charset, @RequestBody byte[] raw) {
		try {
			Data data = Data.create(raw, Charset.forName(charset));
			Template template = scripts.get(Key.create(id));
			template.setData(data);
			template.update();
		} catch (Exception e) {
			log.error("Failed to update template with id " + id, e);
			throw new ApplicationException();
		}
	}
	
	/**
	 * 
	 * @param name
	 * @param data
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, 
		produces = {
			MediaType.APPLICATION_JSON_VALUE, 
			MediaType.APPLICATION_XML_VALUE
		}
	)
	@ResponseBody
	public Key add(@RequestParam("name") String name, @RequestBody Data data) {
		Key key = null;
		try {
			key = scripts.create(name, Template.wrap(data));
		} catch (Exception e) {
			log.error("Failed to add template " + name, e);
			throw new ApplicationException();
		}
		
		return notNull(key);
	}
	
	/**
	 * 
	 * @param id
	 * @param raw
	 */
	@RequestMapping(method = RequestMethod.PUT, 
		consumes = {
			MediaType.TEXT_PLAIN_VALUE, MediaType.TEXT_XML_VALUE
		}, 
		produces = {
			MediaType.APPLICATION_JSON_VALUE, 
			MediaType.APPLICATION_XML_VALUE
		}
	)
	@ResponseStatus(HttpStatus.OK)
	public Key add(@RequestParam("name") String name, @RequestBody String raw) {
		Key key = null;
		try {
			Data data = Data.create(raw);
			key = scripts.create(name, Template.wrap(data));
		} catch (Exception e) {
			log.error("Failed to add template " + name, e);
			throw new ApplicationException();
		}
		
		return notNull(key);
	}
	
	/**
	 * 
	 * @param name
	 * @param charset
	 * @param raw
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public Key add(@RequestParam("name") String name, @RequestParam("charset") String charset, @RequestBody byte[] raw) {
		Key key = null;
		try {
			Data data = Data.create(raw, Charset.forName(charset));
			key = scripts.create(name, Template.wrap(data));
		} catch (Exception e) {
			log.error("Failed to add template " + name, e);
			throw new ApplicationException();
		}
		
		return notNull(key);
	}
	
}
