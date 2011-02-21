package org.beanfuse.security.access;

import javax.servlet.http.HttpServletRequest;

import org.beanfuse.security.access.log.AccessLogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultResourceAccessor implements ResourceAccessor {
	private static final Logger logger = LoggerFactory.getLogger(DefaultResourceAccessor.class);

	public AccessLog beginAccess(HttpServletRequest request, long time) {
		AccessLog accessLog = AccessLogFactory.getLog(request);
		accessLog.setBeginAt(time);
		if (logger.isDebugEnabled()) {
			logger.debug(accessLog.toString());
		}
		return accessLog;
	}

	public void endAccess(AccessLog accessLog, long time) {
		accessLog.setEndAt(time);
		if (logger.isDebugEnabled()) {
			logger.debug(accessLog.toString());
		}
	}

	public void finish() {

	}

	public void start() {

	}
}
