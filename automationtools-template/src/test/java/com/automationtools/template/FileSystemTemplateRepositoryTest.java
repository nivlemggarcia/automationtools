package com.automationtools.template;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.automationtools.core.Data;
import com.automationtools.util.CreateUuid;
import static com.automationtools.util.FileUtilities.*;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@ImportResource("classpath:filesystemtemplaterepositorytest-config.xml")
@ContextConfiguration(classes = {FileSystemTemplateRepositoryTest.class})
public class FileSystemTemplateRepositoryTest {
	
	private static final Logger log = LoggerFactory.getLogger(FileSystemTemplateRepositoryTest.class);
	
	@Autowired @Qualifier("fileSystemTemplateRepository")
	private TemplateRepository fileSystemTemplateRepository;
	
	@Value("${source.directory}")
	private String sourceDirectory;
	
	private static final String BASE_FILENAME = "sample-template";
	
	@Bean
	public List<Parser<?>> createParsers() {
		Parser<?> p = (raw) -> Data.create(raw);
		return Arrays.asList(p);
	}
	
	@Before
	public void beforeTest() throws IOException {
		ensureFileExist(new File(sourceDirectory, BASE_FILENAME + ".txt"));
	}
	
	@After
	public void afterTest() throws IOException {
		new File(sourceDirectory, BASE_FILENAME + ".txt").delete();
	}
	
	@Test
	public void testEqualKeys() {
		String id = CreateUuid.fromBytes(BASE_FILENAME.getBytes()).toString();
		Key k = Key.create(id);
		Template t = null;
		try {
			Collection<Template> templates = fileSystemTemplateRepository.getAll();
			for(Template template : templates)
				if(template.getKey().raw().startsWith(BASE_FILENAME)) {
					t = template;
					break;
				}
			
			Assert.assertTrue("Keys not equal", k.equals(t.getKey()));
			Assert.assertTrue("HashCode not equal", k.hashCode() == t.getKey().hashCode());
		} catch (Exception e) {
			log.error("Error with " + e.getMessage(), e);
			Assert.fail(e.getMessage());
		}
		
		
	}
	
	@Test
	public void testGetAll() {
		try {
			Collection<Template> templates = fileSystemTemplateRepository.getAll();
			if(templates.isEmpty())
				Assert.fail("Repository is empty");
		} catch (Exception e) {
			log.error("Error with " + e.getMessage(), e);
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testGet() {
		try {
			String id = CreateUuid.fromBytes(BASE_FILENAME.getBytes()).toString();
			Template t = fileSystemTemplateRepository.get(Key.create(id));
			Assert.assertTrue(t != null);
		} catch (Exception e) {
			log.error("Error with " + e.getMessage(), e);
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testUpdate() {
		try {
			Collection<Template> templates = fileSystemTemplateRepository.getAll();
			for(Template template : templates) {
				template.setData(Data.create("HAMBLABAGOOOM!".getBytes()));
				if(!template.update())
					Assert.fail("Failed to update template " + template.getKey().raw());
			}
		} catch (Exception e) {
			log.error("Error with " + e.getMessage(), e);
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testDelete() {
		try {
			Collection<Template> templates = fileSystemTemplateRepository.getAll();
			for(Template template : templates)
				if(!template.delete())
					Assert.fail("Failed to delete " + template.getKey().raw());
		} catch (Exception e) {
			log.error("Error with " + e.getMessage(), e);
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testCreate() {
		try {
			Template t = Template.wrap(Data.create("BLAH!"));
			Key k = fileSystemTemplateRepository.create("blahfile", t);
			log.info(k.raw());
		} catch (Exception e) {
			log.error("Error with " + e.getMessage(), e);
			Assert.fail(e.getMessage());
		}
	}
	
}
