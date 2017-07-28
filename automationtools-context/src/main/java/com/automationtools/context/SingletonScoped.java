package com.automationtools.context;

import org.springframework.context.annotation.Scope;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation that implements Spring annotation: 
 * 
 * <pre>
 * {@code @scope("singleton")}
 * </pre>
 *  
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
@Scope("singleton")
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SingletonScoped {
}
