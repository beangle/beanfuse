package org.beanfuse.security.providers.rememberme;

import javax.servlet.http.HttpServletRequest;

public interface RememberMeService {

	RememberMeAuthentication autoLogin(HttpServletRequest httpRequest);

}
