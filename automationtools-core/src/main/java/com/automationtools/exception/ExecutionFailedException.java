package com.automationtools.exception;

/**
 * The class {@code ExecutionFailedException} and its subclasses are a form of
 * {@code RuntimeException} that indicates an unexpected error occurred during execution.
 * 
 * <p>{@code ExecutionFailedException} and its subclasses are <em>unchecked
 * exceptions</em>. Unchecked exceptions do <em>not</em> need to be
 * declared in a method or constructor's {@code throws} clause if they
 * can be thrown by the execution of the method or constructor and
 * propagate outside the method or constructor boundary.
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class ExecutionFailedException extends RuntimeException {
	
	/** Generated serialVersionUID */
	private static final long serialVersionUID = 5174417116450691022L;
	
	/**
	 * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
	 */
	public ExecutionFailedException() {
		super();
	}
	
	/**
	 * Constructs a new exception with the specified detail message. The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     * 
	 * @param message	The detail message. The detail message is saved for
     *          		later retrieval by the {@link #getMessage()} method.
	 */
	public ExecutionFailedException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a new exception with the specified cause and a detail
     * message of <tt>(cause==null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables.
     * 
	 * @param t			the cause (which is saved for later retrieval by the
     *         			{@link #getCause()} method).  (A <tt>null</tt> value is
     *         			permitted, and indicates that the cause is nonexistent or
     *         			unknown.)
	 */
	public ExecutionFailedException(Throwable t) {
		super(t);
	}
	
	/**
	 * Constructs a new exception with the specified detail message and
     * cause. <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this exception's detail message.
     * 
	 * @param message	the detail message (which is saved for later retrieval
     *         			by the {@link #getMessage()} method).
     *         
	 * @param t			the cause (which is saved for later retrieval by the
     *         			{@link #getCause()} method). (A <tt>null</tt> value is
     *         			permitted, and indicates that the cause is nonexistent or
     *         			unknown.)
	 */
	public ExecutionFailedException(String message, Throwable t) {
		super(message, t);
	}
	
}
