package org.beanfuse.security.online;

//$Id: CategoryProfile.java,v 1.1 2006/10/12 14:40:10 chaostone Exp $
/*
 *
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone            2006-10-12          created
 *  
 ********************************************************************************/

import org.beanfuse.model.LongIdEntity;
import org.beanfuse.security.UserCategory;

/**
 * 用户类别配置
 * 
 * @author chaostone
 * 
 */
public interface CategoryProfile extends LongIdEntity {
	/**
	 * 系统会话配置
	 * 
	 * @return
	 */
	public SessionProfile getSessionProfile();

	/**
	 * 设置系统会话配置
	 * 
	 * @param sessionProfile
	 */
	public void setSessionProfile(SessionProfile sessionProfile);

	/**
	 * 用户类别
	 * 
	 * @return
	 */
	public UserCategory getCategory();

	/**
	 * 设置用户类别
	 * 
	 * @param category
	 */
	public void setCategory(UserCategory category);

	/**
	 * 最大在线人数
	 * 
	 * @return
	 */
	public int getCapacity();

	/**
	 * 设置最大在线人数
	 * 
	 * @param max
	 */
	public void setCapacity(int max);

	/**
	 * 过期时间(以分计(min))
	 * 
	 * @return
	 */
	public int getInactiveInterval();

	/**
	 * 设置过期时间(以分计(min))
	 * 
	 * @param second
	 */
	public void setInactiveInterval(int second);

	/**
	 * 单个用户的最大session数
	 * 
	 * @param sessions
	 * @return
	 */
	public int getUserMaxSessions();

	/**
	 * 设置单个用户的最大session数
	 * 
	 * @param sessions
	 */
	public void setUserMaxSessions(int sessions);
}
