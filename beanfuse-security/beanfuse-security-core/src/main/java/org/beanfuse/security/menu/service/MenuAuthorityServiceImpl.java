//$Id: AuthorityServiceImpl.java,v 1.6 2007/01/22 13:01:24 chaostone Exp $
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

package org.beanfuse.security.menu.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.beanfuse.entity.Model;
import org.beanfuse.persist.impl.BaseServiceImpl;
import org.beanfuse.security.Authority;
import org.beanfuse.security.Group;
import org.beanfuse.security.Resource;
import org.beanfuse.security.User;
import org.beanfuse.security.menu.Menu;
import org.beanfuse.security.menu.MenuAuthority;
import org.beanfuse.security.menu.MenuProfile;
import org.beanfuse.security.menu.dao.MenuAuthorityDao;
import org.beanfuse.security.service.UserService;

/**
 * 授权信息的服务实现类
 * 
 * @author dell,chaostone 2005-9-26
 */
public class MenuAuthorityServiceImpl extends BaseServiceImpl implements MenuAuthorityService {

	protected MenuAuthorityDao menuAuthorityDao;

	protected UserService userService;

	public List getMenuAuthorities(MenuProfile profile, User user) {
		return getMenuAuthorities(profile, user, -1, null);
	}

	public List getMenuAuthorities(MenuProfile profile, User user, int depth, String ancestorCode) {
		if (null == user)
			return Collections.EMPTY_LIST;
		Set menuAuthorities = new HashSet();
		if (null != user.getGroups()) {
			for (Iterator it = user.getGroups().iterator(); it.hasNext();) {
				menuAuthorities.addAll(menuAuthorityDao.getMenuAuthorities(profile, ((Group) it
						.next()), depth, ancestorCode));
			}
		}
		List authorityList = new ArrayList(menuAuthorities);
		Collections.sort(authorityList);
		return authorityList;
	}

	public List getMenuAuthorities(MenuProfile profile, Group group) {
		return getMenuAuthorities(profile, group, -1);
	}

	public List getMenuAuthorities(MenuProfile profile, Group group, int depth) {
		return getMenuAuthorities(profile, group, depth, null);
	}

	public List getMenuAuthorities(MenuProfile profile, Group group, int depth, String ancestorCode) {
		if (null == group)
			return Collections.EMPTY_LIST;
		List groupAuthorities = menuAuthorityDao.getMenuAuthorities(profile, group, depth,
				ancestorCode);
		return groupAuthorities;
	}

	public MenuAuthority getMenuAuthority(Group group, Menu menu) {
		if (group == null || null == menu)
			return null;
		else
			return menuAuthorityDao.getMenuAuthority(group, menu);
	}

	public List getMenus(MenuProfile profile, User user) {
		return getMenus(profile, user, -1, null);
	}

	public List getMenus(MenuProfile profile, User user, int depth, String ancestorCode) {
		Set modules = new HashSet();
		Set groups = userService.getGroups(user);
		for (Iterator iterator = groups.iterator(); iterator.hasNext();) {
			Group group = (Group) iterator.next();
			modules.addAll(menuAuthorityDao.getMenus(profile, group, depth, ancestorCode));
		}
		if (null == modules)
			return Collections.EMPTY_LIST;
		List moduleList = new ArrayList(modules);
		Collections.sort(moduleList);
		return moduleList;
	}

	public List getMenus(MenuProfile profile, Group group) {
		return menuAuthorityDao.getMenus(profile, group, 0, null);
	}

	/**
	 * @deprecated
	 */
	public void copyAuthority(MenuProfile profile, Group fromGroup, Collection toGroups) {
		List fromAuthorities = getMenuAuthorities(profile, fromGroup);
		List allAdded = new ArrayList();
		for (Iterator iter = toGroups.iterator(); iter.hasNext();) {
			Group toGroup = (Group) iter.next();
			List toAuthorities = getMenuAuthorities(profile, toGroup);
			Collection newAuthorities = CollectionUtils.subtract(fromAuthorities, toAuthorities);
			for (Iterator iterator = newAuthorities.iterator(); iterator.hasNext();) {
				// GroupAuthority auth = (GroupAuthority) iterator.next();
				// allAdded.add(auth.clone());
			}
		}
		entityService.saveOrUpdate(allAdded);
	}

	public void authorize(Group group, Set resources) {
		// 查找保留的权限
		Set reserved = new HashSet();
		for (Iterator iterator = group.getAuthorities().iterator(); iterator.hasNext();) {
			Authority authority = (Authority) iterator.next();
			if (resources.contains(authority.getResource())) {
				reserved.add(authority);
				resources.remove(authority.getResource());
			}
		}
		group.getAuthorities().clear();
		group.getAuthorities().addAll(reserved);
		// 新权限
		Authority model = null;
		try {
			model = (Authority) Model.newInstance(Authority.class);
		} catch (Exception e) {
			throw new RuntimeException("cannot init authroity by class:" + Authority.class);
		}
		model.setGroup(group);
		for (Iterator iter = resources.iterator(); iter.hasNext();) {
			Resource element = (Resource) iter.next();
			Authority authority = null;// (Authority) model.clone();
			authority.setResource(element);
			group.getAuthorities().add(authority);
		}
		entityService.saveOrUpdate(group);
	}

	/**
	 * 从module列表中拼成一个功能点名称集合
	 * 
	 * @param modules
	 * @return
	 */
	public static Set distillResources(Collection menus) {
		if (null == menus || menus.size() < 0)
			return Collections.EMPTY_SET;
		Set actionNames = new HashSet();
		for (Iterator it = menus.iterator(); it.hasNext();) {
			Set actions = ((Menu) it.next()).getResources();
			if (null != actions) {
				for (Iterator ot = actions.iterator(); ot.hasNext();) {
					actionNames.add(((Resource) ot.next()).getName());
				}
			}
		}
		return actionNames;
	}

	public void saveOrUpdate(MenuAuthority o) {
		entityService.saveOrUpdate(o);
	}

	public void setMenuAuthorityDao(MenuAuthorityDao menuAuthorityDao) {
		this.menuAuthorityDao = menuAuthorityDao;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
