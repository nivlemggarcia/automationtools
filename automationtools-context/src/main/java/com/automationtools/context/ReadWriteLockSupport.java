package com.automationtools.context;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Class that offer read and write locking mechanism.
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public class ReadWriteLockSupport {
	
	/**
	 * {@code Lock} used for reading and writing.
	 */
	private ReadWriteLock lock;
	
	/**
	 * Default constructor that creates a new 
	 * {@code ReentrantReadWriteLock} with default 
	 * (nonfair) ordering properties.
	 */
	public ReadWriteLockSupport() {
		lock = new ReentrantReadWriteLock();
	}
	
	/**
	 * Constructor that accepts boolean to set the 
	 * {@linkplain ReentrantReadWriteLock#isFair() fairness policy} 
	 * of {@code ReentrantReadWriteLock}.
	 */
	public ReadWriteLockSupport(boolean isfair) {
		lock = new ReentrantReadWriteLock(isfair);
	}
	
	/**
	 * Sets the {@code Lock} used for reading and writing.
	 */
	public void setLock(ReadWriteLock lock) {
		this.lock = lock;
	}
	
	/**
	 * Returns the {@code Lock} used for reading and writing.
	 */
	public ReadWriteLock getLock() {
		return lock;
	}
	
	/**
	 * Returns the {@code Lock} used for reading.
	 */
	protected Lock readLock() {
		return lock.readLock();
	}
	
	/**
	 * Returns the {@code Lock} used for writing.
	 */
	protected Lock writeLock() {
		return lock.writeLock();
	}

}
