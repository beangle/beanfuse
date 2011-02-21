package org.beanfuse.security.providers;

import org.beanfuse.security.Authentication;
import org.beanfuse.security.AuthenticationException;
import org.beanfuse.security.User;
import org.beanfuse.security.UserDetails;
import org.beanfuse.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAuthenticationProvider implements AuthenticationProvider {

	protected static Logger logger = LoggerFactory.getLogger(AbstractAuthenticationProvider.class);

	protected UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public User attachToUser(Authentication auth) {
		User user = userService.get((String) auth.getPrincipal());
		if (null == user) {
			throw new AuthenticationException(Authentication.ERROR_NOTEXIST);
		}
		if (!user.isEnabled()) {
			throw new AuthenticationException(Authentication.ERROR_UNACTIVE);
		}
		if (null == auth.getDetails()) {
			auth.setDetails(new UserDetails());
		}
		UserDetails details = (UserDetails) auth.getDetails();
		details.setUserid(user.getId());
		details.setFullname(user.getFullname());
		details.setCategory(user.getDefaultCategory());
		return user;
	}

}
