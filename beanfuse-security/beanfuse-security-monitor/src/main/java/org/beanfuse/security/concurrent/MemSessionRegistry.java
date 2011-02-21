package org.beanfuse.security.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beanfuse.security.Authentication;
import org.beanfuse.security.UserDetails;
import org.beanfuse.security.online.OnlineActivity;
import org.beanfuse.security.ui.WebUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemSessionRegistry implements SessionRegistry {

	protected static final Logger logger = LoggerFactory.getLogger(MemSessionRegistry.class);

	// <principal:Object,SessionIdSet>
	protected Map principals = Collections.synchronizedMap(new HashMap());
	// <sessionId:Object,OnlineActvities>
	protected Map sessionIds = Collections.synchronizedMap(new HashMap());

	public List getOnlineActivities() {
		return new ArrayList(sessionIds.values());
	}

	public boolean isRegisted(Object principal) {
		Set sessionsUsedByPrincipal = (Set) principals.get(principal);
		return (null != sessionsUsedByPrincipal && !sessionsUsedByPrincipal.isEmpty());
	}

	public List getOnlineActivities(Object principal, boolean includeExpiredSessions) {
		Set sessionsUsedByPrincipal = (Set) principals.get(principal);
		List list = new ArrayList();
		if (null == sessionsUsedByPrincipal) {
			return list;
		}
		synchronized (sessionsUsedByPrincipal) {
			for (Iterator iter = sessionsUsedByPrincipal.iterator(); iter.hasNext();) {
				String sessionId = (String) iter.next();
				OnlineActivity activity = getOnlineActivity(sessionId);
				if (activity == null) {
					continue;
				}
				if (includeExpiredSessions || !activity.isExpired()) {
					list.add(activity);
				}
			}
		}

		return list;
	}

	public OnlineActivity getOnlineActivity(String sessionId) {
		return (OnlineActivity) sessionIds.get(sessionId);
	}

	public void refreshLastRequest(String sessionId) {
		OnlineActivity info = getOnlineActivity(sessionId);
		if (info != null) {
			info.refreshLastRequest();
		}
	}

	public OnlineActivity buildActivity(Object principal, Object userDetails, String sessionid,
			Date lastRequest) {
		OnlineActivity activity = new OnlineActivity();
		activity.setSessionid(sessionid);
		activity.setPrincipal(principal);
		activity.setLastAccessAt(lastRequest);
		UserDetails details = (UserDetails) userDetails;
		activity.setUserid(details.getUserid());
		activity.setFullname(details.getFullname());
		activity.setCategory(details.getCategory());
		if (details instanceof WebUserDetails) {
			activity.setHost(((WebUserDetails) details).getHost());
		}
		activity.setLoginAt(new Date());
		return activity;
	}

	public void register(String sessionId, Authentication authentication) {
		Object principal = authentication.getPrincipal();
		OnlineActivity existed = getOnlineActivity(sessionId);
		if (null != existed) {
			existed.addRemark(" expired with replacement.");
			remove(sessionId);
		}
		sessionIds.put(sessionId, buildActivity(principal, authentication.getDetails(), sessionId,
				new Date()));

		Set sessionsUsedByPrincipal = (Set) principals.get(principal);

		if (sessionsUsedByPrincipal == null) {
			sessionsUsedByPrincipal = Collections.synchronizedSet(new HashSet(4));
			principals.put(principal, sessionsUsedByPrincipal);
		}

		sessionsUsedByPrincipal.add(sessionId);
		logger.debug("Register session {} for {}", sessionId, principal);
	}

	public OnlineActivity remove(String sessionId) {
		OnlineActivity info = getOnlineActivity(sessionId);
		if (null == info) {
			return null;
		}
		sessionIds.remove(sessionId);
		logger.debug("Remove session {} for {}", sessionId, info.getPrincipal());
		Set sessionsUsedByPrincipal = (Set) principals.get(info.getPrincipal());
		if (null != sessionsUsedByPrincipal) {
			synchronized (sessionsUsedByPrincipal) {
				sessionsUsedByPrincipal.remove(sessionId);
				// No need to keep object in principals Map anymore
				if (sessionsUsedByPrincipal.size() == 0) {
					principals.remove(info.getPrincipal());
					logger.debug("Remove principal {} from registry", info.getPrincipal());
				}
			}
		}
		return info;
	}

	public int count() {
		return sessionIds.size();
	}

}
