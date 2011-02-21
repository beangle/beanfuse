//$Id: OnlineUserListener.java,v 1.3 2006/10/12 14:39:39 chaostone Exp $
/*
 *
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 */
/********************************************************************************
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-10-17         Created
 *  
 ********************************************************************************/

package org.beanfuse.security.monitor;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author chaostone
 */
public class OnlineUserListener implements HttpSessionListener {

	private SecurityMonitor monitor;

	/**
	 * session建立时
	 */
	public void sessionCreated(HttpSessionEvent event) {
	}

	/**
	 * session销毁时 销毁该session对应的登陆用户记录
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		if (null == monitor) {
			WebApplicationContext wac = WebApplicationContextUtils
					.getRequiredWebApplicationContext(event.getSession().getServletContext());
			monitor = (SecurityMonitor) wac.getBean("securityMonitor", SecurityMonitor.class);
		}
		monitor.logout(event.getSession());
	}

}
