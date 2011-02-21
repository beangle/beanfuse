//$Id: Restriction.java,v 1.1 2007-10-13 下午01:45:19 chaostone Exp $
/*
 *
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone              2007-10-13         Created
 *  
 ********************************************************************************/

package org.beanfuse.security.restriction;

import java.util.Map;

import org.beanfuse.model.LongIdEntity;

/**
 * 资源访问限制
 * 
 * @author chaostone
 * 
 */
public interface Restriction extends LongIdEntity, Cloneable {
	public static final String ALL = "*";
	/**
	 * 数据权限持有者
	 * 
	 * @return
	 */
	public RestrictionHolder getHolder();

	/**
	 * 设置数据权限持有者
	 * 
	 * @param holder
	 */
	public void setHolder(RestrictionHolder holder);

	public ParamGroup getParamGroup();

	public void setParamGroup(ParamGroup paramGroup);

	/**
	 * 限制项
	 * 
	 * @return
	 */
	public Map getItems();

	public void setItems(Map items);

	public String getItem(String paramName);

	public String getItem(Param param);

	public void setItem(Param param, String text);

	/**
	 * 是否有效
	 * 
	 * @return
	 */
	public boolean isEnabled();

	/**
	 * 是否有效
	 * 
	 * @param isEnabled
	 */
	public void setEnabled(boolean isEnabled);

	public Object getValue(Param param);
}
