package com.automationtools.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

/**
 * A generic representation of a type of data.
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 */
public class Data {

	/**
	 * The raw content.
	 */
	private byte[] content;
	
	/**
	 * Constructor that accepts a source of <strong>raw content</strong>.
	 */
	public Data(byte[] content) {
		setContent(content);
	}
	
	/**
	 * Factory method for creating a new instance of {@code Data}
	 * using a byte array.
	 * 
	 * <p>
	 * This is the same as: <pre>new Data(byte[]);</pre>
	 * </p>
	 */
	public static Data create(byte[] content) {
		return new Data(content);
	}
	
	/**
	 * Factory method for creating a new instance of {@code Data}
	 * using an {@linkplain InputStream}.
	 */
	public static Data create(InputStream stream) throws IOException {
		return new Data(StreamUtils.copyToByteArray(stream));
	}
	
	/**
	 * Returns the actual content of the file.
	 */
	public byte[] getContent() {
		return content;
	}
	
	/**
	 * Sets the reference that represent 
	 * the actual content of the file. 
	 */
	protected void setContent(byte[] content) {
		Assert.notNull(content);
		this.content = content;
	}
	
	/**
	 * Create a deep-copy of this {@code Data}.
	 */
	public Data copy() {
		byte[] newContent = Arrays.copyOf(content, content.length);
		return Data.create(newContent);
	}
	
}
