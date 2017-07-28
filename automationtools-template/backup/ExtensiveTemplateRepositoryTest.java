package com.automationtools.template;

import static com.automationtools.util.FileUtilities.ensureFileExist;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
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
import com.automationtools.context.Data;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@ImportResource("classpath:extensivetemplaterepositorytest-config.xml")
@ContextConfiguration(classes = {ExtensiveTemplateRepositoryTest.class})
public class ExtensiveTemplateRepositoryTest {

	private static final Logger log = LoggerFactory.getLogger(ExtensiveTemplateRepositoryTest.class);
	
	@Bean
	public DataParser defaultTemplateDataParser() {
		return (in) -> Data.create(in);
	}
	
	@Autowired @Qualifier("extensiveTemplateRepository")
	private Repository<Template<?>> extensiveTemplateRepository;
	
	@Value("${source.directory}")
	private File sourceDirectory;
	
	@Value("${properties.directory}")
	private File propertiesDirectory;
	
	private static final String BASE_FILENAME = "sample-template";
	
	@Before
	public void beforeTest() throws IOException {
		ensureFileExist(
			new File(sourceDirectory, BASE_FILENAME + ".txt"), 
			new File(propertiesDirectory, BASE_FILENAME + ".properties")
		);
	}
	
	@Test
	public void testExtensiveTemplateRepository() {
		try {
			Collection<Template<?>> templates = extensiveTemplateRepository.getAll();
			if(templates.isEmpty())
				Assert.fail("Repository is empty");
			
			for(Template<?> template : templates) {
				template.getData().setContent("HAMBLABAGOOOM!".getBytes());
				if(!template.update())
					Assert.fail("Failed to update template " + template.getKey().getRaw());
				
				Properties p = new Properties();
				p.put("value", "BOINK");
				template.getData().setMetadata(p);
				if(!template.update())
					Assert.fail("Failed to update properties " + template.getKey().getRaw());
			}
			
			for(Template<?> template : templates)
				if(!template.delete())
					Assert.fail("Failed to delete " + template.getKey().getRaw());
		} catch (Exception e) {
			log.error("Error with " + e.getMessage(), e);
			Assert.fail(e.getMessage());
		}
	}
	
}
