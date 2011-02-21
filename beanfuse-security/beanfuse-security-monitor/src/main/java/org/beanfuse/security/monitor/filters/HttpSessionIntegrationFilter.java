package org.beanfuse.security.monitor.filters;

import javax.servlet.http.HttpSession;

import org.beanfuse.security.Authentication;

public interface HttpSessionIntegrationFilter {

	public void register(HttpSession session, Authentication auth);

	public void clear(HttpSession session);
}
