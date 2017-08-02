package com.automationtools.soapui;

import java.io.ByteArrayInputStream;
import com.automationtools.exception.ParsingFailedException;
import com.automationtools.parser.Parser;
import com.eviware.soapui.impl.wsdl.WsdlProject;

/**
 * The class {@code SoapuiDataParser} translating 
 * any given SoapUI Tests in a form of a byte array in to a 
 * {@code SoapuiData}.
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public class SoapuiDataParser implements Parser<SoapuiData> {

	/**
	 * Translates the given byte array into {@code SoapuiData} type.
	 * 
	 * @param arg	The source.
	 * @return 		{@code SoapuiData} object.
	 */
	@Override
	public SoapuiData parse(byte[] arg) throws ParsingFailedException {
		try {
			/* Parse argument as WsdlProject. This will throw 
			 * an exception when parsing-related error occurred. */
			new WsdlProject().loadProjectFromInputStream(new ByteArrayInputStream(arg));
		} catch (Throwable t) {
			throw new ParsingFailedException(t.getMessage(), t);
		}
		
		/* At this point, it is safe to say that the argument 
		 * is a valid SoapUI project */
		return SoapuiData.create(arg);
	}

}
