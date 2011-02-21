package org.beanfuse.security.providers.sso;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class SsoDefaultAuthenticationProvider extends  AbstractSsoAuthenticationProvider {

	protected String doGetUserLoginName(HttpServletRequest request) {
		String userName = null;
		Principal p = request.getUserPrincipal();
		if (null != p) {
			userName = p.getName();
		}
		if (StringUtils.isEmpty(userName)) {
			userName = request.getRemoteUser();
		}
		return userName;
	}
}
