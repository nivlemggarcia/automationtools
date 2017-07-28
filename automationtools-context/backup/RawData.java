package com.automationtools.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.springframework.util.StreamUtils;

/**
 * A generic representation of a type of data.
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 */
public class RawData {
	
	/**
	 * The raw content.
	 */
	private byte[] content;
	
	/**
	 * Holds the data that describes the content
	 */
	private Map<String, String> metadata;
	
	/**
	 * Default constructor. Returns an empty instance of {@code RawData}.
	 */
	public RawData() {
		super();
		this.metadata = new HashMap<>();
	}
	
	/**
	 * Constructor that accepts a source of <strong>raw content</strong>.
	 */
	public RawData(byte[] content) {
		this();
		this.content = content;
	}
	
	/**
	 * Constructor that reads the supplied {@linkplain InputStream input stream}
	 * as the source of <strong>raw content</strong>.
	 */
	public RawData(InputStream stream) throws IOException {
		this(StreamUtils.copyToByteArray(stream));
	}
	
	/**
	 * Factory method for creating a new empty instance of {@code RawData}.
	 * 
	 * <p>
	 * Using this is similar to: <pre>RawData data = new RawData();</pre>
	 * </p>
	 */
	public static RawData create() {
		return new RawData();
	}
	
	/**
	 * Factory method for creating a new instance of {@code RawData}
	 * using a byte array.
	 * 
	 * <p>
	 * Using this is similar to: <pre>RawData data = new RawData(byte[]);</pre>
	 * </p>
	 */
	public static RawData create(byte[] content) {
		return new RawData(content);
	}
	
	/**
	 * Factory method for creating a new instance of {@code RawData}
	 * using an {@linkplain InputStream}.
	 * 
	 * <p>
	 * Using this is similar to: <pre>RawData data = new RawData(InputStream);</pre>
	 * </p>
	 */
	public static RawData create(InputStream stream) throws IOException {
		return new RawData(stream);
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
	public void setContent(byte[] content) {
		this.content = content;
	}

	/**
	 * Returns the data that describes the content.
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	/**
	 * Sets the data that describes the content.
	 */
	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}
	
	/**
	 * Sets the data that describes the content.
	 */
	public void setMetadata(Properties metadata) {
		if(metadata == null) {
			this.metadata = null;
			return;
		}

		this.metadata = new HashMap<>();
		if(metadata != null)
			for(String key : metadata.stringPropertyNames())
				this.metadata.put(key, metadata.getProperty(key));
	}
	
	public RawData copy() {
		byte[] newContent = Arrays.copyOf(content, content.length);
		RawData copy = RawData.create(newContent);
		if(metadata == null || metadata.isEmpty())
			return copy;
		
		copy.setMetadata(new HashMap<>(metadata));
		return copy;
	}
	
}
