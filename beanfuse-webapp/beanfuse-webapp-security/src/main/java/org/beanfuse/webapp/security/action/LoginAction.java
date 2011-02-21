package org.beanfuse.webapp.security.action;

import static org.apache.commons.lang.StringUtils.trim;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.beanfuse.security.monitor.SecurityMonitor;
import org.beanfuse.security.providers.UserNamePasswordAuthentication;
import org.beanfuse.webapp.security.formbean.LoginForm;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements ServletRequestAware {

	private SecurityMonitor securityMonitor;

	private HttpServletRequest request;

	private LoginForm loginForm;

	private CaptchaService captchaService;

	public String index() {
		if (null == loginForm) {
			return "failure";
		}
		try {
			String sessionId = request.getSession().getId();
			Boolean valid = captchaService.validateResponseForID(sessionId, loginForm.getCaptcha());
			if (Boolean.FALSE.equals(valid)) {
				addActionError(getText("error.captcha"));
				return "failure";
			}
		} catch (CaptchaServiceException e) {
			addActionError(getText("error.captcha"));
			return "failure";
		}
		String errorMsg = doLogin(loginForm);
		if (StringUtils.isNotEmpty(errorMsg)) {
			addActionError(getText(errorMsg));
			return "failure";
		}
		return "success";
	}

	protected String doLogin(LoginForm loginForm) {
		loginForm.setName(trim(loginForm.getName()));
		org.beanfuse.security.providers.UserNamePasswordAuthentication auth = new UserNamePasswordAuthentication(
				loginForm.getName(), loginForm.getPassword());
		auth.setRequest(request);
		auth.setDetails(securityMonitor.getUserDetailsSource().buildDetails(request));
		try {
			securityMonitor.authenticate(auth);
		} catch (org.beanfuse.security.AuthenticationException e) {
			return e.getMessage();
		}
		return "";
	}

	public void setSecurityMonitor(SecurityMonitor securityMonitor) {
		this.securityMonitor = securityMonitor;
	}

	public LoginForm getLoginForm() {
		return loginForm;
	}

	public void setLoginForm(LoginForm loginForm) {
		this.loginForm = loginForm;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setCaptchaService(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}

}
