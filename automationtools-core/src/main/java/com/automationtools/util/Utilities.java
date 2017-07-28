package com.automationtools.util;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Utilities {
	
	private Utilities() {
	}
	
	public static boolean containsAny(String target, Collection<String> filters) {
		for(String filter : filters) {
			Pattern p = Pattern.compile(".*" + filter + ".*", Pattern.DOTALL);
			Matcher m = p.matcher(target);
			if(m.matches())
				return true;
		}
		
		return false;
	}
	
	public static boolean containsAny(String target, String ... filters) {
		for(String filter : filters) {
			Pattern p = Pattern.compile(".*" + filter + ".*", Pattern.DOTALL);
			Matcher m = p.matcher(target);
			if(m.matches())
				return true;
		}
		
		return false;
	}
	
	@SafeVarargs
	public static <T> boolean containsAny(Collection<T> target, T ... filters) {
		for(T filter : filters) 
			if(target.contains(filter))
				return true;
		
		return false;
	}
	
	@SafeVarargs
	public static <T> boolean containsAll(Collection<T> target, T ... filters) {
		for(T filter : filters) 
			if(!target.contains(filter))
				return false;
		
		return true;
	}

	public static boolean equalsAny(Object target, Object ... objs) {
		boolean answer = false;
		
		for(Object obj : objs) {
			answer = answer || target.equals(obj);
		}
		
		return answer;
	}

}
