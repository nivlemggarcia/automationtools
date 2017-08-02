package com.automationtools.template;

/**
 * Serves as a identifier of a {@linkplain Template}.
 * 
 * @author Melvin Garcia
 * @since 1.0.0
 */
public abstract class Key {
	
	/**
	 * Returns the <strong>encoded</strong> 
	 * version of the identifier.
	 */
	public abstract String encoded();
	
	/**
	 * Returns the <strong>raw</strong> identifier. 
	 */
	public String raw() {
		return "";
	}
	
	/**
	 * Factory method for creating temporary keys. 
	 * 
	 * @param encoded	Encoded String
	 * @return			{@code Key} instance.
	 */
	public static Key create(String encoded) {
		return new Key() {
			@Override
			public String encoded() {
				return encoded;
			}
		};
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return encoded().hashCode();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		
		if(!(obj instanceof Key))
			return false;
		
		Key other = (Key) obj;
		return other.encoded().equals(this.encoded());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return encoded();
	}
}
