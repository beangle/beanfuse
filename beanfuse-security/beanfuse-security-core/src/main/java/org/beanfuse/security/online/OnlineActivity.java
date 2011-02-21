package org.beanfuse.security.online;

import java.util.Date;

import org.beanfuse.model.pojo.LongIdObject;
import org.beanfuse.security.UserCategory;

public class OnlineActivity extends LongIdObject {
	private static final long serialVersionUID = -4828041170356897582L;
	/** login */
	private Object principal;

	private String sessionid;

	/** userId */
	private Long userid;

	/** 用户真实姓名 */
	private String fullname;

	/** 用户类别 */
	private UserCategory category;

	private String remark;

	/** 登录时间 */
	private Date loginAt;

	/** 登录IP */
	private String host;

	private Date lastAccessAt;

	private boolean expired = false;

	public OnlineActivity() {
		super();
	}

	public void expireNow() {
		this.expired = true;
	}

	public void refreshLastRequest() {
		this.lastAccessAt = new Date();
	}

	public Date getLastAccessAt() {
		return lastAccessAt;
	}

	public void setLastAccessAt(Date lastRequest) {
		this.lastAccessAt = lastRequest;
	}

	public Object getPrincipal() {
		return principal;
	}

	public void setPrincipal(Object principal) {
		this.principal = principal;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public void addRemark(String added) {
		if (null == remark) {
			remark = added;
		} else {
			remark += added;
		}
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getLoginAt() {
		return loginAt;
	}

	public void setLoginAt(Date loginAt) {
		this.loginAt = loginAt;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public UserCategory getCategory() {
		return category;
	}

	public void setCategory(UserCategory category) {
		this.category = category;
	}

	public Long getOnlineTime() {
		return new Long(System.currentTimeMillis() - getLoginAt().getTime());
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
