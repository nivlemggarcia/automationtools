package com.automationtools.util;

import java.util.Objects;

/**
 * Represents an operation that accepts a single input argument and returns no
 * result with the capability to throw a <em>checked exception</em> if necessary. 
 * Unlike most other functional interfaces, {@code Consumer} is expected
 * to operate via side-effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object)}.
 *
 * @param <T> the type of the input to the operation
 * @param <E> the type of the exception that is thrown by the function
 *
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
@FunctionalInterface
public interface CheckedConsumer<T, E extends Throwable> {
	
	/**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t) throws E;

    /**
     * Returns a composed {@code CheckedConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default CheckedConsumer<T, E> andThen(CheckedConsumer<? super T, ? extends E> after) throws E {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }

}
