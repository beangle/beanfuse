package org.beanfuse.security.ui;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.beanfuse.security.UserDetails;
import org.beanfuse.security.concurrent.SessionIdentifierAware;
import org.beanfuse.utils.web.RequestUtils;

public class WebUserDetails extends UserDetails implements SessionIdentifierAware, Serializable {

	private static final long serialVersionUID = -5467873860513829680L;

	private String sessionId;

	/** 登录IP */
	private String host;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public WebUserDetails(HttpServletRequest request) {
		setHost(RequestUtils.getIpAddr(request));

		HttpSession session = request.getSession(false);
		this.sessionId = (session != null) ? session.getId() : null;
		doPopulateAdditionalInformation(request);
	}

	protected void doPopulateAdditionalInformation(HttpServletRequest request) {
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + ": ");
		sb.append("RemoteIpAddress: " + this.getHost() + "; ");
		sb.append("SessionId: " + this.getSessionId());
		return sb.toString();
	}
}
