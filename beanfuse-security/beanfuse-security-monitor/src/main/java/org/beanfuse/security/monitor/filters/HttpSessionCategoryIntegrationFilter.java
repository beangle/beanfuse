package org.beanfuse.security.monitor.filters;

import javax.servlet.http.HttpSession;

import org.beanfuse.security.Authentication;
import org.beanfuse.security.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpSessionCategoryIntegrationFilter implements HttpSessionIntegrationFilter {
	private final static Logger logger = LoggerFactory
			.getLogger(HttpSessionCategoryIntegrationFilter.class);

	public void register(HttpSession session, Authentication auth) {
		logger.debug(
				"add session security attribute USERID,LOGINNAME,USERNAME,USER_CATEGORYID for {}",
				auth.getPrincipal());
		UserDetails details = (UserDetails) auth.getDetails();
		session.setAttribute(Authentication.USERID, details.getUserid());
		session.setAttribute(Authentication.LOGINNAME, auth.getPrincipal());
		session.setAttribute(Authentication.FULLNAME, details.getFullname());
		session.setAttribute(Authentication.USER_CATEGORYID, details.getCategory().getId());
	}

	public void clear(HttpSession session) {
		logger.debug("clean session security attribute USERID,LOGINNAME,USERNAME,USER_CATEGORYID");
		session.removeAttribute(Authentication.USERID);
		session.removeAttribute(Authentication.LOGINNAME);
		session.removeAttribute(Authentication.FULLNAME);
		session.removeAttribute(Authentication.USER_CATEGORYID);
	}
}
