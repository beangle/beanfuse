package org.beanfuse.security.providers.ldap;

import org.beanfuse.security.Authentication;
import org.beanfuse.security.AuthenticationException;
import org.beanfuse.security.providers.AbstractAuthenticationProvider;
import org.beanfuse.security.providers.UserNamePasswordAuthentication;

public abstract class AbstractLdapAuthenticationProvider extends AbstractAuthenticationProvider {

	final public Authentication authenticate(Authentication auth) throws AuthenticationException {
		logger.debug("Authentication using {}", getClass());
		if (doVerify(auth)) {
			attachToUser(auth);
			return auth;
		} else {
			throw new BadLdapCredentialsException();
		}
	}

	abstract protected boolean doVerify(Authentication auth);

	public boolean supports(Class authTokenType) {
		return (UserNamePasswordAuthentication.class.isAssignableFrom(authTokenType));
	}

	public String toString() {
		return getClass().getName();
	}
}
