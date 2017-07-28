package com.automationtools.util;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 */
public final class FileUtilities {

	private FileUtilities() {
	}
	
	public static void ensureFolderExist(File ... files) {
		for (File file : files) {
			if(file.exists())
				continue;
			
			synchronized (file) {
				file.mkdirs();
			}
		}
	}
	
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
