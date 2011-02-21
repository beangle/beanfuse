//$Id: SystemUserService.java,v 1.1 2007-4-8 下午04:50:12 chaostone Exp $
/*
 *
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-4-8         Created
 *  
 ********************************************************************************/

package org.beanfuse.security.service;

import java.util.List;
import java.util.Set;

import org.beanfuse.model.EntityExistException;
import org.beanfuse.security.Group;
import org.beanfuse.security.User;
import org.beanfuse.security.dao.UserDao;

public interface UserService {
	/**
	 * 根据用户名和密码查找用户
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public User get(String name, String password);

	/**
	 * 根据登陆名查找用户
	 * 
	 * @param name
	 * @return
	 */
	public User get(String name);

	/**
	 * 查询指定id的用户，不存在时返回null
	 * 
	 * @param id
	 * @return
	 */
	public User get(Long id);

	/**
	 * 保存新用户，用户存在时，抛出异常
	 * 
	 * @param user
	 */
	public void saveOrUpdate(User user) throws EntityExistException;

	/**
	 * 返回userIds指定的用户
	 * 
	 * @param userIds
	 * @return
	 */
	public List getUsers(Long userIds[]);

	/**
	 * 查询用户关联的用户组(不包含上级组)
	 * 
	 * @param user
	 * @return
	 */
	public Set getGroups(User user);

	/**
	 * 设置用户状态
	 * 
	 * @param userIds
	 * @param state
	 */
	public void updateState(Long[] userIds, int state);

	/**
	 * 创建帐户
	 * 
	 * @param creator
	 * @param newUser
	 */
	public void createUser(User creator, User newUser);

	/**
	 * 删除creator与managed的管理关系，如该用户为creator所创建，则删除user
	 * 
	 * @param creator
	 * @param managed
	 */
	public void removeUser(User creator, User user);

	/**
	 * 是否属于管理关系
	 * 
	 * @param manager
	 * @param managed
	 * 
	 * @return
	 */
	public boolean isManagedBy(User manager, User user);

	/**
	 * 创建一个用户组
	 * 
	 * @param creator
	 * @param group
	 */
	public void createGroup(User creator, Group group);

	/**
	 * 删除管理者与用户组的管理关系，如果该用户组为其所创建则彻底删除. 1)超级管理员不能被删除.<br>
	 * 2)如果删除人有超级管理员用户组，则可以删除不是自己创建的用户组
	 * 
	 * @param manager
	 * @param group
	 */
	public void removeGroup(User manager, List groups);

	/**
	 * 设置用户信息管理的数据存取对象
	 * 
	 * @param userDao
	 */
	public void setUserDao(UserDao userDao);
}
