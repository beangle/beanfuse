package org.beanfuse.security.access;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.beanfuse.security.access.config.ConfigLoader;
import org.beanfuse.security.access.log.AccessLogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CachedResourceAccessor implements ResourceAccessor {
	private static final Logger logger = LoggerFactory.getLogger(CachedResourceAccessor.class);

	private List accessLogs = new ArrayList();

	private int cacheSize = ConfigLoader.getInstance().getConfig().getCachedLogSize().intValue();

	public AccessLog beginAccess(HttpServletRequest request, long time) {
		AccessLog accessLog = AccessLogFactory.getLog(request);
		accessLog.setBeginAt(time);
		synchronized (accessLogs) {
			accessLogs.add(accessLog);
			if (accessLogs.size() >= cacheSize) {
				shrink(accessLogs);
				if (accessLogs.size() >= cacheSize) {
					flush(accessLogs);
				}
			}
		}
		return accessLog;
	}

	public void endAccess(AccessLog log, long time) {
		log.setEndAt(time);
	}

	public void finish() {
		if (!accessLogs.isEmpty()) {
			flush(accessLogs);
		}
	}

	public void start() {

	}

	public List getAccessLogs() {
		return accessLogs;
	}

	protected void shrink(List accessLogs) {
		long minDuration = ConfigLoader.getInstance().getConfig().getMinDuration().longValue();
		for (Iterator iterator = accessLogs.iterator(); iterator.hasNext();) {
			AccessLog accessLog = (AccessLog) iterator.next();
			if (accessLog.getDuration() < minDuration) {
				iterator.remove();
			}
		}
	}

	protected void flush(List accessLogs) {
		for (Iterator iterator = accessLogs.iterator(); iterator.hasNext();) {
			AccessLog accessLog = (AccessLog) iterator.next();
			logger.info(accessLog.toString());
		}
		accessLogs.clear();
	}
}
