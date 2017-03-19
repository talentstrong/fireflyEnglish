package com.firefly.web.framework.exception;

public class PageNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6475547729904543859L;

	public PageNotFoundException() {
		super();
	}

	public PageNotFoundException(String message) {
		super(message);
	}

	public PageNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
