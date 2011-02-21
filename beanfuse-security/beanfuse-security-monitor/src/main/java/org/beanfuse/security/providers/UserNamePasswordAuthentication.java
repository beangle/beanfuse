package org.beanfuse.security.providers;

import javax.servlet.http.HttpServletRequest;

import org.beanfuse.security.Authentication;
import org.beanfuse.security.UserDetails;
import org.beanfuse.security.monitor.ServletRequestAware;

public class UserNamePasswordAuthentication implements Authentication, ServletRequestAware {

	private String userName;

	private String password;

	private UserDetails details;

	private HttpServletRequest request;

	public UserNamePasswordAuthentication() {
		super();
	}

	public UserNamePasswordAuthentication(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public Object getCredentials() {
		return password;
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
