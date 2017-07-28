package com.automationtools.core.monitor;

import static com.automationtools.util.FileUtilities.ensureFolderExist;
import static java.nio.file.StandardWatchEventKinds.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.WatchEvent.Kind;

/**
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class FileMonitorThread extends Thread {

	private static final Logger log = LoggerFactory.getLogger(FileMonitorThread.class);
	
	protected WatchService watcher; 
	
	protected Path path;
	
	protected Kind<?>[] events;
	
	public FileMonitorThread() {
		super();
		setDaemon(true);
	}

	public FileMonitorThread(File file) throws IOException {
		this(file, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
	}
	
	public FileMonitorThread(File file, Kind<?> ... events) throws IOException {
		this();
		ensureFolderExist(file);
		
		this.path = file.toPath();
		this.watcher = path.getFileSystem().newWatchService();
		this.path.register(watcher, events);
		this.events = events;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		log.info("Started listening to {}", path);
		while(true) {
			WatchKey key = null;
			try {
				key = watcher.take();
			} catch (InterruptedException e) {
				return;
			}

			for (WatchEvent<?> event : key.pollEvents()) {
				WatchEvent<Path> ev = (WatchEvent<Path>) event;
				processEvent(ev);
			}

			boolean valid = key.reset();
			if (!valid) {
				break;
			}
		}
	}
	
	public void processEvent(WatchEvent<Path> event) {
		log.debug("WatchEvent Fired -> {}", event);
	}
	
}
