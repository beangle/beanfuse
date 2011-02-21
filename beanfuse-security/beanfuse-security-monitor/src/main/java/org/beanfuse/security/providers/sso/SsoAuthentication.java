package org.beanfuse.security.providers.sso;

import javax.servlet.http.HttpServletRequest;

import org.beanfuse.security.Authentication;
import org.beanfuse.security.UserDetails;
import org.beanfuse.security.monitor.ServletRequestAware;

public class SsoAuthentication implements Authentication, ServletRequestAware {

	private String userName;

	private UserDetails details;

	private HttpServletRequest request;

	public SsoAuthentication(HttpServletRequest request) {
		super();
		this.request = request;
	}

	public SsoAuthentication() {
		super();
	}

	public Object getCredentials() {
		return null;
	}

	public Object setPrincipal(Object principal) {
		return userName = (String) principal;
	}

	public Object getDetails() {
		return details;
	}

	public Object getPrincipal() {
		return userName;
	}

	public void setDetails(UserDetails details) {
		this.details = details;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

}
