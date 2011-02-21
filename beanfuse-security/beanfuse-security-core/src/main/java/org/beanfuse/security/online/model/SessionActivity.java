//$Id: LogInOut.java,v 1.1 2007-7-16 上午11:04:58 chaostone Exp $
/*
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2007-7-16         Created
 *  
 ********************************************************************************/

package org.beanfuse.security.online.model;

import java.sql.Timestamp;
import java.util.Date;

import org.beanfuse.model.pojo.LongIdObject;
import org.beanfuse.security.UserCategory;

/**
 * 登录和退出日志
 * 
 * @author chaostone
 * 
 */
public class SessionActivity extends LongIdObject implements
		org.beanfuse.security.online.SessionActivity {

	private static final long serialVersionUID = -3144771635148215917L;

	/** 会话ID */
	private String sessionid;

	/** 系统登录用户 */
	private String name;

	/** 用户真实姓名 */
	private String fullname;

	/** 登录IP */
	private String host;

	/** 登录时间 */
	private Date loginAt;

	/** 最后访问时间 */
	private Date lastAccessAt;

	/** 在线时间 */
	private Long onlineTime;

	/** 用户类型 */
	private UserCategory category;

	/** 退出时间 */
	private Timestamp logoutAt;

	/** 备注 */
	private String remark;

	public SessionActivity() {
		super();
	}

	public SessionActivity(String sessionId, String name, String fullname, UserCategory category) {
		super();
		this.sessionid = sessionId;
		this.name = name;
		this.fullname = fullname;
		this.loginAt = new Date(System.currentTimeMillis());
		this.lastAccessAt = new Date(System.currentTimeMillis());
		this.category = category;
	}

	public String toString() {
		String str = " User:[" + getName() + "]";
		long onlineTime = System.currentTimeMillis() - loginAt.getTime();
		long minute = (onlineTime / 1000) / 60;
		long second = (onlineTime / 1000) % 60;
		str += "OnLine time:[" + minute + " minute " + second + " second]";
		return str;
	}

	public void calcOnlineTime() {
		if (null == logoutAt) {
			setOnlineTime(new Long(System.currentTimeMillis() - loginAt.getTime()));
		} else {
			setOnlineTime(new Long(logoutAt.getTime() - loginAt.getTime()));
		}
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionId) {
		this.sessionid = sessionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Date getLoginAt() {
		return loginAt;
	}

	public void setLoginAt(Date loginAt) {
		this.loginAt = loginAt;
	}

	public Date getLastAccessAt() {
		return lastAccessAt;
	}

	public void setLastAccessAt(Date lastAccessAt) {
		this.lastAccessAt = lastAccessAt;
	}

	public Long getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Long onlineTime) {
		this.onlineTime = onlineTime;
	}

	public UserCategory getCategory() {
		return category;
	}

	public void setCategory(UserCategory category) {
		this.category = category;
	}

	public Timestamp getLogoutAt() {
		return logoutAt;
	}

	public void setLogoutAt(Timestamp logoutAt) {
		this.logoutAt = logoutAt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
