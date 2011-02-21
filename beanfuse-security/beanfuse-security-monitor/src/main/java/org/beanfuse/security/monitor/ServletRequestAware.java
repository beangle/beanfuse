package org.beanfuse.security.monitor;

import javax.servlet.http.HttpServletRequest;

public interface ServletRequestAware {

	public HttpServletRequest getRequest();

	public void setRequest(HttpServletRequest request);

}
