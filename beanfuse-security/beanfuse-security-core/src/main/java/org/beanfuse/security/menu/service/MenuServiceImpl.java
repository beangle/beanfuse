//$Id: MenuServiceImpl.java,v 1.3 2006/11/28 03:22:36 chaostone Exp $
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
 * chaostone            2005-9-26           rename
 *  
 ********************************************************************************/

package org.beanfuse.security.menu.service;

import java.util.Collections;
import java.util.List;

import org.beanfuse.persist.impl.BaseServiceImpl;
import org.beanfuse.security.menu.Menu;
import org.beanfuse.security.menu.dao.MenuDao;
import org.beanfuse.security.menu.service.MenuService;

/**
 * 系统模块服务实现类
 * 
 * @author dell,chaostone 2005-9-26
 */
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {
	private MenuDao menuDao = null;

	public List getDescendants(String ancestorCode, int depth) {
		if (null == ancestorCode)
			return null;
		else
			return menuDao.getDescendants(ancestorCode, depth);
	}

	public List getActiveChildren(String parentCode) {
		return menuDao.getActiveChildren(parentCode);
	}

	public List getActiveDescendants(String ancestorCode, int depth) {
		return menuDao.getActiveDescendants(ancestorCode, depth);
	}

	public List getChildren(String parentCode) {
		return menuDao.getChildren(parentCode);
	}

	public Menu get(Long menuId) {
		return menuDao.get(menuId);
	}

	public Menu getByName(String name) {
		return menuDao.getByName(name);
	}

	public Menu getByCode(String code) {
		return menuDao.getByCode(code);
	}

	public List get(Menu menu) {
		return menuDao.get(menu);
	}

	public List get(Long[] menuIds) {
		if (null == menuIds || menuIds.length < 1)
			return Collections.EMPTY_LIST;
		else
			return menuDao.get(menuIds);
	}

	public void saveOrUpdate(Menu menu) {
		menuDao.saveOrUpdate(menu);
	}

	public void updateState(Long[] ids, boolean isEnabled) {
		if (null == ids || ids.length < 1)
			return;
		else {
			for (int i = 0; i < ids.length; i++) {
				Menu menu = get(ids[i]);
				if (null != menu) {
					menu.setEnabled(isEnabled);
					saveOrUpdate(menu);
				}
			}
		}
	}

	// public Set getFunctionPoints(Menu menu) {
	// String hql = "select distinct f.name from Menu r join
	// r.functionPoints f where r.code like :code";
	// Map params = new HashMap();
	// params.put("code", menu.getCode() + "%");
	// return new HashSet(entityDao.searchHQLQuery(hql, params));
	// }

	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}
}
