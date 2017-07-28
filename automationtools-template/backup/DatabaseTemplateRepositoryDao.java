package com.automationtools.template.dao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.UUID;
import com.automationtools.core.Data;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public interface DatabaseTemplateRepositoryDao {
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract Collection<Record> getAll() throws Exception;
	
	/**
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public int delete(Record record) throws Exception;
	
	/**
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public int saveOrUpdate(Record record) throws Exception;

	/**
	 * 
	 */
	public class Record {
		
		private String name;
		
		private byte[] content;
		
		/**
		 * 
		 */
		public String getId() {
			byte[] b = getName().getBytes(StandardCharsets.UTF_8);
			return UUID.nameUUIDFromBytes(b).toString().replaceAll("\\-", "");
		}
		
		public byte[] getContent() {
			return content;
		}
		
		public String getName() {
			return name;
		}
		
		public void setContent(byte[] content) {
			this.content = content;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		/**
		 * 
		 * @return
		 * @throws IOException
		 */
		public Data toRawData() throws IOException {
			return Data.create(content);
		}
		
	}
}
