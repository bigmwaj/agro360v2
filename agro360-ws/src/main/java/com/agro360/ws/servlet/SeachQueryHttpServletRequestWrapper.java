package com.agro360.ws.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class SeachQueryHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private static final String CHARSET = "UTF-8";
	
	private static final String CONTENT_TYPE = "application/json";
	
	private boolean loaded;

	public SeachQueryHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (loaded) {
			throw new RuntimeException("Déjà chargé!");
		}

		var query = getRequest().getParameter("q");
		var decodedBytes = Base64.getDecoder().decode(query);
		var decodedString = new String(decodedBytes);
		
		try (var body = new BodyInputStream(decodedString.getBytes(getCharacterEncoding()))) {
			loaded = true;
			return body;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	@Override
	public String getCharacterEncoding() {
		return CHARSET;
	}
	
	@Override
	public String getContentType() {
		return CONTENT_TYPE;
	}

	/**
	 * The exact copy of 
	 * @see org.springframework.web.servlet.function.DefaultServerRequestBuilder$BodyInputStream
	 */
	private static class BodyInputStream extends ServletInputStream {

		private final InputStream delegate;

		public BodyInputStream(byte[] body) {
			this.delegate = new ByteArrayInputStream(body);
		}

		@Override
		public boolean isFinished() {
			return false;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void setReadListener(ReadListener readListener) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int read() throws IOException {
			return this.delegate.read();
		}

		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			return this.delegate.read(b, off, len);
		}

		@Override
		public int read(byte[] b) throws IOException {
			return this.delegate.read(b);
		}

		@Override
		public long skip(long n) throws IOException {
			return this.delegate.skip(n);
		}

		@Override
		public int available() throws IOException {
			return this.delegate.available();
		}

		@Override
		public void close() throws IOException {
			this.delegate.close();
		}

		@Override
		public synchronized void mark(int readlimit) {
			this.delegate.mark(readlimit);
		}

		@Override
		public synchronized void reset() throws IOException {
			this.delegate.reset();
		}

		@Override
		public boolean markSupported() {
			return this.delegate.markSupported();
		}
	}
}
