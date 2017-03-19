package com.firefly.web.framework;

public class ActionContextContainer {
	private static final ThreadLocal<ActionContext> threadLocal = new ThreadLocal<ActionContext>();
	
	public static void setContext(ActionContext context) {
		threadLocal.set(context);
	}
	
	public static ActionContext getActionContext() {
		return threadLocal.get();
	}
	
	public static void destory() {
		threadLocal.remove();
	}
}
