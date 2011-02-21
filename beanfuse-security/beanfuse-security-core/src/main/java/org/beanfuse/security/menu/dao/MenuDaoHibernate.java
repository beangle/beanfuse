//$Id: MenuDaoHibernate.java,v 1.2 2006/11/28 03:22:36 chaostone Exp $
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
 * chaostone            2005-9-26           rename,modify
 *  
 ********************************************************************************/

package org.beanfuse.security.menu.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beanfuse.persist.hibernate.BaseDaoHibernate;
import org.beanfuse.persist.hibernate.CriterionUtils;
import org.beanfuse.security.menu.Menu;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * 系统功能实体信息存取实现类
 * 
 * @author dell,chaostone 2005-9-26
 */
public class MenuDaoHibernate extends BaseDaoHibernate implements MenuDao {

	public Menu get(Long menuId) {
		return (Menu) entityDao.get("Menu", menuId);
	}

	public Menu getByName(String name) {
		Map params = new HashMap();
		params.put("name", name);

		List rs = entityDao.searchHQLQuery("from Menu as menu where menu.name=:name", params, true);
		if (rs.isEmpty())
			return null;
		else
			return (Menu) rs.get(0);
	}

	public Menu getByCode(String code) {
		Map params = new HashMap();
		params.put("code", code);

		List rs = entityDao.searchHQLQuery("from Menu as menu where menu.code=:code", params, true);
		if (rs.isEmpty())
			return null;
		else
			return (Menu) rs.get(0);
	}

	public List get(Menu menu) {
		Criteria criteria = getSession().createCriteria(Menu.class);
		List criterions = CriterionUtils.getEntityCriterions(menu);
		for (Iterator iterator = criterions.iterator(); iterator.hasNext();) {
			Criterion one = (Criterion) iterator.next();
			criteria.add(one);
		}
		return criteria.list();
	}

	public List get(Long[] menuIds) {
		Criteria criteria = getSession().createCriteria(Menu.class);
		criteria.add(Restrictions.in("id", menuIds));
		return criteria.list();
	}

	public List getDescendants(String ancestorCode, int depth) {
		return getDescendants(ancestorCode, depth, null);
	}

	public List getActiveDescendants(String ancestorCode, int depth) {
		return getDescendants(ancestorCode, depth, Boolean.TRUE);
	}

	private List getDescendants(String ancestorCode, int depth, Boolean isEnabled) {
		String hql = "select  a from  Menu as a where" + "		a.code like :ancestorCode and"
				+ "		length(a.code) > :ancestorCodeLength and"
				+ "		(:depth=-1 or length(a.code)/2 <= :depth)";
		if (null != isEnabled) {
			hql += " and a.enabled=" + isEnabled;
		}
		hql += " order by a.code";
		Query query = getSession().createQuery(hql);
		query.setParameter("ancestorCode", ancestorCode + "%");
		query.setParameter("depth", new Integer(depth));
		query.setParameter("ancestorCodeLength", new Long(ancestorCode.length() + 1));
		query.setCacheable(true);
		return query.list();
	}

	public List getActiveChildren(String parentCode) {
		return getChildren(parentCode, Boolean.TRUE);
	}

	public List getChildren(String parentCode) {
		return getChildren(parentCode, null);
	}

	private List getChildren(String parentCode, Boolean isEnabled) {
		String hql = "select from  Menu as a where a.code like :parentCode"
				+ "		and length(a.code) - parentCodeLength<=2"
				+ "    	and length(a.code) >parentCodeLength";
		if (null != isEnabled) {
			hql += " and a.enabled=" + isEnabled;
		}
		hql += " order by a.code";
		Query query = getSession().createQuery(hql);
		query.setParameter("parentCode", parentCode + "%");
		query.setParameter("parentCodeLength", new Integer(parentCode.length() + 1));
		query.setCacheable(true);
		return query.list();
	}

	public void saveOrUpdate(Menu menu) {
		entityDao.saveOrUpdate(menu);
	}

}
