package com.automationtools.core.monitor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Melvin Garcia
 * @since v.3.1
 */
public class FileSystemEventProducer extends FileMonitorThread {
	
	private static final Logger log = LoggerFactory.getLogger(FileSystemEventProducer.class);
	
	public FileSystemEventProducer(File file, Kind<?>... events) throws IOException {
		super(file, events);
	}
	
	public FileSystemEventProducer(File file, FileSystemEvent.EventTypes ... events) throws IOException {
		super(file, toKind(events));
	}
	
	protected static Kind<?>[] toKind(FileSystemEvent.EventTypes ... events) {
		List<Kind<?>> kinds = new ArrayList<>();
		for(FileSystemEvent.EventTypes event : events)
			kinds.add(event.toWatchEventKind());
		
		return kinds.toArray(new Kind<?>[kinds.size()]);
	}
	
	@Override
	public void processEvent(WatchEvent<Path> event) {
		fireFileSystemEvent(new FileSystemEvent(this.path, event.context(), event.kind()));
	}
	
	public void fireFileSystemEvent(FileSystemEvent event) {
		log.debug("FileSystemEvent Fired -> {}", event);
	}
	
}
