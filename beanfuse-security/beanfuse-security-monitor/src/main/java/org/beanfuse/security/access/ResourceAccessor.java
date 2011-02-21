package org.beanfuse.security.access;

import javax.servlet.http.HttpServletRequest;

public interface ResourceAccessor {

	public AccessLog beginAccess(HttpServletRequest request, long time);

	public void endAccess(AccessLog log, long time);

	public void start();

	public void finish();
}
