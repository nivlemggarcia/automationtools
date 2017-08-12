package com.automationtools.web.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestStep;

/**
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class SoapuiTestCase implements Serializable {
	
	/** Generated serialVersionUID */
	private static final long serialVersionUID = -4235797960381763027L;

	private TestCase xml;
	
	private String name;
	
	private Collection<SoapuiTestStep> testSteps;
	
	public SoapuiTestCase() {
		testSteps = new ArrayList<SoapuiTestStep>();
	}
	
	public SoapuiTestCase(TestCase xml) {
		this();
		this.xml = xml;
		this.name = xml.getName();
		for(TestStep ts : xml.getTestStepList())
			testSteps.add(new SoapuiTestStep(ts));
	}
	
	public TestCase getXml() {
		return xml;
	}
	
	public Collection<SoapuiTestStep> getTestSteps() {
		return testSteps;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
