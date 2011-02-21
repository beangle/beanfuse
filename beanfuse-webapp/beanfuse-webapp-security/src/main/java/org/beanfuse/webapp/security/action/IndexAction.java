package org.beanfuse.webapp.security.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.beanfuse.security.monitor.SecurityMonitor;
import org.beanfuse.security.online.OnlineActivity;

/**
 * 系统index页面，如果登录成功则进入首页面
 * 
 * @author chaostone
 */
public class IndexAction extends SecurityAction implements ServletRequestAware {
	private HttpServletRequest request;

	private SecurityMonitor securityMonitor;

	public String index() {
		OnlineActivity info = securityMonitor.getSessionController().getOnlineActivity(
				request.getSession().getId());
		if (null == info) {
			return LOGIN;
		} else {
			return SUCCESS;
		}
	}

	public void setSecurityMonitor(SecurityMonitor securityMonitor) {
		this.securityMonitor = securityMonitor;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}
