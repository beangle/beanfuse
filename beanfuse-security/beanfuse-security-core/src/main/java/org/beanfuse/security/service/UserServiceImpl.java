//$Id: UserServiceImpl.java,v 1.2 2006/10/19 09:21:32 chaostone Exp $
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
 * dell                                     Created
 * chaostone            2005-9-26           rename some method's signature         
 *  
 ********************************************************************************/

package org.beanfuse.security.service;

import java.sql.Date;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.model.EntityExistException;
import org.beanfuse.persist.impl.BaseServiceImpl;
import org.beanfuse.query.Condition;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.security.Group;
import org.beanfuse.security.User;
import org.beanfuse.security.dao.UserDao;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * 用户信息服务的实现类
 * 
 * @author dell,chaostone 2005-9-26
 */
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public User get(String name, String password) {
		return userDao.get(name, password);
	}

	public User get(Long id) {
		return userDao.get(id);
	}

	public User get(String loginName) {
		if (StringUtils.isEmpty(loginName))
			return null;
		EntityQuery entityQuery = new EntityQuery(User.class, "user");
		entityQuery.add(new Condition("user.name=:name", loginName));
		Collection users = entityService.search(entityQuery);
		if (users.isEmpty())
			return null;
		else
			return (User) users.iterator().next();
	}

	public List getUsers(Long[] userIds) {
		return userDao.get(userIds);
	}

	public void updateState(Long[] ids, int state) {
		if (null == ids || ids.length < 1)
			return;
		List users = userDao.get(ids);
		for (int i = 0; i < users.size(); i++) {
			User cur = (User) users.get(i);
			cur.setStatus(state);
		}
		entityService.saveOrUpdate(users);
	}

	public void saveOrUpdate(User user) throws EntityExistException {
		try {
			user.setUpdatedAt(new Date(System.currentTimeMillis()));
			if (user.isVO()) {
				user.setCreatedAt(new Date(System.currentTimeMillis()));
			}
			entityService.saveOrUpdate(user);
		} catch (DataIntegrityViolationException e) {
			throw new EntityExistException("User already exits:" + user);
		} catch (Exception e) {
			throw new EntityExistException("User already exits:" + user);
		}
	}

	public Set getGroups(User user) {
		return user.getGroups();
	}

	public void createUser(User creator, User newUser) {
		newUser.setCreator(creator);
		newUser.setUpdatedAt(new Date(System.currentTimeMillis()));
		newUser.setCreatedAt(new Date(System.currentTimeMillis()));
		entityDao.saveOrUpdate(newUser);
	}

	public void removeUser(User manager, User user) {
		if (isManagedBy(manager, user)) {
			entityService.remove(user);
		}
	}

	public boolean isManagedBy(User manager, User user) {
		if (manager.isAdmin() || manager.equals(user.getCreator())) {
			return true;
		} else {
			for (Iterator iter = manager.getMngGroups().iterator(); iter.hasNext();) {
				Group group = (Group) iter.next();
				if (group.getUsers().contains(user)) {
					return true;
				}
			}
			return false;
		}
	}

	public void createGroup(User creator, Group group) {
		group.setUpdatedAt(new Date(System.currentTimeMillis()));
		group.setCreatedAt(new Date(System.currentTimeMillis()));
		group.setCreator(creator);
		// 纳入创建者的管理范围
		creator.getMngGroups().add(group);
		entityDao.saveOrUpdate(group);
		entityDao.saveOrUpdate(creator);
	}

	/**
	 * 超级管理员不能删除
	 */
	public void removeGroup(User manager, List groups) {
		for (Iterator iterator = groups.iterator(); iterator.hasNext();) {
			Group group = (Group) iterator.next();
			if (manager.getMngGroups().contains(group))
				manager.getMngGroups().remove(group);
			if (group.getCreator().equals(manager) || manager.isAdmin())
				entityService.remove(group);
		}
		entityService.saveOrUpdate(manager);
	}

}
