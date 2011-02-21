package org.beanfuse.security.concurrent;

import java.util.List;

import org.beanfuse.security.Authentication;
import org.beanfuse.security.online.OnlineActivity;

/**
 * 记录session信息的注册表
 * 
 * @author chaostone
 * 
 */
public interface SessionRegistry {

	/**
	 * 注册
	 * 
	 * @param sessionId
	 * @param authentication
	 */
	public void register(String sessionId, Authentication authentication);

	/**
	 * 注销指定sessionId
	 * 
	 * @param sessionId
	 * @return
	 */
	public OnlineActivity remove(String sessionId);

	/**
	 * 查询在线记录
	 * 
	 * @return
	 */
	public List getOnlineActivities();

	/**
	 * 查询某帐号的在线信息
	 * 
	 * @param principal
	 * @param includeExpiredSessions
	 * @return
	 */
	public List getOnlineActivities(Object principal, boolean includeExpiredSessions);

	/**
	 * 查询对应sessionId的信息
	 * 
	 * @param sessionId
	 * @return
	 */
	public OnlineActivity getOnlineActivity(String sessionId);

	/**
	 * 查询帐号是否还有没有过期的在线记录
	 * 
	 * @param principal
	 * @return
	 */
	public boolean isRegisted(Object principal);

	/**
	 * 更新对应sessionId的最后访问时间
	 * 
	 * @param sessionId
	 */
	public void refreshLastRequest(String sessionId);

	/**
	 * session count
	 * 
	 * @return
	 */
	public int count();

}
