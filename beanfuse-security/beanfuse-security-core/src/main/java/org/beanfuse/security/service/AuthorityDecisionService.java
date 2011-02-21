package org.beanfuse.security.service;

import java.util.Set;

import org.beanfuse.security.Group;

/**
 * 授权判断服务
 * 
 * @author chaostone
 * 
 */
public interface AuthorityDecisionService {

	public boolean isAuthorized(Long userId, String resourceName);

	public void registerAuthorities(Long userId);

	public void registerGroupAuthorities(Group group);

	public void removeAuthorities(Long userId);

	public boolean isPublicResource(String resourceName);

	public Set getPublicResources();

	public void setResourceService(ResourceService resourceService);

	public void setUserService(UserService userService);

	public void setAuthorityService(AuthorityService authorityService);

	public void refreshResourceCache();
}
