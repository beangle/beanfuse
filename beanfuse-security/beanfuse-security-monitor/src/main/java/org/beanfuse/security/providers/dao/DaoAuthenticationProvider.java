package org.beanfuse.security.providers.dao;

import org.beanfuse.security.Authentication;
import org.beanfuse.security.AuthenticationException;
import org.beanfuse.security.BadCredentialsException;
import org.beanfuse.security.User;
import org.beanfuse.security.providers.AbstractAuthenticationProvider;
import org.beanfuse.security.providers.UserNamePasswordAuthentication;
import org.beanfuse.security.providers.encoding.PasswordEncoder;

public class DaoAuthenticationProvider extends AbstractAuthenticationProvider {

	private PasswordEncoder passwordEncoder;

	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		logger.debug("Authentication using {}", getClass());
		User user = this.attachToUser(auth);
		if (!passwordEncoder.isPasswordValid(user.getPassword(), (String) auth.getCredentials())) {
			throw new BadCredentialsException(Authentication.ERROR_PASSWORD);
		}
		return auth;
	}

	public boolean supports(Class authTokenType) {
		return (UserNamePasswordAuthentication.class.isAssignableFrom(authTokenType));
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public String toString() {
		return getClass().getName();
	}

}
