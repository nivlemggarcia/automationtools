package com.automationtools.util;

import com.automationtools.web.exception.ResourceNotFoundException;

public final class PreCondition {
	
	private PreCondition() {
	}
	
	public static <T> T notNull(T resource) {
		if(resource == null)
			throw new ResourceNotFoundException();
		
		return resource;
	}

}
