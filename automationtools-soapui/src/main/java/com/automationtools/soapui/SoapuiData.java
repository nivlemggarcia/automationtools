package com.automationtools.soapui;

import static org.springframework.util.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import org.springframework.util.StreamUtils;
import com.automationtools.core.Data;
import com.automationtools.exception.ParsingFailedException;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A type of {@code Data} the represents the SoapUI test suite.
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public class SoapuiData extends Data {
	
	/**
	 * The soapui test suite document.
	 */
	private WsdlProject document;

	/**
	 * Constructor that accepts the <strong>raw content</strong>
	 * of a soapui test and build from it the soapui test suite
	 * document.
	 * 
	 * @param content
	 * 			The raw content of soapui test.
	 */
	public SoapuiData(byte[] content) {
		super(content);
	}
	
	/**
	 * Constructor that accepts a source of <strong>raw content</strong>.
	 */
	public SoapuiData(String content) {
		this(content.getBytes(), Charset.defaultCharset());
	}
	
	/**
	 * Constructor that accepts a source of <strong>raw content</strong> 
	 * and the {@linkplain java.nio.charset.Charset charset} to be used 
	 * to decode the {@code bytes}.
	 */
	public SoapuiData(byte[] content, Charset charset) {
		super(content, charset);
	}
	
	/**
	 * Factory method for creating a new instance of {@code SoapuiData}
	 * from a byte array.
	 * 
	 * <p>
	 * This is the same as: <pre>new SoapuiData(byte[]);</pre>
	 * </p>
	 */
	public static SoapuiData create(byte[] content) {
		return new SoapuiData(content);
	}
	
	/**
	 * Factory method for creating a new instance of {@code SoapuiData}
	 * from a byte array.
	 * 
	 * <p>
	 * This is the same as: <pre>new SoapuiData(byte[], Charset);</pre>
	 * </p>
	 */
	public static SoapuiData create(byte[] content, Charset charset) {
		return new SoapuiData(content, charset);
	}
	
	/**
	 * Factory method for creating a new instance of {@code SoapuiData}
	 * from a String.
	 * 
	 * <p>
	 * This is the same as: <pre>new SoapuiData(String);</pre>
	 * </p>
	 */
	public static SoapuiData create(String content) {
		return new SoapuiData(content);
	}
	
	/**
	 * Factory method for creating a new instance of {@code SoapuiData}
	 * from an {@linkplain InputStream}.
	 */
	public static SoapuiData create(InputStream stream) throws IOException {
		return create(StreamUtils.copyToByteArray(stream));
	}
	
	/**
	 * Returns the soapui test suite document.
	 */
	@JsonIgnore
	public WsdlProject getDocument() {
		if(document == null)
			/* Parse */
			document = toWsdlProject(new ByteArrayInputStream(getContent()));
		
		return document;
	}
	
	/**
	 * Returns the soapui test suite document name.
	 */
	public String getName() {
		return getDocument().getName();
	}
	
	/**
	 * Factory method that creates {@code WsdlProject} from the 
	 * supplied {@code InputStream}.
	 */
	protected static CustomWsdlProject toWsdlProject(InputStream in) {
		notNull(in, "InputStream cannot be null");
		try {
			return new CustomWsdlProject(in);
		} catch (Exception e) {
			throw new ParsingFailedException(e.getMessage(), e);
		}
	}
	
	/**
	 * Create a deep-copy of this {@code SoapuiData}.
	 */
	@Override
	public SoapuiData copy() {
		Data d = super.copy();
		return SoapuiData.create(d.getContent(), d.getCharset());
	}

}
