package org.beanfuse.security.concurrent;

import java.util.List;

import org.beanfuse.security.Authentication;
import org.beanfuse.security.AuthenticationException;
import org.beanfuse.security.online.OnlineActivity;

public class ConcurrentSessionControllerImpl implements ConcurrentSessionController {

	protected SessionRegistry sessionRegistry;

	private boolean exceptionIfMaximumExceeded = false;

	private int maximumSessions = 1;

	/**
	 * allowableSessionsExceeded
	 * 
	 * @param sessionId
	 * @param sessions
	 * @param allowableSessions
	 * @param registry
	 * @see checkAuthenticationAllowed
	 */
	protected boolean allowableSessionsExceeded(String sessionId, List sessions,
			int allowableSessions, SessionRegistry registry) {
		if (exceptionIfMaximumExceeded || (sessions == null)) {
			return false;
		}
		// Determine least recently used session, and mark it for invalidation
		OnlineActivity leastRecentlyUsed = null;
		for (int i = 0; i < sessions.size(); i++) {
			if ((leastRecentlyUsed == null)
					|| ((OnlineActivity) sessions.get(i)).getLastAccessAt().before(
							leastRecentlyUsed.getLastAccessAt())) {
				leastRecentlyUsed = (OnlineActivity) sessions.get(i);
			}
		}

		leastRecentlyUsed.expireNow();
		return true;
	}

	public boolean checkAuthenticationAllowed(Authentication request)
			throws AuthenticationException {
		Object principal = request.getPrincipal();
		String sessionId = ((SessionIdentifierAware) request.getDetails()).getSessionId();
		List sessions = sessionRegistry.getOnlineActivities(principal, false);
		int sessionCount = 0;
		if (sessions != null) {
			sessionCount = sessions.size();
		}
		int allowableSessions = getMaximumSessionsForThisUser(request);

		if (sessionCount < allowableSessions) {
			return true;
		} else if (allowableSessions == -1) {
			return true;
		} else if (sessionCount == allowableSessions) {
			for (int i = 0; i < sessionCount; i++) {
				if (((OnlineActivity) sessions.get(i)).getSessionid().equals(sessionId)) {
					return true;
				}
			}
		}
		return allowableSessionsExceeded(sessionId, sessions, allowableSessions, sessionRegistry);
	}

	/**
	 * Method intended for use by subclasses to override the maximum number of
	 * sessions that are permitted for a particular authentication. The default
	 * implementation simply returns the <code>maximumSessions</code> value for
	 * the bean.
	 * 
	 * @param authentication
	 *            to determine the maximum sessions for
	 * 
	 * @return either -1 meaning unlimited, or a positive integer to limit
	 *         (never zero)
	 */
	protected int getMaximumSessionsForThisUser(Authentication authentication) {
		return maximumSessions;
	}

	public void registerAuthentication(Authentication authentication) {
		String sessionId = ((SessionIdentifierAware) authentication.getDetails()).getSessionId();
		sessionRegistry.register(sessionId, authentication);
	}

	public void removeAuthentication(String sessionId) {
		sessionRegistry.remove(sessionId);
	}

	public void setExceptionIfMaximumExceeded(boolean exceptionIfMaximumExceeded) {
		this.exceptionIfMaximumExceeded = exceptionIfMaximumExceeded;
	}

	public void setMaximumSessions(int maximumSessions) {
		this.maximumSessions = maximumSessions;
	}

	public OnlineActivity getOnlineActivity(String sessionId) {
		return sessionRegistry.getOnlineActivity(sessionId);
	}

	public List getOnlineActivities() {
		return sessionRegistry.getOnlineActivities();
	}

	public boolean isRegisted(Object principal) {
		return sessionRegistry.isRegisted(principal);
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}
}
