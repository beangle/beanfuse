//$Id: MenuDao.java,v 1.2 2006/11/28 03:22:36 chaostone Exp $
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
 * chaostone            2005-9-26           rename
 *  
 ********************************************************************************/

package org.beanfuse.security.menu.dao;

import java.util.List;

import org.beanfuse.security.menu.Menu;

/**
 * 系统功能实体信息存取类,@see <code>Menu</code>
 * 
 * @author dell,chaostone 2005-9-26
 */
public interface MenuDao {
	/**
	 * 查找指定id的系统模块
	 * 
	 * @param moduleId
	 * @return
	 */
	public Menu get(Long moduleId);

	/**
	 * 通过模块名称查找模块
	 * 
	 * @param name
	 * @return
	 */
	public Menu getByName(String name);

	/**
	 * 通过模块代码查找模块
	 * 
	 * @param name
	 * @return
	 */
	public Menu getByCode(String code);

	/**
	 * 返回基于代码、名称和英文名的模糊查询
	 * 
	 * @param module
	 * @return
	 */
	public List get(Menu module);

	/**
	 * 依据模块id数组中的指定返回模块列表
	 * 
	 * @param moduleIds
	 * @return
	 */
	public List get(Long[] moduleIds);

	/**
	 * 通过父亲代码获取子模块
	 * 
	 * @param parentId
	 * @return
	 */
	public List getChildren(String parentCode);

	/**
	 * 通过父亲代码获取可用的子模块
	 * 
	 * @param parentCode
	 * @return
	 */
	public List getActiveChildren(String parentCode);

	/**
	 * 通过父亲的模块查找制定深度的子模块
	 * 
	 * @param ancestorId
	 * @param depth
	 * @return
	 */
	public List getDescendants(String ancestorCode, int depth);

	/**
	 * 通过父亲的模块查找制定深度的可用子模块
	 * 
	 * @param ancestorId
	 * @param depth
	 * @return
	 */
	public List getActiveDescendants(String ancestorCode, int depth);

	/**
	 * 保存资源
	 * 
	 * @param menu
	 */
	public void saveOrUpdate(Menu menu);
}
