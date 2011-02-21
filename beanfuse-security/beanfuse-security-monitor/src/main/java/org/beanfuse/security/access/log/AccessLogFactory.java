package org.beanfuse.security.access.log;

import javax.servlet.http.HttpServletRequest;

import org.beanfuse.security.access.AccessLog;
import org.beanfuse.security.access.config.ConfigLoader;
import org.beanfuse.utils.web.RequestUtils;

public class AccessLogFactory {

	public static AccessLog getLog(HttpServletRequest request) {
		DefaultAccessLog log = null;
		if (UserAccessLog.class.getName().equals(
				ConfigLoader.getInstance().getConfig().getAccessLogClass())) {
			UserAccessLog userAccessLog = new UserAccessLog();
			userAccessLog.setUser(request.getSession().getAttribute(
					ConfigLoader.getInstance().getConfig().getUserKey()));
			log = userAccessLog;
		} else {
			log = new DefaultAccessLog();
		}
		log.setUri(RequestUtils.getRequestURI(request));
		log.setParams(request.getQueryString());
		return log;
	}
}
