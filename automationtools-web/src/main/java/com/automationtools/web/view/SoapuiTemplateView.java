package com.automationtools.web.view;

import static org.springframework.util.Assert.notNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.automationtools.soapui.SoapuiData;
import com.automationtools.template.Template;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.model.testsuite.TestSuite;

/**
 * The class {@code SoapuiTemplateView} is the <strong><em>view representation</em></strong>
 * of a {@code Template} with {@code SoapuiData}. 
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public class SoapuiTemplateView extends TemplateView {
	
	/**
	 * {@code Data} that the wrapped {@code Template} holds.
	 */
	private SoapuiData data;
	
	/**
	 * Collection of {@code TestSuite}s from the soapui document. 
	 */
	private Collection<SoapuiTestSuite> testSuites;

	/**
	 * Creates a new instance of {@code SoapuiTemplateView} 
	 * which wraps the supplied {@code Template}.
	 */
	public SoapuiTemplateView(Template wrapped) {
		super(wrapped);
		data = (SoapuiData) wrapped.getData();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLabel() {
		return data.getName();
	}
	
	/**
	 * Returns a collection of {@code TestSuite}s from the soapui document. 
	 */
	public Collection<SoapuiTestSuite> getTestSuites() {
		if(testSuites == null)
			testSuites = toTestSuites(data.getDocument());
		
		return testSuites;
	}
	
	/**
	 * Helper method that produces a collection of 
	 * {@code TestSuite}s from the supplied soapui document.
	 */
	static Collection<SoapuiTestSuite> toTestSuites(WsdlProject document) {
		notNull(document, "WsdlProject document cannot be null");
		List<SoapuiTestSuite> testSuites = new ArrayList<>();
		for(TestSuite ts : document.getTestSuiteList())
			testSuites.add(new SoapuiTestSuite(ts));
		
		return testSuites;
	}

}
