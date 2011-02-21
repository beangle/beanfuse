//$Id: ResourceServiceImpl.java 2008-8-3 下午06:06:51 chaostone Exp $
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

import org.beanfuse.persist.impl.BaseServiceImpl;
import org.beanfuse.query.Condition;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.security.Resource;

public class ResourceServiceImpl extends BaseServiceImpl implements ResourceService {

	public void updateState(Long[] resourceIds, boolean isEnabled) {
		EntityQuery query = new EntityQuery(Resource.class, "resource");
		query.add(new Condition("resource.id in (:ids)", resourceIds));
		List<Resource> resources = (List) entityDao.search(query);
		for (Resource resource : resources) {
			resource.setEnabled(isEnabled);
		}
		entityDao.saveOrUpdate(resources);
	}

	public Resource getResource(String name) {
		EntityQuery query = new EntityQuery(Resource.class, "resource");
		query.add(new Condition("resource.name=:name", name));
		query.setCacheable(true);
		List resources = (List) entityDao.search(query);
		if (resources.isEmpty()) {
			return null;
		} else {
			return (Resource) resources.get(0);
		}
	}

	public List getResources(int scope) {
		EntityQuery query = new EntityQuery(Resource.class, "resource");
		query.add(new Condition("resource.scope=:scope and resource.enabled=true", new Integer(
				scope)));
		return entityDao.search(query);
	}

	public List getResources() {
		EntityQuery query = new EntityQuery(Resource.class, "resource");
		query.add(new Condition("resource.enabled=true"));
		return entityDao.search(query);
	}

}
