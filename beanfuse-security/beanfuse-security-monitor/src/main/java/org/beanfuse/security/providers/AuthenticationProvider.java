package org.beanfuse.security.providers;

import org.beanfuse.security.Authentication;
import org.beanfuse.security.AuthenticationException;
import org.beanfuse.security.User;
import org.beanfuse.security.service.UserService;

/**
 * 被监视者服务
 * 
 * @author chaostone
 * 
 */
public interface AuthenticationProvider {

	public Authentication authenticate(Authentication auth) throws AuthenticationException;

	public boolean supports(Class authTokenType);

	public void setUserService(UserService userService);

	public User attachToUser(Authentication auth);

}
