package com.automationtools.core.monitor;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardWatchEventKinds;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@ContextConfiguration(classes = {
		FileMonitorServiceTest.class, 
})
public class FileMonitorServiceTest {
	
	private static final String TARGET_PATH = "C:/Users/IBM_ADMIN/Workspace/Sprint/EAITS/FileSystemEventMonitor/monitor";
	
	@Bean
	public FileMonitorThread createFileMonitorService() throws IOException {
		FileMonitorThread service = new FileMonitorThread(
				new File(TARGET_PATH), 
				StandardWatchEventKinds.ENTRY_CREATE, 
				StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.ENTRY_DELETE);
		return service;
	}

	@Autowired
	private FileMonitorThread monitor;
	
	@Test
	public void testListener() {
		try {
			monitor.start();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
