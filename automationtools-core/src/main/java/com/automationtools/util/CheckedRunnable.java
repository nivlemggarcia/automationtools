package com.automationtools.util;

@FunctionalInterface
public interface CheckedRunnable<E extends Throwable> {

    public abstract void run() throws E;
    
}
