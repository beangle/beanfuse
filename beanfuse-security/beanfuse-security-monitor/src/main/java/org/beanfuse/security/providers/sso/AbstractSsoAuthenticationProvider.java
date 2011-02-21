package org.beanfuse.security.providers.sso;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.security.Authentication;
import org.beanfuse.security.AuthenticationException;
import org.beanfuse.security.providers.AbstractAuthenticationProvider;

public abstract class AbstractSsoAuthenticationProvider extends AbstractAuthenticationProvider {

	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		logger.debug("Authentication using {}", getClass());
		SsoAuthentication ssoau = (SsoAuthentication) auth;
		String userName = doGetUserLoginName(ssoau.getRequest());
		if (StringUtils.isEmpty(userName)) {
			throw new AuthenticationException("sso user not found!");
		} else {
			auth.setPrincipal(userName);
			attachToUser(auth);
			return auth;
		}
	}

	abstract protected String doGetUserLoginName(HttpServletRequest request);

	public boolean supports(Class authTokenType) {
		return (SsoAuthentication.class.isAssignableFrom(authTokenType));
	}

	public String toString() {
		return getClass().getName();
	}
}
