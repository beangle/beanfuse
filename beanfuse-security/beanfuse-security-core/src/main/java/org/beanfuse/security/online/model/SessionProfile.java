package org.beanfuse.security.online.model;

import java.util.HashMap;
import java.util.Map;

import org.beanfuse.model.pojo.LongIdObject;
/**
 * 会话配置
 * @author chaostone
 *
 */
public class SessionProfile extends LongIdObject implements org.beanfuse.security.online.SessionProfile {
	
	/**配置名称*/
	private String name;
	
	/**系统最大在线人数*/
	private int capacity;

	/**单用户最大session数*/
	private int userMaxSessions;

	/**过期时间(min) */
	private int inactiveInterval;
	
	/**用户种类特定配置*/
	private Map categoryProfiles=new HashMap();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int max) {
		this.capacity = max;
	}

	public int getUserMaxSessions() {
		return userMaxSessions;
	}

	public void setUserMaxSessions(int maxSessions) {
		this.userMaxSessions = maxSessions;
	}

	public int getInactiveInterval() {
		return inactiveInterval;
	}

	public void setInactiveInterval(int inactiveInterval) {
		this.inactiveInterval = inactiveInterval;
	}

	public Map getCategoryProfiles() {
		return categoryProfiles;
	}

	public void setCategoryProfiles(Map categoryProfiles) {
		this.categoryProfiles = categoryProfiles;
	}
	
}
