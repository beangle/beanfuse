/*
 *
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2006-8-28            Created
 *  
 ********************************************************************************/
package org.beanfuse.security.monitor;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.beanfuse.security.Authentication;
import org.beanfuse.security.AuthenticationException;
import org.beanfuse.security.concurrent.category.CategorySessionController;
import org.beanfuse.security.monitor.filters.HttpSessionIntegrationFilter;
import org.beanfuse.security.providers.AuthenticationProvider;
import org.beanfuse.security.providers.rememberme.RememberMeService;
import org.beanfuse.security.ui.UserDetailsSource;

/**
 * 用户在线服务提供类
 * 
 * @author chaostone
 * 
 */
public interface SecurityMonitor {

	// -----------Authentication-----------------
	/**
	 * 认证
	 * 
	 * @param auth
	 * @param httpRequest
	 * @return
	 * @throws AuthenticationException
	 */
	public Authentication authenticate(Authentication auth) throws AuthenticationException;

	/**
	 * 退出 1)unregister session registry<br>
	 * 2)unregister authority<br>
	 * 3)clear session attributes(httpSessionIntegrationFilter)<br>
	 * but not invalidate it
	 * 
	 * @see HttpSessionIntegrationFilter
	 * @param sessionId
	 */
	public void logout(HttpSession session);

	/**
	 * enableRememberMe
	 * 
	 * @return
	 */
	public boolean enableRememberMe();

	/**
	 * @see RememberMeService
	 * @return
	 */
	public RememberMeService getRememberMeService();

	/**
	 * @see RememberMeService
	 * @param rememberMeService
	 */
	public void setRememberMeService(RememberMeService rememberMeService);

	/**
	 * 用户详情
	 * 
	 * @return
	 */
	public UserDetailsSource getUserDetailsSource();

	/**
	 * 设置用户详情
	 * 
	 * @param source
	 */
	public void setUserDetailsSource(UserDetailsSource source);

	/**
	 * @see AuthenticationProvider
	 * @return
	 */
	public List getProviders();

	/**
	 * @see AuthenticationProvider
	 * @param providers
	 */
	public void setProviders(List providers);

	// --------------------------authrization-------------------
	/**
	 * 是否经过授权
	 * 
	 * @param user
	 * @param resourceName
	 * @return
	 */
	public boolean isAuthorized(Long userId, String resourceName);

	/**
	 * 是否忽略判断
	 * 
	 * @param resourceName
	 * @return
	 */
	public boolean isPublicResource(String resourceName);

	// ---------------session monitoring------------------
	/**
	 * 会话控制器
	 * 
	 * @see CategorySessionController
	 * @return
	 */
	public CategorySessionController getSessionController();

	/**
	 * 设置会话控制器
	 * 
	 * @param sessionController
	 */
	public void setSessionController(CategorySessionController sessionController);

	// -----------httpsession integration------------
	public HttpSessionIntegrationFilter getHttpSessionIntegrationFilter();

}
