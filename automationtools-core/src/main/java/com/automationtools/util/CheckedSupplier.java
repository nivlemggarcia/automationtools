package com.automationtools.util;

/**
 * Represents a supplier of results that is capable of 
 * throwing <em>checked exceptions</em> if necessary.
 *
 * <p>There is no requirement that a new or distinct 
 * result be returned each time the supplier is invoked.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #get()}.
 * 
 * @param <T>	the type of results supplied by this supplier.
 * @param <E> 	the type of exception that will be thrown by this supplier.
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
@FunctionalInterface
public interface CheckedSupplier<T, E extends Throwable> {
	
	/**
	 * Gets the result.
	 * 
	 * @return	the result.
	 * @throws E
	 * 			Exception thrown when getting the result.
	 */
	T get() throws E;

}
