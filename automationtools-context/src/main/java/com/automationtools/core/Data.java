package com.automationtools.core;

import static org.springframework.util.Assert.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
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
	 * Used to decode the content.
	 */
	private Charset charset = Charset.defaultCharset();
	
	/**
	 * Constructor that accepts a source of <strong>raw content</strong>.
	 */
	public Data(byte[] content) {
		this(content, Charset.defaultCharset());
	}
	
	/**
	 * Constructor that accepts a source of <strong>raw content</strong>.
	 */
	public Data(String content) {
		this(content.getBytes(), Charset.defaultCharset());
	}
	
	/**
	 * Constructor that accepts a source of <strong>raw content</strong> 
	 * and the {@linkplain java.nio.charset.Charset charset} to be used 
	 * to decode the {@code bytes}.
	 */
	public Data(byte[] content, Charset charset) {
		setContent(content);
		setCharset(charset);
	}
	
	/**
	 * Factory method for creating a new instance of {@code Data}
	 * from a byte array.
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
	 * from a byte array.
	 * 
	 * <p>
	 * This is the same as: <pre>new Data(byte[]);</pre>
	 * </p>
	 */
	public static Data create(byte[] content, Charset charset) {
		return new Data(content, charset);
	}
	
	/**
	 * Factory method for creating a new instance of {@code Data}
	 * from a String.
	 * 
	 * <p>
	 * This is the same as: <pre>new Data(String);</pre>
	 * </p>
	 */
	public static Data create(String content) {
		return new Data(content);
	}
	
	/**
	 * Factory method for creating a new instance of {@code Data}
	 * from an {@linkplain InputStream}.
	 */
	public static Data create(InputStream stream) throws IOException {
		return create(StreamUtils.copyToByteArray(stream));
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
		notNull(content, "Raw content cannot be null");
		this.content = content;
	}
	
	/**
	 * Sets the reference that represent 
	 * the actual content of the file in a 
	 * form of {@code String}. 
	 */
	protected void setContent(String content) {
		notNull(content, "Raw content cannot be null");
		this.content = content.getBytes(charset);
	}
	
	/**
	 * Returns the {@linkplain java.nio.charset.Charset charset} 
	 * that is used for decoding the {@code bytes}.
	 */
	public Charset getCharset() {
		return charset;
	}
	
	/**
	 * Sets the {@linkplain java.nio.charset.Charset charset} 
	 * that will be used for decoding the {@code bytes}.
	 */
	protected void setCharset(Charset charset) {
		this.charset = charset;
	}
	
	/**
	 * Create a deep-copy of this {@code Data}.
	 */
	public Data copy() {
		byte[] newContent = Arrays.copyOf(content, content.length);
		return Data.create(newContent, charset);
	}
	
	/**
	 * Returns the {@code Class} of this {@code Data}.
	 */
	public Class<?> getType() {
		return this.getClass();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new String(content, charset);
	}
	
}
