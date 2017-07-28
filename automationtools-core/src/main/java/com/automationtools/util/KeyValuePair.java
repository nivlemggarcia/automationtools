package com.automationtools.util;

/**
 * 
 * @author 	Melvin Garcia
 *
 * @param <K>
 * @param <V>
 */
public class KeyValuePair<K, V> {
	
	private K key;
	
	private V value;
	
	public KeyValuePair() {
	}
	
	public KeyValuePair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;

		if(!this.getClass().isInstance(obj))
			return false;
		
		KeyValuePair<?, ?> other = (KeyValuePair<?, ?>) obj;
		return this.key.equals(other.key) && this.value.equals(other.value);
	}
	
}
