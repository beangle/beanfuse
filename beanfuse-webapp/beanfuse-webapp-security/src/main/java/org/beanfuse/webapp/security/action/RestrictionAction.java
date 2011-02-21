//$Id: RestrictionAction.java,v 1.1 2008-8-8 下午01:54:09 鄂州蚊子 Exp $
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
 * 鄂州蚊子                 2008-8-8             Created
 *  
 ********************************************************************************/
package org.beanfuse.webapp.security.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.security.restriction.Param;
import org.beanfuse.security.restriction.ParamGroup;
import org.beanfuse.security.restriction.Restriction;
import org.beanfuse.security.restriction.RestrictionHolder;
import org.beanfuse.webapp.security.helper.RestrictionHelper;

public class RestrictionAction extends SecurityAction {

	public String tip() {
		return forward();
	}

	/**
	 * 删除数据限制权限
	 */
	public String remove() {
		Restriction restriction = getRestriction();
		RestrictionHolder holer = new RestrictionHelper(entityService).getHolder();
		holer.getRestrictions().remove(restriction);
		entityService.saveOrUpdate(holer);
		return redirect("info", "info.delete.success");
	}

	/**
	 * 查看限制资源界面
	 */
	public String info() {
		RestrictionHelper helper = new RestrictionHelper(entityService);
		helper.setRestrictionService(restrictionService);
		helper.populateInfo(helper.getHolder());
		return forward();
	}

	public String save() {
		Restriction restriction = getRestriction();
		for (Iterator iter = restriction.getParamGroup().getParams().iterator(); iter.hasNext();) {
			Param param = (Param) iter.next();
			String value = get(param.getName());
			if (StringUtils.isEmpty(value)) {
				value = null;
			}
			restriction.setItem(param, value);
		}
		RestrictionHolder holder = new RestrictionHelper(entityService).getHolder();
		if (restriction.isVO()) {
			holder.getRestrictions().add(restriction);
			entityService.saveOrUpdate(holder);
		} else {
			entityService.saveOrUpdate((String) RestrictionHelper.restrictionTypeMap
					.get(get("restrictionType")), restriction);
		}
		return redirect("info", "info.save.success");
	}

	/**
	 * 编辑权限<br>
	 * TODO 限制非超级管理员的管理范围
	 */
	public String edit() {
		// 取得各参数的值
		Restriction restriction = getRestriction();
		Map mngParams = new HashMap();
		Map aoParams = new HashMap();
		for (Iterator iter = restriction.getParamGroup().getParams().iterator(); iter.hasNext();) {
			Param param = (Param) iter.next();
			List mngParam = restrictionService.getValues(param);
			mngParams.put(param.getName(), mngParam);
			if (null == param.getEditor()) {
				aoParams.put(param.getName(), restriction.getItem(param));
			} else {
				Set aoParam = restrictionService.select(restrictionService.getValues(param),
						restriction, param);
				aoParams.put(param.getName(), aoParam);
			}
		}
		put("mngParams", mngParams);
		put("aoParams", aoParams);
		put("restriction", restriction);
		return forward();
	}

	private Restriction getRestriction() {
		Long restrictionId = getLong("restriction.id");
		Restriction restriction = null;
		String entityName = (String) RestrictionHelper.restrictionTypeMap
				.get(get("restrictionType"));
		if (null == restrictionId) {
			restriction = new org.beanfuse.security.restriction.model.Restriction();
		} else {
			restriction = (Restriction) entityService.get(entityName, restrictionId);
		}
		Map params = getParams("restriction");
		populate(params, restriction, entityName);
		if (null == restrictionId) {
			restriction.setParamGroup((ParamGroup) entityService.get(ParamGroup.class, restriction
					.getParamGroup().getId()));
		}
		return restriction;
	}
}
