package com.firefly.cache.memcache.exception;

public class MemcacheInitException extends Exception {

	private static final long serialVersionUID = -6441576367275409444L;

	public MemcacheInitException(String message, Throwable cause) {
		super(message, cause);
	}

	public MemcacheInitException(String message) {
		super(message);
	}

}
