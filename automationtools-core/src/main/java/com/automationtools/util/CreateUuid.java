package com.automationtools.util;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class CreateUuid {
	
	private CreateUuid() {
	}
	
	/**
	 * {@code Lock} used when creating {@linkplain UUID}.
	 */
	private static final ReentrantLock lock = new ReentrantLock();
	
	/**
	 * The buffer used for {@code Long} to byte array conversion.
	 */
	private static final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);    
	
	/**
	 * Timestamp holder used for time-based UUID.
	 */
	private static long lastTime;
	
	public static UUID fromBytes(byte[] bytes) {
		return UUID.nameUUIDFromBytes(bytes);
	}
	
	public static UUID fromSysdate() {
		return fromTimestamp(System.currentTimeMillis());
	}
	
	public static UUID fromDate(Date arg) {
		return fromTimestamp(arg.getTime());
	}
	
	public static UUID fromTimestamp(Long arg) {
		/* UTC time in Gregorian calendar (UUID format). */
		long timeMillis = (arg * 10000) + 0x01B21DD213814000L;
		
		lock.lock();
		try {
			if(timeMillis > lastTime) {
				lastTime = timeMillis;
			} else {
				timeMillis = ++lastTime;
			}
		} finally {
			lock.unlock();
		}
		
		UUID id = UUID.nameUUIDFromBytes(buffer.putLong(timeMillis).array());
		buffer.clear();
		return id;
	}
	
	public static void main(String[] args) {
		final Long timestamp = System.currentTimeMillis();
		final UUID uuid = CreateUuid.fromTimestamp(timestamp);
		for(int i = 0; i < 10; i++)
			System.out.println(uuid.toString());
	}
	
}
