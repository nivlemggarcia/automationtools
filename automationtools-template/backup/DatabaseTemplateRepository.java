package com.automationtools.template.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;
import com.automationtools.core.Data;
import com.automationtools.template.Repository;
import com.automationtools.template.Template;
import com.automationtools.template.TemplateRepository;
import com.automationtools.template.dao.DatabaseTemplateRepositoryDao;
import com.automationtools.template.dao.DatabaseTemplateRepositoryDao.Record;

/**
 * The class {@code DatabaseTemplateRepository} is a concrete implementation
 * of {@code Repository} that stores and manages {@code Template}s from a
 * <em>data access object</em> (DAO).
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 * @see		Repository
 * @see		TemplateRepository
 */
public class DatabaseTemplateRepository extends TemplateRepository {
	
	private static final Logger log = LoggerFactory.getLogger(DatabaseTemplateRepository.class);
	
	private DatabaseTemplateRepositoryDao dao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Template> getAll() throws Exception {
		Assert.state(dao != null, "DAO is null");
		Collection<Record> records = dao.getAll();
		if(records.size() < 1) {
			log.warn("No files found");
			return null;
		}
		
		log.info("Initializing ...");
		Set<Template> templates = new HashSet<>();
		readLock().lock();
		try {
//			for(Record record : records) {
//				FileTemplateKey key = new FileTemplateKey(templatefile);
//				try {
//					/* Parse the raw data */
//					RawData raw = getParser().parse(record.content);
//					if(propertiesLookup != null)
//						raw.setMetadata(propertiesLookup.get(key));
//					
//					/* Construct the template */
//					Template<?> template = new Template<>(raw);
//					template.setKey(key);
//					template.setSource(this);
//					if(!templates.add(template))
//						log.debug("Skipped {} [ Possible duplicate ]", templatefile.getName());
//					else
//						log.debug("Loaded {} as {}", templatefile.getName(), template.getDataType().getSimpleName());
//				} catch (Exception e) {
//					log.error("Error occurred while parsing file '" + templatefile.getName() + "'", e);
//				}
//			}
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
		Assert.state(dao != null, "DAO is null");
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean update(Template arg) throws Exception {
		Assert.state(dao != null, "DAO is null");
		return false;
	}
	
	/**
	 * 
	 */
	@Required
	public void setDao(DatabaseTemplateRepositoryDao dao) {
		Assert.notNull(dao);
		this.dao = dao;
	}
	
	/**
	 * 
	 */
	public DatabaseTemplateRepositoryDao getDao() {
		return dao;
	}
	
}
