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

package org.beanfuse.security.menu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.persist.hibernate.BaseDaoHibernate;
import org.beanfuse.security.Authority;
import org.beanfuse.security.Group;
import org.beanfuse.security.menu.Menu;
import org.beanfuse.security.menu.MenuAuthority;
import org.beanfuse.security.menu.MenuProfile;
import org.hibernate.Query;

public class MenuAuthorityDaoHibernate extends BaseDaoHibernate implements MenuAuthorityDao {

	public MenuAuthority getMenuAuthority(Group group, Menu menu) {
		Map params = new HashMap();
		params.put("group", group);
		params.put("menu", menu);
		List authorityList = entityDao.searchNamedQuery("getGroupMenu", params, false);
		if (authorityList.isEmpty())
			return null;
		else
			return (MenuAuthority) authorityList.get(0);
	}

	public List getMenuAuthorities(MenuProfile profile, Group group, int depth, String ancestorCode) {
		StringBuilder hql = new StringBuilder(
				" select a  from MenuAuthority a join a.group as r");
		hql.append(" join a.menu as m where r = :group" + " and m.enabled=true ");
		if (null != profile) {
			hql.append(" and m.profile=:profile");
		}
		if (StringUtils.isNotEmpty(ancestorCode)) {
			hql.append(" and m.code like :ancestorCode and length(m.code)>:ancestorCodeLength");
		}
		if (depth > 0) {
			hql.append(" and length(m.code)/2 <= :depth");
		}
		Query query = getSession().createQuery(hql.toString());
		query.setParameter("group", group);

		if (StringUtils.isNotEmpty(ancestorCode)) {
			query.setParameter("ancestorCode", ancestorCode + "%");
			query.setParameter("ancestorCodeLength", new Long(ancestorCode.length()));
		}
		if (depth > 0) {
			query.setParameter("depth", new Long(depth));
		}
		if (null != profile) {
			query.setParameter("profile", profile);
		}
		return query.list();
	}

	/**
	 * 查询用户组对应的模块
	 */
	public List getMenus(MenuProfile profile, Group group, int depth, String ancestorCode) {
		StringBuilder hql = new StringBuilder(" select m from MenuAuthority a join a.group as r");
		hql.append(" join a.menu as m where r = :group" + " and m.enabled=true ");
		if (null != profile) {
			hql.append(" and m.profile=:profile");
		}
		if (StringUtils.isNotEmpty(ancestorCode)) {
			hql.append(" and m.code like :ancestorCode and length(m.code)>:ancestorCodeLength");
		}
		if (depth > 0) {
			hql.append(" and length(m.code)/2 <= :depth");
		}
		Query query = getSession().createQuery(hql.toString());
		query.setParameter("group", group);

		if (StringUtils.isNotEmpty(ancestorCode)) {
			query.setParameter("ancestorCode", ancestorCode + "%");
			query.setParameter("ancestorCodeLength", new Long(ancestorCode.length()));
		}
		if (depth > 0) {
			query.setParameter("depth", new Long(depth));
		}
		if (null != profile) {
			query.setParameter("profile", profile);
		}
		query.setCacheable(true);
		return query.list();
	}

	public void saveOrUpdate(Authority authority) {
		entityDao.saveOrUpdate(authority);
	}

	public void remove(Authority authority) {
		entityDao.remove(authority);
	}

}
