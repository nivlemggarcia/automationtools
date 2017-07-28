package com.automationtools.template;

import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import com.automationtools.core.Data;

public class ParserChainTest {
	
	private Parser<?> parser;
	
	@Before
	public void init() {
		Set<Parser<?>> parsers = new HashSet<>();
		for(int i = 0; i < 10; i++) {
			final String x = String.valueOf(i);
			parsers.add((arg) -> {
					if(new String(arg).contains(x)) 
						return Data.create("PARSED -> " + new String(arg)); 
					return null;
				}
			);
		}
		
		parser = ChainedParser.create(parsers);
	}
	
	@Test
	public void testParse() throws Exception {
		Data d = (Data) parser.parse("444".getBytes());
		System.out.println(d.toString());
	}

}
