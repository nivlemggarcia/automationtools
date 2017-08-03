package com.automationtools.services;

import java.util.Collection;
import javax.inject.Inject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.automationtools.exception.ApplicationException;
import com.automationtools.template.Key;
import com.automationtools.template.Repository;
import com.automationtools.template.Template;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
@RestController
@RequestMapping("/scripts")
public class ScriptManagementService {
	
	private static final Logger log = LoggerFactory.getLogger(ScriptManagementService.class);
	
	/**
	 * Service which does all the template manipulation/retrieval work.  
	 */
	@Inject
	private Repository<Key, Template> scripts;
	
	/**
	 * Returns all the {@code Template} instances from the {@code Repository}.
	 */
	@GetMapping(produces = {
			MediaType.APPLICATION_JSON_VALUE, 
			MediaType.APPLICATION_XML_VALUE
		}
	)
	@ResponseBody
	public ResponseEntity<Collection<Template>> getAll() {
		Collection<Template> templates = null;
		try {
			templates = scripts.getAll();
		} catch (Exception e) {
			log.error("Failed to fetch templates", e);
			throw new ApplicationException();
		}
		
		if(templates == null || templates.isEmpty())
			return new ResponseEntity<Collection<Template>>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<>(templates, HttpStatus.OK);
	}
	
	/**
	 * Returns a single {@code Template} instance from the {@code Repository}
	 * with identifier matching the input id.
	 * 
	 * @param id	{@code Template} identifier
	 */
	@GetMapping(value = "/{id}", produces = {
			MediaType.APPLICATION_JSON_VALUE, 
			MediaType.APPLICATION_XML_VALUE
		}
	)
	@ResponseBody
	public ResponseEntity<Template> get(@PathVariable String id) {
		Template template = null;
		try {
			template = scripts.get(Key.create(id));
		} catch (Exception e) {
			log.error("Failed to fetch template with id " + id, e);
			throw new ApplicationException();
		}
		
		if(template == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(template, HttpStatus.OK);
	}
	
//	/**
//	 * 
//	 * @param id
//	 */
//	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//	@ResponseStatus(HttpStatus.OK)
//	public void delete(@PathVariable String id) {
//		try {
//			Template template = scripts.get(Key.create(id));
//			template.delete();
//		} catch (Exception e) {
//			log.error("Failed to delete template with id " + id, e);
//			throw new ApplicationException();
//		}
//	}
//	
//	/**
//	 * 
//	 * @param id
//	 * @param data
//	 */
//	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//	@ResponseStatus(HttpStatus.OK)
//	public void update(@PathVariable String id, @RequestBody Data data) {
//		try {
//			Template template = scripts.get(Key.create(id));
//			template.setData(data);
//			template.update();
//		} catch (Exception e) {
//			log.error("Failed to update template with id " + id, e);
//			throw new ApplicationException();
//		}
//	}
//	
//	/**
//	 * 
//	 * @param id
//	 * @param raw
//	 */
//	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, 
//		consumes = {
//			MediaType.TEXT_PLAIN_VALUE, 
//			MediaType.TEXT_XML_VALUE
//		}
//	)
//	@ResponseStatus(HttpStatus.OK)
//	public void update(@PathVariable String id, @RequestBody String raw) {
//		try {
//			Data data = Data.create(raw);
//			Template template = scripts.get(Key.create(id));
//			template.setData(data);
//			template.update();
//		} catch (Exception e) {
//			log.error("Failed to update template with id " + id, e);
//			throw new ApplicationException();
//		}
//	}
//	
//	/**
//	 * 
//	 * @param id
//	 * @param charset
//	 * @param raw
//	 */
//	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//	@ResponseStatus(HttpStatus.OK)
//	public void update(@PathVariable String id, @RequestParam("charset") String charset, @RequestBody byte[] raw) {
//		try {
//			Data data = Data.create(raw, Charset.forName(charset));
//			Template template = scripts.get(Key.create(id));
//			template.setData(data);
//			template.update();
//		} catch (Exception e) {
//			log.error("Failed to update template with id " + id, e);
//			throw new ApplicationException();
//		}
//	}
//	
//	/**
//	 * 
//	 * @param name
//	 * @param data
//	 * @return
//	 */
//	@RequestMapping(method = RequestMethod.PUT, 
//		produces = {
//			MediaType.APPLICATION_JSON_VALUE, 
//			MediaType.APPLICATION_XML_VALUE
//		}
//	)
//	@ResponseBody
//	public Key add(@RequestParam("name") String name, @RequestBody Data data) {
//		Key key = null;
//		try {
//			key = scripts.create(name, Template.wrap(data));
//		} catch (Exception e) {
//			log.error("Failed to add template " + name, e);
//			throw new ApplicationException();
//		}
//		
//		return notNull(key);
//	}
//	
//	/**
//	 * 
//	 * @param id
//	 * @param raw
//	 */
//	@RequestMapping(method = RequestMethod.PUT, 
//		consumes = {
//			MediaType.TEXT_PLAIN_VALUE, MediaType.TEXT_XML_VALUE
//		}, 
//		produces = {
//			MediaType.APPLICATION_JSON_VALUE, 
//			MediaType.APPLICATION_XML_VALUE
//		}
//	)
//	@ResponseStatus(HttpStatus.OK)
//	public Key add(@RequestParam("name") String name, @RequestBody String raw) {
//		Key key = null;
//		try {
//			Data data = Data.create(raw);
//			key = scripts.create(name, Template.wrap(data));
//		} catch (Exception e) {
//			log.error("Failed to add template " + name, e);
//			throw new ApplicationException();
//		}
//		
//		return notNull(key);
//	}
//	
//	/**
//	 * 
//	 * @param name
//	 * @param charset
//	 * @param raw
//	 * @return
//	 */
//	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//	@ResponseBody
//	public Key add(@RequestParam("name") String name, @RequestParam("charset") String charset, @RequestBody byte[] raw) {
//		Key key = null;
//		try {
//			Data data = Data.create(raw, Charset.forName(charset));
//			key = scripts.create(name, Template.wrap(data));
//		} catch (Exception e) {
//			log.error("Failed to add template " + name, e);
//			throw new ApplicationException();
//		}
//		
//		return notNull(key);
//	}
	
}
