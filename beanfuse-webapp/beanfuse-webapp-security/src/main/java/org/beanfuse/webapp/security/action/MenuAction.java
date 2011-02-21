//$Id: MenuAction.java,v 1.1 2008-8-5 上午09:57:35 鄂州蚊子 Exp $
/*
 *
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 */
/********************************************************************************
 * @author 鄂州蚊子
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * 鄂州蚊子                 2008-8-5             Created
 *  
 ********************************************************************************/
package org.beanfuse.webapp.security.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.beanfuse.collection.Order;
import org.beanfuse.lang.SeqStringUtil;
import org.beanfuse.model.Entity;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.security.Resource;
import org.beanfuse.security.menu.Menu;
import org.beanfuse.security.menu.MenuProfile;
import org.beanfuse.security.menu.service.MenuService;

/**
 * 系统模块(菜单)管理响应类
 * 
 * @author 鄂州蚊子 2008-8-4
 */
public class MenuAction extends SecurityAction {

	private MenuService menuService;

	protected void indexSetting() {
		put("profiles", entityService.loadAll(MenuProfile.class));
	}

	protected void editSetting(Entity entity) {
		put("profiles", entityService.loadAll(MenuProfile.class));
		Menu menu = (Menu) entity;
		List resurces = entityService.loadAll(Resource.class);
		Set existResources = menu.getResources();
		if (null != resurces) {
			resurces.removeAll(existResources);
		}
		put("resources", resurces);
	}

	protected String saveAndForward(Entity entity) {
		Menu menu = (Menu) entity;
		try {
			List resources = new ArrayList();
			String resourceIdSeq = get("resourceIds");
			if (null != resourceIdSeq && resourceIdSeq.length() > 0) {
				resources = entityService.load(Resource.class, "id", SeqStringUtil
						.transformToLong(resourceIdSeq));
			}
			menu.getResources().clear();
			menu.getResources().addAll(resources);
			menuService.saveOrUpdate(menu);
		} catch (Exception e) {
			return forward(ERROR);
		}
		return redirect("search", "info.save.success");
	}

	/**
	 * 禁用或激活一个或多个模块
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String activate() {
		String menuIdSeq = get("menuIds");
		Long[] menuIds = SeqStringUtil.transformToLong(menuIdSeq);
		Boolean isActivate = getBoolean("isActivate");
		if (null != isActivate && Boolean.FALSE.equals(isActivate))
			menuService.updateState(menuIds, false);
		else
			menuService.updateState(menuIds, true);
		return redirect("search", "info.save.success");
	}

	/**
	 * 打印预览功能列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String preview() {
		EntityQuery query = new EntityQuery(Menu.class, "menu");
		populateConditions(query);
		query.addOrder(Order.parse("menu.code asc"));
		put("menus", entityService.search(query));
		query.setQueryStr(null);
		query.setOrders(Collections.EMPTY_LIST);
		query.setSelect("max(length(menu.code)/2)");
		List rs = (List) entityService.search(query);
		put("depth", rs.get(0));
		return forward();
	}

	public void setmenuService(MenuService menuService) {
		this.menuService = menuService;
	}
}
