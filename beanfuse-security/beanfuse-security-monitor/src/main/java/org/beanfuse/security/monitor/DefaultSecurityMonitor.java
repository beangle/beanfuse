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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.beanfuse.security.Authentication;
import org.beanfuse.security.AuthenticationBreakException;
import org.beanfuse.security.AuthenticationException;
import org.beanfuse.security.UserCategory;
import org.beanfuse.security.UserDetails;
import org.beanfuse.security.concurrent.ConcurrentLoginException;
import org.beanfuse.security.concurrent.category.CategorySessionController;
import org.beanfuse.security.monitor.filters.HttpSessionIntegrationFilter;
import org.beanfuse.security.online.CategoryProfile;
import org.beanfuse.security.online.OnlineActivity;
import org.beanfuse.security.providers.AuthenticationProvider;
import org.beanfuse.security.providers.ProviderNotFoundException;
import org.beanfuse.security.providers.rememberme.RememberMeService;
import org.beanfuse.security.service.AuthorityDecisionService;
import org.beanfuse.security.ui.UserDetailsSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * 缺省的监听实现
 * 
 * @author chaostone
 * 
 */
public class DefaultSecurityMonitor implements SecurityMonitor, InitializingBean {

	protected static Logger logger = LoggerFactory.getLogger(DefaultSecurityMonitor.class);

	/** 允许从cookie中进行登录 */
	protected boolean enableRememberMe = true;

	protected List providers = new ArrayList();

	protected AuthorityDecisionService authorityDecisionService;

	protected CategorySessionController sessionController;

	protected RememberMeService rememberMeService;

	protected UserDetailsSource userDetailsSource;

	protected HttpSessionIntegrationFilter httpSessionIntegrationFilter;

	public boolean isPublicResource(String actionName) {
		return authorityDecisionService.isPublicResource(actionName);
	}

	public void afterPropertiesSet() throws Exception {
		if (providers.isEmpty()) {
			throw new RuntimeException("authentication provider list is empty");
		}
		Assert.notNull(httpSessionIntegrationFilter, "httpSessionIntegrationFilter is null");
		Assert.notNull(sessionController, "sessionController is null");
		Assert.notNull(userDetailsSource, "userDetailsSource is null");
		Assert.notNull(authorityDecisionService, "authorityDecisionService is null");
		logger.info("providers:" + providers);
	}

	/**
	 * 资源是否被授权<br>
	 */
	public boolean isAuthorized(Long userId, String actionName) {
		return authorityDecisionService.isAuthorized(userId, actionName);
	}

	/**
	 * 注销用户<br>
	 * 根据用户session删除用户登陆记录<br>
	 * 删除权限<br>
	 * 持久化会话记录
	 * 
	 * @param sessionId
	 */
	public void logout(HttpSession session) {
		String sessionId = session.getId();
		OnlineActivity info = sessionController.getOnlineActivity(sessionId);
		if (null != info) {
			sessionController.removeAuthentication(sessionId);
			if (!sessionController.isRegisted(info.getPrincipal())) {
				authorityDecisionService.removeAuthorities(info.getUserid());
			}
			httpSessionIntegrationFilter.clear(session);
		}
	}

	/**
	 * 
	 */
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		Iterator iter = getProviders().iterator();
		Class toTest = authentication.getClass();
		AuthenticationException lastException = null;

		while (iter.hasNext()) {
			AuthenticationProvider provider = (AuthenticationProvider) iter.next();
			if (!provider.supports(toTest)) {
				continue;
			}
			Authentication result;
			try {
				result = provider.authenticate(authentication);
			} catch (AuthenticationException ae) {
				lastException = ae;
				if (ae instanceof AuthenticationBreakException) {
					break;
				} else {
					continue;
				}
			}

			if (sessionController.checkAuthenticationAllowed(result)) {
				UserDetails details = (UserDetails) authentication.getDetails();
				sessionController.registerAuthentication(result);
				authorityDecisionService.registerAuthorities(details.getUserid());
				afterAuthenticate(authentication);
				return result;
			} else {
				lastException = new ConcurrentLoginException(Authentication.ERROR_OVERMAX);
				break;
			}
		}

		if (lastException == null) {
			lastException = new ProviderNotFoundException();
		}
		throw lastException;
	}

	/**
	 * after Authenticate process sth.
	 * 
	 * @param authentication
	 */
	protected void afterAuthenticate(Authentication authentication) {
		if (authentication instanceof ServletRequestAware) {
			HttpServletRequest request = ((ServletRequestAware) authentication).getRequest();
			if (null != request) {
				httpSessionIntegrationFilter.register(request.getSession(), authentication);
				CategoryProfile profile = sessionController
						.getCategoryProfile(((UserDetails) authentication.getDetails())
								.getCategory());
				request.getSession().setMaxInactiveInterval(profile.getInactiveInterval() * 60);
				((ServletRequestAware) authentication).setRequest(null);
			}
		}
	}

	public void changeCategory(String sessionId, UserCategory category) {
		sessionController.changeCategory(sessionId, category);
	}

	public boolean enableRememberMe() {
		return enableRememberMe;
	}

	public List getProviders() {
		return providers;
	}

	public void setProviders(List providers) {
		this.providers = providers;
	}

	public RememberMeService getRememberMeService() {
		return rememberMeService;
	}

	public void setRememberMeService(RememberMeService rememberMeService) {
		this.rememberMeService = rememberMeService;
	}

	public void setAuthorityDecisionService(AuthorityDecisionService authorityDecisionService) {
		this.authorityDecisionService = authorityDecisionService;
	}

	public void setSessionController(CategorySessionController sessionController) {
		this.sessionController = sessionController;
	}

	public CategorySessionController getSessionController() {
		return sessionController;
	}

	public boolean isEnableRememberMe() {
		return enableRememberMe;
	}

	public void setEnableRememberMe(boolean enableRememberMe) {
		this.enableRememberMe = enableRememberMe;
	}

	public HttpSessionIntegrationFilter getHttpSessionIntegrationFilter() {
		return httpSessionIntegrationFilter;
	}

	public void setHttpSessionIntegrationFilter(
			HttpSessionIntegrationFilter httpSessionIntegrationFilter) {
		this.httpSessionIntegrationFilter = httpSessionIntegrationFilter;
	}

	public UserDetailsSource getUserDetailsSource() {
		return userDetailsSource;
	}

	public void setUserDetailsSource(UserDetailsSource userDetailsSource) {
		this.userDetailsSource = userDetailsSource;
	}

}
