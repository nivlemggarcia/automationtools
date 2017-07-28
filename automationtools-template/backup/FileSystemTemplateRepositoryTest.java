package com.automationtools.template;

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
import static com.automationtools.util.FileUtilities.*;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@ImportResource("classpath:filesystemtemplaterepositorytest-config.xml")
@ContextConfiguration(classes = {FileSystemTemplateRepositoryTest.class})
public class FileSystemTemplateRepositoryTest {
	
	private static final Logger log = LoggerFactory.getLogger(FileSystemTemplateRepositoryTest.class);
	
	@Bean
	public DataParser defaultTemplateDataParser() {
		return (in) -> Data.create(in);
	}
	
	@Autowired @Qualifier("fileSystemTemplateRepository")
	private Repository<Template<?>> fileSystemTemplateRepository;
	
	@Value("${source.directory}")
	private File sourceDirectory;
	
	private static final String BASE_FILENAME = "sample-template";
	
	@Before
	public void beforeTest() throws IOException {
		ensureFileExist(new File(sourceDirectory, BASE_FILENAME + ".txt"));
	}
	
	@Test
	public void testFileSystemTemplateRepository() {
		try {
			Collection<Template<?>> templates = fileSystemTemplateRepository.getAll();
			if(templates.isEmpty())
				Assert.fail("Repository is empty");
			
			for(Template<?> template : templates) {
				template.setData(Data.create("HAMBLABAGOOOM!".getBytes()));
				if(!template.update())
					Assert.fail("Failed to update template " + template.getKey().getRaw());
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
