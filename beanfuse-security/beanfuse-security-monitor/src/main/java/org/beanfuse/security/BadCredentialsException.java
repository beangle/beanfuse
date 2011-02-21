package org.beanfuse.security;

public class BadCredentialsException extends AuthenticationException {

	private static final long serialVersionUID = 3165517475635935263L;

	public BadCredentialsException() {
		super(Authentication.ERROR_PASSWORD);
	}

	public BadCredentialsException(String message) {
		super(message);
	}
}
