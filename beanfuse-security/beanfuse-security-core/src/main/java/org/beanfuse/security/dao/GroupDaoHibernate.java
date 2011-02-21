//$Id: GroupDaoHibernate.java,v 1.1 2006/10/12 14:39:58 chaostone Exp $
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
 * chaostone            2005-9-26           move,add
 *  
 ********************************************************************************/

package org.beanfuse.security.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import org.beanfuse.persist.hibernate.BaseDaoHibernate;
import org.beanfuse.security.Group;
import org.beanfuse.security.dao.GroupDao;

/**
 * 用户组信息存取实现类
 * 
 * @author dell, chaostone 2005-9-26
 */
public class GroupDaoHibernate extends BaseDaoHibernate implements GroupDao {

	public void remove(Group group) {
		entityDao.remove(group);
	}

	public void remove(Long groupId) {
		Group group = (Group) entityDao.get(Group.class, groupId);
		entityDao.remove(group);
	}

	public List get(Long[] userIds) {
		Criteria criteria = getSession().createCriteria(Group.class);
		criteria.add(Restrictions.in("id", userIds));
		return criteria.list();
	}

	public Group get(Long groupId) {
		return (Group) entityDao.get("Group", groupId);
	}

	public void saveOrUpdate(Group group) {
		entityDao.saveOrUpdate(group);
	}

}
