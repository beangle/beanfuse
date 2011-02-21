//$Id:PopulateHelper.java 2009-1-20 下午08:29:30 chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.struts2.action.helper;

import java.util.Map;

import org.beanfuse.entity.Model;
import org.beanfuse.entity.types.EntityType;
import org.beanfuse.model.EntityUtils;

public class PopulateHelper {

	/**
	 * 将request中的参数设置到clazz对应的bean。
	 * 
	 * @param request
	 * @param clazz
	 * @param name
	 * @return
	 */
	public static Object populate(Class clazz, String name) {
		EntityType type = null;
		if (clazz.isInterface()) {
			type = Model.getEntityType(clazz.getName());
		} else {
			type = Model.getEntityType(clazz);
		}
		return populate(type.newInstance(), type.getEntityName(), name);
	}

	public static Object populate(Class clazz) {
		EntityType type = null;
		if (clazz.isInterface()) {
			type = Model.getEntityType(clazz.getName());
		} else {
			type = Model.getEntityType(clazz);
		}
		String entityName = type.getEntityName();
		return populate(type.newInstance(), entityName, EntityUtils.getCommandName(entityName));
	}

	public static Object populate(String entityName) {
		EntityType type = Model.getEntityType(entityName);
		return populate(type.newInstance(), type.getEntityName(), EntityUtils
				.getCommandName(entityName));
	}

	public static Object populate(String entityName, String name) {
		EntityType type = Model.getEntityType(entityName);
		return populate(type.newInstance(), type.getEntityName(), name);
	}

	public static Object populate(Object obj, String entityName, String name) {
		Map params = ParamHelper.getParams(name);
		return Model.getPopulator().populate(params, obj, entityName);
	}
}
