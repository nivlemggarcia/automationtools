package com.automationtools.report;

import static org.springframework.util.Assert.*;
import java.util.Properties;
import java.util.function.Supplier;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import com.automationtools.context.CheckedSupplier;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
public class ImmutableMail extends Mail {
	
	/**
	 * 
	 */
	private Session session;

	/**
	 * 
	 */
	private Address from;

	/**
	 * 
	 */
	private Address[] to;

	/**
	 * 
	 */
	private Address[] cc;

	/**
	 * 
	 */
	private Address[] bcc;

	/**
	 * 
	 */
	private String subject;

	/**
	 * 
	 */
	private Multipart content;

	/**
	 * 
	 */
	private Properties properties;

	/**
	 * 
	 */
	private Authenticator authenticator;

	/**
	 * 
	 * @param builder
	 */
	private ImmutableMail(Builder builder) throws Exception {
		this.from = builder.from.get();
		this.to = builder.to;
		this.cc = builder.cc;
		this.bcc = builder.bcc;
		this.subject = builder.subject;
		this.content = builder.content;
		this.properties = builder.properties;
		this.authenticator = builder.authenticator;
		
	}

	@Override
	public Session getSession() {
		if(session == null) {
			/* Create session if accessed for the first time */
			if(authenticator != null)
				session = Session.getInstance(properties, authenticator);
			else
				session = Session.getInstance(properties);
		}
		
		return session;
	}

	@Override
	public Address getFrom() {
		return from;
	}

	@Override
	public Address[] getTo() {
		return to;
	}

	@Override
	public Address[] getCc() {
		return cc;
	}

	@Override
	public Address[] getBcc() {
		return bcc;
	}

	@Override
	public String getSubject() {
		return subject;
	}

	@Override
	public Multipart getContent() {
		return content;
	}

	public static Builder builder() {
		return new Builder();
	}
	
	/**
	 * 
	 */
	public static class Builder {
		
		private CheckedSupplier<Address, AddressException> from;
		
		private CheckedSupplier<Address[], AddressException> to;
		
		private CheckedSupplier<Address[], AddressException> cc;
		
		private CheckedSupplier<Address[], AddressException> bcc;
		
		private String subject;
		
		private MailContent content;
		
		private Properties properties;
		
		private Authenticator authenticator;
		
		private MailAttachment[] attachment; 
		
		private Builder() {
		}

		public Builder from(CheckedSupplier<Address, AddressException> from) {
			this.from = from;
			return this;
		}
		
		public Builder from(Address from) {
			this.from = from;
			return this;
		}

		public Builder to(CheckedSupplier<Address[], AddressException> to) {
			this.to = to;
			return this;
		}
		
		public Builder to(Address[] to) {
			this.to = to;
			return this;
		}

		public Builder cc(CheckedSupplier<Address[], AddressException> cc) {
			this.cc = cc;
			return this;
		}
		
		public Builder cc(Address[] cc) {
			this.cc = cc;
			return this;
		}

		public Builder bcc(CheckedSupplier<Address[], AddressException> bcc) {
			this.bcc = bcc;
			return this;
		}
		
		public Builder bcc(Address[] bcc) {
			this.bcc = bcc;
			return this;
		}

		public Builder subject(Supplier<String> subject) {
			this.subject = subject.get();
			return this;
		}
		
		public Builder subject(String subject) {
			this.subject = subject;
			return this;
		}

		public Builder content(Supplier<MailContent> content) {
			this.content = content.get();
			return this;
		}
		
		public Builder content(MailContent content) {
			this.content = content;
			return this;
		}
		
		public Builder properties(Supplier<Properties> properties) {
			this.properties = properties.get();
			return this;
		}
		
		public Builder properties(Properties properties) {
			this.properties = properties;
			return this;
		}

		public Builder attachment(Supplier<MailAttachment[]> attachment) {
			this.attachment = attachment.get();
			return this;
		}
		
		public Builder attachment(MailAttachment[] attachment) {
			this.attachment = attachment;
			return this;
		}
		
		public Builder authenticator(Supplier<Authenticator> authenticator) {
			this.authenticator = authenticator.get();
			return this;
		}
		
		public Builder authenticator(Authenticator authenticator) {
			this.authenticator = authenticator;
			return this;
		}
		
		/**
		 * 
		 * @return
		 * @throws Exception 
		 */
		public Mail build() throws Exception {
//			notNull(content, "Mail content cannot be null");
//			notNull(from, "Sender cannot be null");
//			notNull(to, "Recipient cannot be null");
//			notNull(subject, "Mail subject cannot be null");
//			notNull(properties, "Mail properties cannot be null");
			return new ImmutableMail(this);
		}
		
	}
	
}
