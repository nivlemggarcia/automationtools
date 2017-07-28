package com.automationtools.soapui;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.automationtools.context.SingletonScoped;
import com.automationtools.core.TaskHandler;
import com.automationtools.exception.ExecutionFailedException;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestRunner;
import com.eviware.soapui.model.testsuite.TestStep;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.support.types.StringToObjectMap;

/**
 * A {@code Handler} implementation backed
 * by SoapUI engine to execute SoapUI Tests.
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
@Named
@SingletonScoped
public class SoapuiTaskHandler implements TaskHandler<SoapuiData, Collection<String>> {
	
	protected static final Logger log = LoggerFactory.getLogger(SoapuiTaskHandler.class);
	
	@Override
	public Collection<String> handle(SoapuiData data) throws Exception {
		boolean successful = true;
		log.info("Processing {} ...", data.getName());
		
		Collection<String> result = new ArrayList<>();
		for(TestSuite ts : data.getDocument().getTestSuiteList()) {
			for(TestCase tc : ts.getTestCaseList()) {
				log.info("Running TestCase [{}] ...", tc.getName());
				TestCaseRunner runner = executeTestCase(tc);
				/* Collect all results for further processing */
				result.addAll(handleResults(runner.getResults()));
				successful = successful && runner.getStatus().equals(TestRunner.Status.FINISHED);
				/* Compute TestCase logs */
				log.info("TestCase [{}] finished with status [{}] in {}millis.", 
						tc.getName(), runner.getStatus(), runner.getTimeTaken());
			}
		}
		
		if(successful) {
			log.info("{} Successful.", data.getName());
			return result;
		}
		
		log.error("{} Failed.", data.getName());
		throw new ExecutionFailedException("Some of the testcase have failed results.");
	}
	
	/**
	 * Execute {@code TestCase} by invoking the SoapUI engine's
	 * {@linkplain TestCaseRunner default runner}.
	 * 
	 * @param tc		{@link TestCase} to be executed
	 */
	protected TestCaseRunner executeTestCase(TestCase tc) {
		return tc.run(new StringToObjectMap(), false);
	}
	
	/**
	 * Further processing the {@code TestStepResult}s.
	 * 
	 * @param results	
	 * 			The {@code TestStepResult}s
	 */
	protected Collection<String> handleResults(Collection<TestStepResult> results) {
		Collection<String> r = new ArrayList<>();
		/* Compute each TestStep logs */
		for(TestStepResult result : results) {
			TestStep step = result.getTestStep();
			StringWriter writer = new StringWriter();
			result.writeTo(new PrintWriter(writer, true));
			r.add(writer.toString());
			
			StringBuilder sb = new StringBuilder();
			if(result.getMessages().length > 0) {
				for(String message : result.getMessages()) {
					if(sb.length() > 1)
						sb.append(" ");
					
					sb.append("[ " + message + " ]");
				}
			}
			
			log.info("TestStep [{}.{}] finished with status [{}] in {}millis. {}", 
					step.getTestCase().getName(), 
					step.getName(), result.getStatus(), 
					result.getTimeTaken(), sb.toString());
		}
		
		return r;
	}

}
