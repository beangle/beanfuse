//$Id: ResourceAction.java,v 1.7 2006/10/20 10:43:19 chaostone Exp $
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
 * chaostone             2005-10-9         Created
 *  
 ********************************************************************************/

package org.beanfuse.webapp.security.action;

import java.util.Collections;
import java.util.List;

import org.beanfuse.collection.Order;
import org.beanfuse.lang.SeqStringUtil;
import org.beanfuse.model.Entity;
import org.beanfuse.query.Condition;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.security.Resource;
import org.beanfuse.security.UserCategory;
import org.beanfuse.security.menu.Menu;
import org.beanfuse.security.restriction.Pattern;
import org.beanfuse.security.service.AuthorityDecisionService;
import org.beanfuse.security.service.ResourceService;

/**
 * 系统模块管理响应类
 * 
 * @author chaostone 2005-10-9
 */
public class ResourceAction extends SecurityAction {

	private ResourceService resourceService;

	private AuthorityDecisionService authorityDecisionService;

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
		Long[] resourceIds = SeqStringUtil.transformToLong(get("resourceIds"));
		Boolean enabled = getBoolean("enabled");
		if (null == enabled) {
			enabled = Boolean.FALSE;
		}
		resourceService.updateState(resourceIds, enabled.booleanValue());
		authorityDecisionService.refreshResourceCache();
		return redirect("search", "info.save.success");
	}

	protected void editSetting(Entity entity) {
		Resource resource = (Resource) entity;
		List patterns = entityService.loadAll(Pattern.class);
		patterns.removeAll(resource.getPatterns());
		put("patterns", patterns);
		put("categories", entityService.loadAll(UserCategory.class));
	}

	protected String saveAndForward(Entity entity) {
		Resource resource = (Resource) entity;
		if (null != resource) {
			List list = this.entityService.load(Resource.class, "name", resource.getName());
			int isUniqueFlag = 0;
			if (null != resource.getId()) {
				isUniqueFlag = 1;
			}
			if (null != list && list.size() > isUniqueFlag) {
				return redirect("edit", "error.notUnique");
			}
		}

		Long[] patternIds = SeqStringUtil.transformToLong(get("patternIds"));
		List patterns = Collections.EMPTY_LIST;
		if (null != patternIds) {
			patterns = entityService.load(Pattern.class, "id", patternIds);
		}
		resource.getPatterns().clear();
		resource.getPatterns().addAll(patterns);

		String categoryIds = get("categoryIds");
		List categories = entityService.load(Resource.class, "id", SeqStringUtil
				.transformToLong(categoryIds));
		resource.getCategories().clear();
		resource.getCategories().addAll(categories);

		entityService.saveOrUpdate(resource);
		authorityDecisionService.refreshResourceCache();

		logger.info("save resource success {}", resource.getTitle());
		return redirect("search", "info.save.success");
	}

	public String info() {
		Long entityId = getEntityId(getShortName());
		Entity entity = getModel(entityName, entityId);
		EntityQuery query = new EntityQuery(Menu.class, "menu");
		query.join("menu.resources", "r");
		query.add(new Condition("r.id=:resourceId", entity.getEntityId()));
		query.addOrder(Order.parse("menu.profile.id,menu.code"));
		List menus = (List) entityService.search(query);
		put(getShortName(), entity);
		put("menus", menus);
		put("categories", entityService.loadAll(UserCategory.class));
		return forward();
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public void setAuthorityDecisionService(AuthorityDecisionService authorityDecisionService) {
		this.authorityDecisionService = authorityDecisionService;
	}

}
