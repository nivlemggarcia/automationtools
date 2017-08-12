package com.automationtools.web.view;

import static com.automationtools.util.XmlUtilities.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.xmlbeans.XmlException;
import com.eviware.soapui.model.testsuite.TestStep;

/**
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class SoapuiTestStep implements Serializable {
	
	/** Generated serialVersionUID */
	private static final long serialVersionUID = -3720818309190824646L;

	public static final String _NAMESPACE = "declare default element namespace 'http://eviware.com/soapui/config'; ";
	
	public static final String _XPATH_REQUEST = "Request";
	
	public static final String _XPATH_ENDPOINT = "Endpoint";
	
	public static final String _XPATH_AUTH_TYPE = "AuthType";
	
	private Map<String, String> projectPropertiesMap;
	
	private TestStep xml;
	
	private String name;
	
	private String endpoint;
	
	private String authType;
	
	public SoapuiTestStep() {
		projectPropertiesMap = new HashMap<String, String>();
	}
	
	public SoapuiTestStep(TestStep xml) {
		this();
		this.xml = xml;
		
		/* Parse XmlObject values */
		this.name = xml.getName();
		this.endpoint = xml.getPropertyValue(_XPATH_ENDPOINT);
		if(endpoint != null && !endpoint.isEmpty())
			projectPropertiesMap.put(_XPATH_ENDPOINT, this.endpoint);
		
		this.authType = xml.getPropertyValue(_XPATH_AUTH_TYPE);
		if(authType != null && !authType.isEmpty())
			projectPropertiesMap.put(_XPATH_AUTH_TYPE, this.authType);
	}
	
	public TestStep getXml() {
		return xml;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEndpoint() {
		return this.endpoint;
	}

	public String getRequest() {
		try {
			return formatXml(xml.getPropertyValue(_XPATH_REQUEST), FormatOptions.PRETTY_PRINT);
		} catch (XmlException e) {
			return xml.getPropertyValue(_XPATH_REQUEST);
		}
	}
	
	public void setRequest(String request) {
		/* Directly modify request xml from 
		 * the SoapUI TestStep document */
		this.xml.setPropertyValue(_XPATH_REQUEST, request);
	}
	
	public Map<String, String> getProjectPropertiesMap() {
		return projectPropertiesMap;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
