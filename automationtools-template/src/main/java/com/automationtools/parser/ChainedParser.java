package com.automationtools.parser;

import static org.springframework.util.Assert.*;
import java.util.Arrays;
import java.util.Iterator;
import com.automationtools.exception.ParsingFailedException;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
@SuppressWarnings("rawtypes")
public class ChainedParser implements Parser {
	
	private Parser<?> wrapped;
	
	private Parser<?> next;
	
	public ChainedParser(Parser<?> parser, Parser<?> next) {
		this.wrapped = parser;
		this.next = next;
	}

	@Override
	public Object parse(byte[] arg) throws ParsingFailedException {
		try {
			Object parsed = wrapped.parse(arg);
			return parsed == null ? next.parse(arg) : parsed;
		} catch (Exception e) {
			if(next != null)
				return next.parse(arg);
			else
				throw new ParsingFailedException("Could not find appropriate Parser");
		}
	}
	
	/**
	 * Factory method that creates a {@code ChainedParser} from {@linkplain Iterable}. 
	 */
	public static Parser<?> create(Iterable<Parser<?>> parsers) {
		notNull(parsers, "Parsers cannot be null");
		Iterator<Parser<?>> it = parsers.iterator();
		if(it.hasNext()) {
			ChainedParser parser = new ChainedParser(it.next(), null);
			while(it.hasNext()) {
				ChainedParser next = new ChainedParser(it.next(), parser);
				parser = next;
			}
			
			return parser;
		}
		
		return null;
	}
	
	/**
	 * Factory method that creates a {@code ChainedParser} using the 
	 * supplied varargs. 
	 */
	public static Parser<?> create(Parser<?> ... parsers) {
		return create(Arrays.asList(parsers));
	}

}
