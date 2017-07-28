package com.automationtools.core.backup;

import static com.automationtools.core.util.FileUtilities.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.nio.file.WatchEvent.Kind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

import com.automationtools.core.Context;
import com.automationtools.core.Handler;

/**
 * {@code FileMonitorService} is a facility for monitoring 
 * file system changes and events. This service utilizes 
 * {@linkplain WatchService} to listen to such events.
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 */
public class FileMonitorService {

	private static final Logger log = LoggerFactory.getLogger(FileMonitorService.class);
	
	/**
	 * Where file system events and 
	 * changes are coming from.
	 */
	private WatchService watcher; 
	
	/**
	 * The location in the file system which 
	 * this service is listening for events.
	 */
	private Path path;
	
	/**
	 * The type of events that this service is interested.
	 */
	private Kind<?>[] events;
	
	/**
	 * Manages the execution of each 
	 * {@linkplain #setWorker(Handler) worker}
	 */
	private ExecutorService executor;
	
	/**
	 * The {@code Worker} which will process the 
	 * {@linkplain WatchEvent event} once it happen.
	 */
	private Handler<WatchEvent<Path>> worker;
	
	/**
	 * Start listening for file system events 
	 * from the specified {@linkplain #setFile(File) path}.
	 * 
	 * @throws IOException 	If an I/O error occurs
	 */
	@SuppressWarnings("unchecked")
	public void start() throws IOException {
		Assert.notNull(this.path);
		ensureFolderExist(this.path.toFile());
		
		this.watcher = path.getFileSystem().newWatchService();
		if(events != null)
			this.path.register(watcher, events);
		
		executor = Executors.newCachedThreadPool();
		executor.execute(() -> {
			log.info("Started listening to " + path);
			while(true) {
				WatchKey key = null;
				try {
					key = watcher.take();
				} catch (InterruptedException e) {
					return;
				}

				for (WatchEvent<?> event : key.pollEvents()) {
					WatchEvent<Path> ev = (WatchEvent<Path>) event;
					executor.submit(() -> worker.handle(Context.wrap(ev)));
				}

				boolean valid = key.reset();
				if (!valid) {
					break;
				}
			}
		});
	}
	
	/**
	 * Returns the {@code ExecutorService} which manages 
	 * the execution of each file system event {@code Worker}.
	 */
	public ExecutorService getExecutor() {
		return executor;
	}
	
	/**
	 * Sets the {@code Worker} that is responsible for 
	 * handling the {@linkplain WatchEvent event} fired by the 
	 * {@code WatchService}. 
	 */
	@Required
	public void setWorker(Handler<WatchEvent<Path>> worker) {
		this.worker = worker;
	}
	
	/**
	 * Returns the {@code Worker} that is responsible for 
	 * handling the {@linkplain WatchEvent event} fired by the 
	 * {@code WatchService}. 
	 */
	public Handler<WatchEvent<Path>> getWorker() {
		return worker;
	}
	
	/**
	 * Sets the location in the file system which 
	 * this service will listen for events.
	 */
	@Required
	public void setFile(File file) {
		this.path = file.toPath();
	}
	
	/**
	 * The {@linkplain Path} equivalent of the 
	 * location in the file system which 
	 * this service is listening for events.
	 */
	public Path getPath() {
		return path;
	}
	
	/**
	 * Sets the type of {@linkplain WatchEvent.Kind events } 
	 * that this service will have to watch out for.
	 */
	public void setEvents(Kind<?> ... events) {
		this.events = events;
	}
	
	/**
	 * Returns the type of {@linkplain WatchEvent.Kind events} 
	 * that this service is listening to.
	 */
	public Kind<?>[] getEvents() {
		return events;
	}
	
}
