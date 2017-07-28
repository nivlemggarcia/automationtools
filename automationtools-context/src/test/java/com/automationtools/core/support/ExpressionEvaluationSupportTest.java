package com.automationtools.core.support;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@ImportResource("classpath:expressionevaluationsupporttest-config.xml")
@ContextConfiguration(classes = {ExpressionEvaluationSupportTest.class})
public class ExpressionEvaluationSupportTest {
	
	@Autowired
	private ExpressionEvaluationSupport evaluator;
	
	@Test
	public void testEvaluateLiteralString() {
		String result = evaluator.evaluate("'Literal String'");
		System.out.println(result);
		
	}
	
	@Test
	public void testEvaluateDateExpression() {
		String result = evaluator.evaluate("'${#SYSDATE.format('MM/dd/yyyy HH:mm:ss.s').evaluate()}'");
		System.out.println(result);
		
	}
	
	@Test
	public void testEvaluateLiteralAndDateExpression() {
		String result = evaluator.evaluate("'Date now is: ${#SYSDATE.format('MM/dd/yyyy HH:mm:ss.s').evaluate()}'");
		System.out.println(result);
		
	}

}
