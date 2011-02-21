package org.beanfuse.webapp.security.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.beanfuse.security.Authentication;
import org.beanfuse.security.monitor.SecurityMonitor;
import org.beanfuse.struts2.route.Action;
import org.beanfuse.utils.web.CookieUtils;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author dell,chaostone
 */
public class LogoutAction extends SecurityAction implements ServletRequestAware,
		ServletResponseAware {

	private HttpServletRequest request;

	private HttpServletResponse response;

	private SecurityMonitor securityMonitor;

	public String index() {
		CookieUtils.deleteCookieByName(request, response, Authentication.PASSWORD);
		securityMonitor.logout(request.getSession());
		((SessionMap) ActionContext.getContext().getSession()).invalidate();
		return redirect(new Action(IndexAction.class, "index"), "");
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setSecurityMonitor(SecurityMonitor securityMonitor) {
		this.securityMonitor = securityMonitor;
	}

}
