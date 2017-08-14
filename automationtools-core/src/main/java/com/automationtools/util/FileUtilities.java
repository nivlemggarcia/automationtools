package com.automationtools.util;

import java.io.File;
import java.io.IOException;

/**
 * A utility class for {@code File}-related operations.
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public final class FileUtilities {

	private FileUtilities() {
	}
	
	/**
	 * A convenience method that will ensure all the 
	 * folder/s specified as argument exist. 
	 */
	public static void ensureFolderExist(File ... files) {
		for (File file : files) {
			if(file.exists())
				continue;
			
			synchronized (file) {
				file.mkdirs();
			}
		}
	}
	
	/**
	 * A convenience method that will ensure all the 
	 * file/s specified as argument exist. 
	 */
	public static void ensureFileExist(File ... files) throws IOException {
		for (File file : files) {
			if(file.exists())
				continue;
			
			ensureFolderExist(file.getParentFile());
			synchronized (file) {
				if(!file.createNewFile())
					throw new RuntimeException(String.format("Failed to create file %s", file.getName()));
			}
		}
	}
}
