package com.firefly.cache;

import java.io.Serializable;

public class EmptyData implements Serializable {
	private static final long serialVersionUID = 357739723786581960L;
	private final static EmptyData instance;
	static {
		instance = new EmptyData();
	}

	public static EmptyData getInstance() {
		return instance;
	}
}
