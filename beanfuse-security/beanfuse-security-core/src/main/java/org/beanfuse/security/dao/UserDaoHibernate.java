//$Id: UserDaoHibernate.java,v 1.2 2006/12/05 08:31:32 chaostone Exp $
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
 * dell                 2005-8-31           Created
 * chaostone            2005-9-26           modified
 *  
 ********************************************************************************/

package org.beanfuse.security.dao;

import java.util.List;

import org.hibernate.Query;

import org.beanfuse.security.User;
import org.beanfuse.security.dao.UserDao;
import org.beanfuse.persist.hibernate.BaseDaoHibernate;

/**
 * 系统用户信息存取实现类
 * 
 * @author dell,chaostone 2005-9-26
 */
public class UserDaoHibernate extends BaseDaoHibernate implements UserDao {

	public User get(String name, String password) {
		Query query = getSession().getNamedQuery("userLogin");
		query.setParameter("name", name);
		query.setParameter("password", password);
		List userList = query.list();
		if (userList.size() > 0)
			return (User) userList.get(0);
		else
			return null;
	}

	public User get(Long userId) {
		return (User) entityDao.get("User", userId);
	}

	public List get(Long[] userIds) {
		String hql = "from User where id in(:ids)";
		Query query = getSession().createQuery(hql);
		query.setParameterList("ids", userIds);
		return query.list();
	}

	public void remove(User user) {
		entityDao.remove(user);
	}

	public void remove(Long userId) {
		entityDao.remove(get(userId));
	}

	public void saveOrUpdate(User user) {
		entityDao.saveOrUpdate(user);
	}

	public void remove(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			remove(ids[i]);
		}
	}

}
