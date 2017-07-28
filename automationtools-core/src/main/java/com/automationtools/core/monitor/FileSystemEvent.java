package com.automationtools.core.monitor;

import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;

/**
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class FileSystemEvent {
	
	public enum EventTypes {
		
		CREATE {
			@Override
			public Kind<Path> toWatchEventKind() {
				return StandardWatchEventKinds.ENTRY_CREATE;
			}
		},
		
		MODIFY {
			@Override
			public Kind<Path> toWatchEventKind() {
				return StandardWatchEventKinds.ENTRY_MODIFY;
			}
		},
		
		DELETE {
			@Override
			public Kind<Path> toWatchEventKind() {
				return StandardWatchEventKinds.ENTRY_DELETE;
			}
		},
		
		;
		
		public abstract WatchEvent.Kind<Path> toWatchEventKind();
		
	}
	
	private Path parentPath;
	
	private Path path;
	
	private WatchEvent.Kind<?> event;
	
	public FileSystemEvent(Path parentPath, Path path, EventTypes event) {
		this.parentPath = parentPath;
		this.path = path;
		this.event = event.toWatchEventKind();
	}
	
	public FileSystemEvent(Path parentPath, Path path, WatchEvent.Kind<?> event) {
		this.path = path;
		this.event = event;
		this.parentPath = parentPath;
	}
	
	public Path getPath() {
		return path;
	}
	
	public Path getParentPath() {
		return parentPath;
	}
	
	public WatchEvent.Kind<?> getType() {
		return event;
	}
	
	@Override
	public String toString() {
		return String.format("kind=%s filename=%s path=%s", event, path, parentPath);
	}
	
}
