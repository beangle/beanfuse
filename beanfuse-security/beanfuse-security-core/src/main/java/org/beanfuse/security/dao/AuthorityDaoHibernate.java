//$Id: AuthorityDaoHibernate.java,v 1.3 2007/01/13 07:06:51 chaostone Exp $
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
 * chaostone             2005-9-27         Created
 *  
 ********************************************************************************/

package org.beanfuse.security.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beanfuse.persist.hibernate.BaseDaoHibernate;
import org.beanfuse.security.Authority;
import org.beanfuse.security.Group;
import org.beanfuse.security.Resource;
import org.hibernate.Query;

public class AuthorityDaoHibernate extends BaseDaoHibernate implements AuthorityDao {

	public Authority getAuthority(Group group, Resource resource) {
		Map params = new HashMap();
		params.put("group", group);
		params.put("resource", resource);
		List authorityList = entityDao.searchNamedQuery("getGroupAuthorityByResource", params,
				false);
		if (authorityList.isEmpty())
			return null;
		else
			return (Authority) authorityList.get(0);
	}

	public List getAuthorities(Group group) {
		Map params = new HashMap();
		params.put("groupId", group.getId());
		return entityDao.searchNamedQuery("getAuthorities", params, false);
	}

	/**
	 * 查询用户组对应的模块
	 */
	public List getResources(Group group) {
		String hql = "select distinct m from Group as r join r.authorities as a"
				+ " join a.resource as m where  r.id = :groupId and m.enabled = true";
		Query query = getSession().createQuery(hql);
		query.setParameter("groupId", group.getId());
		query.setCacheable(true);
		return query.list();
	}

	/**
	 * 找到该组内激活的资源id
	 */
	public Set getResourceIds(Group group) {
		String hql = "select m.id from Group as r join r.authorities as a"
				+ " join a.resource as m where  r.id = :groupId and m.enabled = true";
		Query query = getSession().createQuery(hql);
		query.setParameter("groupId", group.getId());
		query.setCacheable(true);
		return new HashSet(query.list());
	}

	public void saveOrUpdate(Authority authority) {
		entityDao.saveOrUpdate(authority);
	}

	public void remove(Authority authority) {
		entityDao.remove(authority);
	}

}
