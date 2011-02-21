package org.beanfuse.security.access.config;

import org.beanfuse.security.access.ResourceAccessor;

public class AccessConfig {

	private String userKey;

	private Long minDuration;

	private String accessorClass;

	private String accessLogClass;

	private Integer cachedLogSize;

	public String toString() {
		String toStr = "{minDuration=" + minDuration + ";accessorClass=" + accessorClass
				+ ";accessLogClass=" + accessLogClass + ";userKey=" + userKey + ";cachedLogSize="
				+ cachedLogSize + "}";
		return toStr;
	}

	public ResourceAccessor getAccessor() {
		ResourceAccessor accessor = null;
		try {
			accessor = (ResourceAccessor) Class.forName(accessorClass).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return accessor;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public Long getMinDuration() {
		return minDuration;
	}

	public void setMinDuration(Long minDuration) {
		this.minDuration = minDuration;
	}

	public String getAccessorClass() {
		return accessorClass;
	}

	public void setAccessorClass(String accessorClass) {
		this.accessorClass = accessorClass;
	}

	public String getAccessLogClass() {
		return accessLogClass;
	}

	public void setAccessLogClass(String accessLogClass) {
		this.accessLogClass = accessLogClass;
	}

	public Integer getCachedLogSize() {
		return cachedLogSize;
	}

	public void setCachedLogSize(Integer cachedLogSize) {
		this.cachedLogSize = cachedLogSize;
	}

}
