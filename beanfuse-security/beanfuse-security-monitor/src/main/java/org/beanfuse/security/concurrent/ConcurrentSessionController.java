package org.beanfuse.security.concurrent;

import java.util.List;

import org.beanfuse.security.Authentication;
import org.beanfuse.security.online.OnlineActivity;

public interface ConcurrentSessionController {

	public boolean checkAuthenticationAllowed(Authentication auth);

	public void registerAuthentication(Authentication authentication);

	public void removeAuthentication(String sessionId);

	public void setSessionRegistry(SessionRegistry sessionRegistry);

	public OnlineActivity getOnlineActivity(String sessionId);

	public boolean isRegisted(Object principal);

	public List getOnlineActivities();

}
