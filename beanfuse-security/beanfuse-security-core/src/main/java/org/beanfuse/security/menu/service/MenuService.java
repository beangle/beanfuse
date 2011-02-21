//$Id: MenuService.java,v 1.3 2006/11/28 03:22:36 chaostone Exp $
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
 * chaostone             2005-9-26          rename
 *  
 ********************************************************************************/

package org.beanfuse.security.menu.service;

import java.util.List;

import org.beanfuse.security.menu.Menu;
import org.beanfuse.security.menu.dao.MenuDao;

/**
 * 系统功能模块的服务接口
 * 
 * @author chaostone 2005-9-26
 */
public interface MenuService {

	/**
	 * 通过模块名称查找模块
	 * 
	 * @param name
	 * @return
	 */
	public Menu getByName(String name);

	/**
	 * 依据模块id数组中的指定返回模块列表
	 * 
	 * @param menuIds
	 * @return
	 */
	public List get(Long[] menuIds);

	/**
	 * 更新系统模块中的名称和优先级信息
	 * 
	 * @param menu
	 */
	public void saveOrUpdate(Menu menu);

	/**
	 * 通过祖先代码获取后代菜单列表
	 * 
	 * @param parentId
	 * @return
	 */
	public List getDescendants(String ancestorCode, int depth);

	/**
	 * 通过父亲的模块查找制定深度的子模块
	 * 
	 * @param parentId
	 * @param depth
	 * @return
	 */
	public List getChildren(String parentCode);

	/**
	 * 更新模块的状态信息
	 * 
	 * @param ids
	 * @param isEnabled
	 */
	public void updateState(Long[] ids, boolean isEnabled);

	/**
	 * 设置系统功能模块信息的存取对象
	 * 
	 * @param menuDao
	 */
	public void setMenuDao(MenuDao menuDao);

	public List getActiveChildren(String parentCode);

	public List getActiveDescendants(String ancestorCode, int depth);
}
