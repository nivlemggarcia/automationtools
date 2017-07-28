package com.automationtools.util;

import com.automationtools.web.exception.ApplicationException;
import com.automationtools.web.exception.ResourceNotFoundException;

public final class PreCondition {
	
	private PreCondition() {
	}
	
//	public static <T> T noErrors(CheckedSupplier<T, Exception> arg) {
//		try {
//			return arg.get();
//		} catch (Exception e) {
//			throw new ApplicationException();
//		}
//	}
//	
//	public static void noErrors(CheckedRunnable<Exception> arg) {
//		try {
//			arg.run();
//		} catch (Exception e) {
//			throw new ApplicationException();
//		}
//	}
	
	public static <T> T notNull(T resource) {
		if(resource == null)
			throw new ResourceNotFoundException();
		
		return resource;
	}

}
