package org.beanfuse.security.providers.rememberme;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.security.Authentication;
import org.beanfuse.utils.web.CookieUtils;

public class TokenBasedRememberMeServiceImpl implements RememberMeService {

	public RememberMeAuthentication autoLogin(HttpServletRequest httpRequest) {
		String username = CookieUtils.getCookieValue(httpRequest, Authentication.NAME);
		if (StringUtils.isNotEmpty(username)) {
			String password = CookieUtils.getCookieValue(httpRequest, Authentication.PASSWORD);
			if (StringUtils.isNotEmpty(password)) {
				return new RememberMeAuthentication(username, password);
			}
		}
		return null;
	}
}
