//$Id: AuthorityAction.java,v 1.6 2006/08/28 14:40:59 chaostone Exp $
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
 * chaostone            2005-10-09          Created
 * zq                   2007-09-18          修改或替换了下面所有的info()方法
 *  
 ********************************************************************************/

package org.beanfuse.webapp.security.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beanfuse.lang.SeqStringUtil;
import org.beanfuse.query.Condition;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.security.Authority;
import org.beanfuse.security.Group;
import org.beanfuse.security.Resource;
import org.beanfuse.security.User;
import org.beanfuse.security.menu.Menu;
import org.beanfuse.security.menu.MenuAuthority;
import org.beanfuse.security.menu.MenuProfile;
import org.beanfuse.security.menu.service.MenuAuthorityService;
import org.beanfuse.security.service.AuthorityDecisionService;
import org.beanfuse.security.service.GroupService;
import org.beanfuse.struts2.route.Action;

/**
 * 权限分配与管理响应类
 * 
 * @author chaostone 2005-10-9
 */
public class MenuAuthorityAction extends SecurityAction {

	private GroupService groupService;

	private MenuAuthorityService menuAuthorityService;

	private AuthorityDecisionService authorityDecisionService;

	/**
	 * 主页面
	 */
	public String index() {
		User manager = getUser();
		put("manager", manager);
		if (manager.isAdmin()) {
			put("allGroups", entityService.loadAll(Group.class));
		}
		return forward();
	}

	/**
	 * 根据菜单配置来分配权限
	 * 
	 * @author 鄂州蚊子
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String editAuthority() {
		Group ao = groupService.get(getLong("group.id"));
		User user = getUser();

		List categories = new ArrayList();
		categories.add(((Group) ao).getCategory());

		EntityQuery query = new EntityQuery(MenuProfile.class, "menuProfile");
		query.add(new Condition("menuProfile.category in(:categories)", categories));
		List menuProfiles = (List) entityService.search(query);
		put("menuProfiles", menuProfiles);

		Long menuProfileId = getLong("menuProfileId");
		if (null == menuProfileId && !menuProfiles.isEmpty()) {
			menuProfileId = ((MenuProfile) (menuProfiles.get(0))).getId();
		}
		if (null != menuProfileId) {
			MenuProfile menuProfile = (MenuProfile) entityService.get(MenuProfile.class,
					menuProfileId);
			List menus = null;
			Collection resources = null;
			if (user.isAdmin()) {
				menus = menuProfile.getMenus();
				resources = this.entityService.loadAll(Resource.class);
			} else {
				menus = menuAuthorityService.getMenus(menuProfile, user);
				resources = authorityService.getResources(user);
			}
			put("resources", new HashSet(resources));
			put("menus", menus);

			Collection aoMenus = null;
			Set aoResources = new HashSet();
			Map aoResourceAuthorityMap = new HashMap();
			List authorities = null;
			aoMenus = menuAuthorityService.getMenus(menuProfile, (Group) ao);
			authorities = authorityService.getAuthorities(ao);
			for (Iterator iter = authorities.iterator(); iter.hasNext();) {
				Authority authority = (Authority) iter.next();
				aoResources.add(authority.getResource());
				aoResourceAuthorityMap.put(authority.getResource().getId().toString(), authority
						.getId());
			}
			put("aoMenus", new HashSet(aoMenus));
			put("aoResources", aoResources);
			put("aoResourceAuthorityMap", aoResourceAuthorityMap);
		}
		put("ao", ao);
		return forward();
	}

	/**
	 * 显示权限操作提示界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String prompt() {
		return forward();
	}

	/**
	 * 保存模块级权限
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String save() {
		Group mao = groupService.get(getLong("group.id"));
		MenuProfile menuProfile = (MenuProfile) entityService.get(MenuProfile.class,
				getLong("menuProfileId"));

		Set newMenus = new HashSet(entityService.load(Menu.class, "id", SeqStringUtil
				.transformToLong(get("menuId"))));

		Set newResources = new HashSet(entityService.load(Resource.class, "id", SeqStringUtil
				.transformToLong(get("resourceId"))));

		// 管理员拥有的菜单权限和系统资源
		User manager = getUser();
		Set mngMenus = null;
		Set mngResources = new HashSet();
		if (manager.isAdmin()) {
			mngMenus = new HashSet(menuProfile.getMenus());
		} else {
			mngMenus = new HashSet(menuAuthorityService.getMenus(menuProfile, (User) manager));
		}
		for (Iterator iter = mngMenus.iterator(); iter.hasNext();) {
			Menu m = (Menu) iter.next();
			mngResources.addAll(m.getResources());
		}

		// 确定要删除的菜单和系统资源
		Set removedMenus = new HashSet();
		for (Iterator iter = mao.getMenuAuthorities().iterator(); iter.hasNext();) {
			MenuAuthority ma = (MenuAuthority) iter.next();
			if (mngMenus.contains(ma.getMenu()) && ma.getMenu().getProfile().equals(menuProfile)) {
				if (!newMenus.contains(ma.getMenu())) {
					removedMenus.add(ma);
				} else {
					newMenus.remove(ma.getMenu());
				}
			}
		}

		Set removedResources = new HashSet();
		for (Iterator iter = mao.getAuthorities().iterator(); iter.hasNext();) {
			Authority au = (Authority) iter.next();
			if (mngResources.contains(au.getResource())) {
				if (!newResources.contains(au.getResource())) {
					removedResources.add(au);
				} else {
					newResources.remove(au.getResource());
				}
			}
		}

		// 删除菜单和系统资源
		mao.getMenuAuthorities().removeAll(removedMenus);
		mao.getAuthorities().removeAll(removedResources);

		// 添加新的菜单和系统资源
		for (Iterator iterator = newMenus.iterator(); iterator.hasNext();) {
			Menu menu = (Menu) iterator.next();
			MenuAuthority authority = null;
			authority = new org.beanfuse.security.menu.model.MenuAuthority(mao, menu);
			mao.getMenuAuthorities().add(authority);
		}

		for (Iterator iterator = newResources.iterator(); iterator.hasNext();) {
			Resource resource = (Resource) iterator.next();
			Authority authority = null;
			authority = new org.beanfuse.security.model.Authority(mao, resource);
			mao.getAuthorities().add(authority);
		}

		entityService.saveOrUpdate(mao);
		authorityDecisionService.registerGroupAuthorities(mao);

		return redirect(new Action(getClass(), "editAuthority", "&group.id=" + mao.getId()
				+ "&menuProfileId=" + menuProfile.getId()), "info.save.success");
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public void setMenuAuthorityService(MenuAuthorityService menuAuthorityService) {
		this.menuAuthorityService = menuAuthorityService;
	}

	public AuthorityDecisionService getAuthorityDecisionService() {
		return authorityDecisionService;
	}

	public void setAuthorityDecisionService(AuthorityDecisionService authorityDecisionService) {
		this.authorityDecisionService = authorityDecisionService;
	}

}
