package com.automationtools.web.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestSuite;

/**
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class SoapuiTestSuite implements Serializable {
	
	/** Generated serialVersionUID */
	private static final long serialVersionUID = 7669360912366298739L;

	private TestSuite document;
	
	private String name;
	
	private Collection<SoapuiTestCase> testCases;
	
	public SoapuiTestSuite() {
		testCases = new ArrayList<SoapuiTestCase>();
	}
	
	public SoapuiTestSuite(TestSuite document) {
		this();
		this.document = document;
		this.name = document.getName();
		for(TestCase tc : document.getTestCaseList())
			testCases.add(new SoapuiTestCase(tc));
	}
	
	public TestSuite getDocument() {
		return document;
	}
	
	public String getName() {
		return name;
	}
	
	public Collection<SoapuiTestCase> getTestCases() {
		return testCases;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
