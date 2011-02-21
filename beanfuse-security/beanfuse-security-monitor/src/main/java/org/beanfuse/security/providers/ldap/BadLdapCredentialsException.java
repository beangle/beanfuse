package org.beanfuse.security.providers.ldap;

import org.beanfuse.security.AuthenticationBreakException;
import org.beanfuse.security.BadCredentialsException;

public class BadLdapCredentialsException extends BadCredentialsException implements
		AuthenticationBreakException {
	private static final long serialVersionUID = 7505204644005447606L;

	public BadLdapCredentialsException() {
		super();
	}

	public BadLdapCredentialsException(String message) {
		super(message);
	}

}
