package com.firefly.cache.memcache.exception;

public class MemcacheException extends RuntimeException {

	private static final long serialVersionUID = -7991432535677142169L;

	public MemcacheException(String message) {
		super(message);
	}

	public MemcacheException(String message, Throwable cause) {
		super(message, cause);
	}

}
