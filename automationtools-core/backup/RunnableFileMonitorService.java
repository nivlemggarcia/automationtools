package com.automationtools.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardWatchEventKinds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automationtools.core.backup.FileMonitorService;

public class RunnableFileMonitorService {
	
	private static final Logger log = LoggerFactory.getLogger(RunnableFileMonitorService.class);

	public static void main(String[] args) throws IOException {
		final String TARGET_PATH = "C:/Users/IBM_ADMIN/Workspace/Sprint/EAITS/FileSystemEventMonitor/monitor";
		FileMonitorService service = new FileMonitorService();
		service.setFile(new File(TARGET_PATH));
		service.setEvents(
			StandardWatchEventKinds.ENTRY_CREATE
		);
		service.setWorker((ev) -> log.info("BLAH! -> " + ev.context() + " Kind:" + ev.kind()));
		service.start();
	}
	
}
