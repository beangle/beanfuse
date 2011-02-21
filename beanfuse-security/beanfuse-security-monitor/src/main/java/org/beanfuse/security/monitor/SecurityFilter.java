//$Id: CheckAuthorityFilter.java,v 1.6 2007/01/13 07:06:51 chaostone Exp $
/*
 *
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 */
/********************************************************************************
 * @author duyaming,duantihua
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * duyaming              2005-10-21         Created
 * duantihua             2006-2-8           add comment& head
 *  
 ********************************************************************************/

package org.beanfuse.security.monitor;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.beanfuse.security.Authentication;
import org.beanfuse.security.AuthenticationException;
import org.beanfuse.security.online.OnlineActivity;
import org.beanfuse.security.providers.sso.SsoAuthentication;
import org.beanfuse.security.web.DefaultResourceExtractor;
import org.beanfuse.security.web.ResourceExtractor;
import org.beanfuse.security.web.SimpleResourceExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 权限过滤器<br>
 * 
 * @author chaostone 主要根据请求链接的最后的*.do判断该action是否在用户的权限范围内.
 *         不用考虑的权限模块定义在同一级的IgnoreAction.properties文件里.
 */
public class SecurityFilter implements Filter {

	private final static Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

	public static String PREVIOUS_URL = "previousURL";

	private SecurityMonitor monitor;

	private String loginFailPath = null;

	private String noAuthorityPath = null;

	private String expiredPath = null;

	/** 功能点提取类名 */
	private String resourceExtractorClassName = DefaultResourceExtractor.class.getName();

	private ResourceExtractor resourceExtractor;

	private Set freeResources = new HashSet();

	public void init(FilterConfig cfg) throws ServletException {
		Enumeration en = cfg.getInitParameterNames();
		while (en.hasMoreElements()) {
			String property = (String) en.nextElement();
			Object value = cfg.getInitParameter(property);
			try {
				PropertyUtils.setProperty(this, property, value);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		// 初始化功能点提取器
		if (StringUtils.isEmpty(resourceExtractorClassName)) {
			resourceExtractorClassName = SimpleResourceExtractor.class.getName();
		}
		try {
			resourceExtractor = (ResourceExtractor) Class.forName(resourceExtractorClassName)
					.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		if (StringUtils.isEmpty(expiredPath)) {
			expiredPath = loginFailPath;
		}
		freeResources.add(resourceExtractor.extract(loginFailPath));
		freeResources.add(resourceExtractor.extract(expiredPath));
		freeResources.add(resourceExtractor.extract(noAuthorityPath));

		logger.info("Filter {} configured successfully with free resources {}",
				cfg.getFilterName(), freeResources);
	}

	/**
	 * 实施过滤
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		String resource = resourceExtractor.extract(httpRequest);
		request.setAttribute("resourceName", resource);
		HttpSession session = httpRequest.getSession(true);
		if (null == monitor) {
			WebApplicationContext wac = WebApplicationContextUtils
					.getRequiredWebApplicationContext(session.getServletContext());
			monitor = (SecurityMonitor) wac.getBean("securityMonitor", SecurityMonitor.class);
		}
		// 避免login没有权限，出现死循环
		if (!freeResources.contains(resource) && !monitor.isPublicResource(resource)) {
			OnlineActivity info = monitor.getSessionController().getOnlineActivity(session.getId());
			if (info != null && null != httpRequest.getRemoteUser()
					&& !info.getPrincipal().equals(httpRequest.getRemoteUser())) {
				info = null;
			}
			if (null == info) {
				Authentication auth = null;
				// remember me
				if (monitor.enableRememberMe()) {
					auth = monitor.getRememberMeService().autoLogin(httpRequest);
				}
				if (null == auth) {
					auth = new SsoAuthentication(httpRequest);
					auth.setDetails(monitor.getUserDetailsSource().buildDetails(httpRequest));
				}
				try {
					monitor.authenticate(auth);
				} catch (AuthenticationException e) {
					// 记录访问失败的URL
					session.setAttribute(PREVIOUS_URL, httpRequest.getRequestURL() + "?"
							+ httpRequest.getQueryString());
					redirectTo((HttpServletRequest) request, (HttpServletResponse) response,
							loginFailPath);
					return;
				}
			} else if (info.isExpired()) {
				monitor.logout(session);
				// 记录访问失败的URL
				session.setAttribute(PREVIOUS_URL, httpRequest.getRequestURL() + "?"
						+ httpRequest.getQueryString());
				redirectTo((HttpServletRequest) request, (HttpServletResponse) response,
						expiredPath);
				return;
			} else {
				info.refreshLastRequest();
				boolean pass = monitor.isAuthorized(info.getUserid(), resource);
				if (pass) {
					logger.debug("user {} access {} success", info.getPrincipal(), resource);
				} else {
					logger
							.info("user {} cannot access resource[{}]", info.getPrincipal(),
									resource);
					redirectTo((HttpServletRequest) request, (HttpServletResponse) response,
							noAuthorityPath);
					return;
				}
			}
		} else {
			logger.debug("free or public resource {} was accessed", resource);
		}
		chain.doFilter(request, response);
	}

	public void redirectTo(HttpServletRequest request, HttpServletResponse response, String path)
			throws IOException {
		if (!path.startsWith("/")) {
			String contextPath = request.getContextPath();
			((HttpServletResponse) response).sendRedirect((contextPath.equals("/") ? ""
					: (contextPath + "/"))
					+ path);
		} else {
			((HttpServletResponse) response).sendRedirect(path);
		}
	}

	public void destroy() {
	}

	public String getLoginFailPath() {
		return loginFailPath;
	}

	public void setLoginFailPath(String failRedirect) {
		this.loginFailPath = failRedirect;
	}

	public String getNoAuthorityPath() {
		return noAuthorityPath;
	}

	public void setNoAuthorityPath(String noAuthorityRedirect) {
		this.noAuthorityPath = noAuthorityRedirect;
	}

	public String getExpiredPath() {
		return expiredPath;
	}

	public void setExpiredPath(String expiredPath) {
		this.expiredPath = expiredPath;
	}

	public String getResourceExtractorClassName() {
		return resourceExtractorClassName;
	}

	public void setResourceExtractorClassName(String resourceExtractorClassName) {
		this.resourceExtractorClassName = resourceExtractorClassName;
	}

}
