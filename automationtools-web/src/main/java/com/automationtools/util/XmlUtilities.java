package com.automationtools.util;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

/**
 * 
 * @author Melvin Garcia
 * @since v.3.0
 */
public class XmlUtilities {
	
	private XmlUtilities() {
	}
	
	public enum FormatOptions implements Options {
		
		PRETTY_PRINT {
			@Override
			public XmlOptions toXmlOptions() {
				XmlOptions opt = new XmlOptions();
				opt.setSavePrettyPrint();
				return opt;
			}
		},
		
		PRETTY_PRINT_NO_COMMENTS {
			@Override
			public XmlOptions toXmlOptions() {
				XmlOptions opt = new XmlOptions();
				opt.setSavePrettyPrint();
				opt.setLoadStripComments();
				return opt;
			}
		},
		
		;
	}
	
	public static String formatXml(String xml, Options formatOptions) throws XmlException {
		return formatXml(xml, formatOptions.toXmlOptions());
	}
	
	public static String formatXml(String xml, XmlOptions formatOptions) throws XmlException {
		try {
			if(xml == null || xml.isEmpty())
				return null;
			
			XmlObject xmlobj = XmlObject.Factory.parse(xml, formatOptions);
			return xmlobj.xmlText(formatOptions);
		} catch (XmlException e) {
			throw new XmlException("Error occurred while parsing Xml String: \n" + xml , e);
		}
	}
	
	interface Options {
		XmlOptions toXmlOptions();
	}

}
