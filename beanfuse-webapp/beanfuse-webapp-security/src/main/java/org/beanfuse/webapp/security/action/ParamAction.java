//$Id: ParamAction.java 2008-8-6 上午09:39:04 chaostone Exp $
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
 * chaostone      2008-8-6  Created
 *  
 ********************************************************************************/
package org.beanfuse.webapp.security.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.collection.Order;
import org.beanfuse.lang.SeqStringUtil;
import org.beanfuse.model.Entity;
import org.beanfuse.query.Condition;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.security.restriction.Param;
import org.beanfuse.security.restriction.ParamGroup;

public class ParamAction extends SecurityAction {

	public void indexSetting() {
		put("paramGroups", entityService.loadAll(ParamGroup.class));
	}

	protected EntityQuery buildQuery() {
		EntityQuery query = new EntityQuery(entityName, getShortName());
		populateConditions(query);
		Long paramGroupId = getLong("paramGroup.id");
		if (null != paramGroupId) {
			query.join("param.paramGroups", "paramGroup");
			query.add(new Condition("paramGroup.id=:paramGroupId", paramGroupId));
		}
		query.addOrder(Order.parse(get("orderBy")));
		query.setLimit(getPageLimit());
		return query;
	}

	public String saveParamGroup() {
		ParamGroup group = (ParamGroup) populateEntity(ParamGroup.class, "paramGroup");
		entityService.saveOrUpdate(group);
		logger.info("save group with name {}", group.getName());
		return redirect("index", "info.save.success");
	}

	public String removeGroup() {
		Long groupId = getLong("paramGroupId");
		if (null != groupId) {
			ParamGroup group = (ParamGroup) entityService.get(ParamGroup.class, groupId);
			entityService.remove(group);
			logger.info("remove group with name {}", group.getName());
		}
		return redirect("index", "info.remove.success");
	}

	protected String saveAndForward(Entity entity) {
		String paramGroupIds = get("paramGroupIds");
		List paramGroups = new ArrayList();
		if (StringUtils.isNotBlank(paramGroupIds)) {
			paramGroups = entityService.load(ParamGroup.class, "id", SeqStringUtil
					.transformToLong(paramGroupIds));
		}
		System.out.println("*********************" + paramGroupIds);
		Param param = (Param) entity;
		param.getParamGroups().clear();
		param.getParamGroups().addAll(paramGroups);
		System.out.println("*********************" + param.getParamGroups());
		return super.saveAndForward(entity);
	}

	protected void editSetting(Entity entity) {
		List paramGroups = entityService.loadAll(ParamGroup.class);
		Param param = (Param) entity;
		paramGroups.removeAll(param.getParamGroups());
		put("paramGroups", paramGroups);
		super.editSetting(entity);
	}

}
