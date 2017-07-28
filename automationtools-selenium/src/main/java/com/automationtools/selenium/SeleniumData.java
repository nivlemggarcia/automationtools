package com.automationtools.selenium;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import javax.xml.namespace.QName;
import org.apache.http.client.utils.URIUtils;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.springframework.util.StreamUtils;
import com.automationtools.core.Data;
import com.automationtools.exception.ParsingFailedException;

/**
 * A type of {@code Data} the represents the Selenium TestCase.
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class SeleniumData extends Data {
	
	/**
	 * Selenium TestCase document default namespace.
	 */
	private static final String _NS = "declare default element namespace 'http://www.w3.org/1999/xhtml'; ";
	
	/**
	 * XPath for Selenium TestCase base url.
	 */
	private static final String _LINK_QUERY_STRING = "$this/html/head/link";
	
	/**
	 * XPath for Selenium TestCase document name.
	 */
	private static final String _TITLE_QUERY_STRING = "$this/html/head/title";
	
	/**
	 * XPath for Selenium TestCase selenese 
	 */
	private static final String _SELENESE_QUERY_STRING = "$this/html/body/table/tbody/tr/td";
	
	/**
	 * Selenium TestCase document
	 */
	private XmlObject document;
	
	/**
	 * Constructor that accepts a source of <strong>raw content</strong>.
	 */
	public SeleniumData(byte[] content) {
		this(content, Charset.defaultCharset());
	}
	
	/**
	 * Constructor that accepts a source of <strong>raw content</strong>.
	 */
	public SeleniumData(String content) {
		this(content.getBytes(), Charset.defaultCharset());
	}
	
	/**
	 * Constructor that accepts a source of <strong>raw content</strong> 
	 * and the {@linkplain java.nio.charset.Charset charset} to be used 
	 * to decode the {@code bytes}.
	 */
	public SeleniumData(byte[] content, Charset charset) {
		super(content, charset);
	}
	
	/**
	 * Factory method for creating a new instance of {@code Data}
	 * from a byte array.
	 * 
	 * <p>
	 * This is the same as: <pre>new SeleniumData(byte[]);</pre>
	 * </p>
	 */
	public static SeleniumData create(byte[] content) {
		return new SeleniumData(content, Charset.defaultCharset());
	}
	
	/**
	 * Factory method for creating a new instance of {@code Data}
	 * from a byte array and the {@linkplain java.nio.charset.Charset charset} 
	 * to be used to decode the {@code bytes}.
	 * 
	 * <p>
	 * This is the same as: <pre>new SeleniumData(byte[]);</pre>
	 * </p>
	 */
	public static SeleniumData create(byte[] content, Charset charset) {
		return new SeleniumData(content, charset);
	}
	
	/**
	 * Factory method for creating a new instance of {@code Data}
	 * from a String.
	 * 
	 * <p>
	 * This is the same as: <pre>new SeleniumData(String);</pre>
	 * </p>
	 */
	public static SeleniumData create(String content) {
		return new SeleniumData(content);
	}
	
	/**
	 * Factory method for creating a new instance of {@code SeleniumData}
	 * from an {@linkplain InputStream}.
	 */
	public static SeleniumData create(InputStream stream) throws IOException {
		return create(StreamUtils.copyToByteArray(stream));
	}
	
	/**
	 * Returns the selenium test suite document.
	 */
	public XmlObject getDocument() {
		try {
			if(document == null)
				document = XmlObject.Factory.parse(new ByteArrayInputStream(getContent()));
		} catch (Exception e) {
			throw new ParsingFailedException(e.getMessage(), e);
		}
			
		return document;
	}
	
	/**
	 * Returns the selenium test suite document name.
	 */
	public String getName() {
		XmlObject[] result = xpath(_NS + _TITLE_QUERY_STRING);
		if(result.length > 0) 
			return ((SimpleValue) result[0]).getStringValue();
		else 
			return null;
	}
	
	/**
	 * Returns the selenium test suite base url as {@code String}.
	 */
	public String getBaseUrlValue() throws Exception {
		String baseUrl = getBaseUrl().getStringValue();
		if(baseUrl == null)
			return null;
		
		URI uri = new URI(baseUrl);
		return URIUtils.extractHost(uri).toString();
	}
	
	/**
	 * Returns the selenium test suite base url 
	 * as {@code SimpleValue} object.
	 */
	public SimpleValue getBaseUrl() throws Exception {
		XmlObject[] result = xpath(_NS + _LINK_QUERY_STRING);
		if(result.length > 0) {
			return (SimpleValue) result[0].selectAttribute(new QName("href"));
		} else
			return null;
	}
	
	/**
	 * Returns the {@code XmlObject}s based on 
	 * the path expression specified.
	 * 
	 * @param path	The path expression
	 */
	public XmlObject[] xpath(String path) {
		return getDocument().selectPath(path);
	}
	
}
