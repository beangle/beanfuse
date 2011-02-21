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

package org.beanfuse.security.online;

import java.sql.Timestamp;
import java.util.Date;

import org.beanfuse.model.LongIdEntity;
import org.beanfuse.security.UserCategory;

/**
 * 登录和退出日志
 * 
 * @author chaostone
 * 
 */
public interface SessionActivity extends LongIdEntity {

	public void calcOnlineTime();

	public String getSessionid();

	public void setSessionid(String sessionId);

	public String getName();

	public void setName(String name);

	public String getFullname();

	public void setFullname(String fullname);

	public String getHost();

	public void setHost(String host);

	public Date getLoginAt();

	public void setLoginAt(Date loginAt);

	public Date getLastAccessAt();

	public void setLastAccessAt(Date lastAccessAt);

	public Long getOnlineTime();

	public void setOnlineTime(Long onlineTime);

	public UserCategory getCategory();

	public void setCategory(UserCategory category);

	public Timestamp getLogoutAt();

	public void setLogoutAt(Timestamp logoutAt);

	public String getRemark();

	public void setRemark(String remark);
}
