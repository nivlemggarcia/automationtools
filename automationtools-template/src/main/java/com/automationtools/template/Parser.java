package com.automationtools.template;

import com.automationtools.exception.ParsingFailedException;

/**
 * Interface for translating any given byte array into a type
 * indicated by the <em>generic type parameter</em> 
 * <strong>{@literal <T>}</strong>.
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 * @param <T>
 * 			The type of object produced after parsing.
 */
public interface Parser<T> {
	
	/**
	 * Translates the given byte array into a type
	 * indicated by the <em>generic type parameter</em>
	 * <strong>{@literal <T>}</strong>.
	 * 
	 * @param arg	The source.
	 * @return 		An object whose type is indicated by <strong>{@literal <T>}</strong>.
	 * @throws ParsingFailedException
	 */
	public T parse(byte[] arg) throws ParsingFailedException;

}
