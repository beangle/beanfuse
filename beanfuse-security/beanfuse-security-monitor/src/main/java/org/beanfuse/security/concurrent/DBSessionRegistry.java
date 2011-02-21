package org.beanfuse.security.concurrent;

import java.util.ArrayList;
import java.util.List;

import org.beanfuse.persist.hibernate.BaseDaoHibernate;
import org.beanfuse.security.Authentication;
import org.beanfuse.security.online.OnlineActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBSessionRegistry extends BaseDaoHibernate implements SessionRegistry {

	protected static final Logger logger = LoggerFactory.getLogger(DBSessionRegistry.class);

	public List getOnlineActivities() {
		return entityDao.searchHQLQuery("from " + OnlineActivity.class.getName());
	}

	public boolean isRegisted(Object principal) {
		return true;
	}

	public List getOnlineActivities(Object principal, boolean includeExpiredSessions) {
		List list = new ArrayList();
		return list;
	}

	public OnlineActivity getOnlineActivity(String sessionId) {
		return null;
	}

	public void refreshLastRequest(String sessionId) {
		OnlineActivity info = getOnlineActivity(sessionId);
		if (info != null) {
			info.refreshLastRequest();
		}
	}

	public void register(String sessionId, Authentication authentication) {
	}

	public OnlineActivity remove(String sessionId) {
		return null;
	}

	public int count() {
		return 0;
	}

}
