package com.automationtools.util;

import java.util.Objects;

/**
 * Represents a function that accepts one argument and produces a result 
 * and is capable of throwing <em>checked exceptions</em> if necessary.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(Object)}.
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 * @param <E> the type of the exception that is thrown by the function
 *
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
@FunctionalInterface
public interface CheckedFunction<T, R, E extends Throwable> {
	
	/**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    R accept(T t) throws E;
	
	/**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    default R apply(T t) throws E {
    	return accept(t);
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V> the type of input to the {@code before} function, and to the
     *           composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     *
     * @see #andThen(Function)
     */
    default <V> CheckedFunction<V, R, E> compose(
    		CheckedFunction<? super V, ? extends T, ? extends E> before) throws E {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V> the type of output of the {@code after} function, and of the
     *           composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     * applies the {@code after} function
     * @throws NullPointerException if after is null
     *
     * @see #compose(Function)
     */
    default <V> CheckedFunction<T, V, E> andThen(
    		CheckedFunction<? super R, ? extends V, ? extends E> after) throws E {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    /**
     * Returns a function that always returns its input argument.
     *
     * @param <T> the type of the input and output objects to the function
     * @return a function that always returns its input argument
     */
    static <T> CheckedFunction<T, T, Throwable> identity() {
        return t -> t;
    }

}
