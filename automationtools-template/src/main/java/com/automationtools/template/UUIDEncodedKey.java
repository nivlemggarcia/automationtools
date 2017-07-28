package com.automationtools.template;

import static com.automationtools.util.CreateUuid.*;
import java.io.Serializable;

/**
 * Base implementation of {@code Key} which utilize 
 * {@linkplain java.util.UUID#nameUUIDFromBytes(byte[]) UUID algorithm} 
 * for encoding the {@linkplain Key#getRaw() raw} identifier. 
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 */
public abstract class UUIDEncodedKey extends Key implements Serializable {

	/** Generated serialVersionUID */
	private static final long serialVersionUID = 3604778332329584725L;
	
	/**
	 * {@inheritDoc}
	 */
	public String encoded() {
		return encode(raw());
	}
	
	/**
	 * Returns a UUID-based encoded string that is derived
	 * from the specified string argument.
	 */
	protected static String encode(String raw) {
		return fromBytes(raw.getBytes()).toString();
	}
	
}
