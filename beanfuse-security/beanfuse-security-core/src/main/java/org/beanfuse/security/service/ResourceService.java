//$Id: ResourceService.java 2008-8-3 下午06:05:22 chaostone Exp $
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
 * Name           Date          Description 
 * ============   ============  ============
 * chaostone      2008-8-3  Created
 *  
 ********************************************************************************/
package org.beanfuse.security.service;

import java.util.List;

import org.beanfuse.security.Resource;

public interface ResourceService {

	/**
	 * 更新资源状态
	 * 
	 * @param resourceIds
	 * @param isEnabled
	 */
	public void updateState(Long[] resourceIds, boolean isEnabled);

	/**
	 * 根据资源名称查找
	 * 
	 * @param name
	 * @return
	 */
	public Resource getResource(String name);

	/**
	 * 查询指定可见范围的资源
	 * 
	 * @param scope
	 * @return
	 */
	public List getResources(int scope);

	/**
	 * 加载所有激活的资源
	 * @return
	 */
	public List getResources();
}
