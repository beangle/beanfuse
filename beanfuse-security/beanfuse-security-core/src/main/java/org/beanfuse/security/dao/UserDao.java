//$Id: UserDao.java,v 1.1 2006/10/12 14:39:51 chaostone Exp $
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
 * dell                 2005-9-7            Created
 * chaostone            2005-9-26           modified
 *  
 ********************************************************************************/

package org.beanfuse.security.dao;

import java.util.List;

import org.beanfuse.security.User;

/**
 * 系统用户信息存取接口
 * 
 * @author dell,chaostone 2005-9-26
 */
public interface UserDao {
	/**
	 * 根据用户名和密码查找用户
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public User get(String name, String password);

	/**
	 * 返回存在userid的用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public User get(Long userId);

	/**
	 * 返回在数组中指定的用户
	 * 
	 * @param userIds
	 * @return
	 */
	public List get(Long userIds[]);

	/**
	 * 保存新建的用户
	 * 
	 * @param user
	 */
	public void saveOrUpdate(User user);

	/**
	 * 删除用户
	 * 
	 * @param user
	 */
	public void remove(User user);

	/**
	 * 删除id数组中指定的用户
	 * 
	 * @param ids
	 */
	public void remove(Long ids[]);

	/**
	 * 指定用户id删除用户
	 * 
	 * @param userId
	 */
	public void remove(Long userId);

}
