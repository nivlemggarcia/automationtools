package com.automationtools.core.monitor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public abstract class FileSystemMonitorService extends FileMonitorThread {
	
	public FileSystemMonitorService(File file, Kind<?>... events) throws IOException {
		super(file, events);
	}
	
	public FileSystemMonitorService(File file, FileSystemEvent.EventTypes ... events) throws IOException {
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
	
	public abstract void fireFileSystemEvent(FileSystemEvent event);
	
}
