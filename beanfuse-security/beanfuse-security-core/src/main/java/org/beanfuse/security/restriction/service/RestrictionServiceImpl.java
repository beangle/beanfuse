//$Id: RetrictionServiceImpl.java,v 1.1 2007-10-14 下午04:41:35 chaostone Exp $
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
 * chaostone              2007-10-14         Created
 *  
 ********************************************************************************/

package org.beanfuse.security.restriction.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.beanfuse.persist.impl.BaseServiceImpl;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.security.Resource;
import org.beanfuse.security.User;
import org.beanfuse.security.dao.AuthorityDao;
import org.beanfuse.security.restriction.Param;
import org.beanfuse.security.restriction.Pattern;
import org.beanfuse.security.restriction.Restriction;
import org.beanfuse.security.restriction.RestrictionHolder;

public class RestrictionServiceImpl extends BaseServiceImpl implements RestrictionService {
	
	private AuthorityDao authorityDao;

	private RestrictionApply restrictionApply;

	/**
	 * 查询用户在指定模块的数据权限
	 */
	public List getRestrictions(final User user, final Resource resource) {
		List restrictions = new ArrayList();
		final Set paramGroups = new HashSet();
		for (Iterator iterator = resource.getPatterns().iterator(); iterator.hasNext();) {
			Pattern pattern = (Pattern) iterator.next();
			paramGroups.add(pattern.getParamGroup());
		}
		// 权限上的限制
		restrictions.addAll(getAuthorityRestrictions(user, resource));
		// 用户组自身限制
		for (Iterator iterator = user.getGroups().iterator(); iterator.hasNext();) {
			RestrictionHolder group = (RestrictionHolder) iterator.next();
			restrictions.addAll(group.getRestrictions());
		}
		// 用户自身限制
		RestrictionHolder userHolder = (RestrictionHolder) user;
		restrictions.addAll(userHolder.getRestrictions());
		// 模式过滤
		return (List) CollectionUtils.select(restrictions, new Predicate() {
			public boolean evaluate(Object obj) {
				Restriction restriciton = (Restriction) obj;
				if (restriciton.isEnabled() && paramGroups.contains(restriciton.getParamGroup()))
					return true;
				else
					return false;
			}
		});
	}

	private List getAuthorityRestrictions(User user, Resource resource) {
		EntityQuery query = new EntityQuery("select restriction from Authority r "
				+ "join r.group.users as user join r.restrictions as restriction"
				+ " where user=:user and r.resource=:resource" + " and restriction.enabled=true");
		Map params = new HashMap();
		params.put("user", user);
		params.put("resource", resource);
		query.setParams(params);
		return (List) entityService.search(query);
	}

	public List getValues(Param param) {
		if (null == param.getEditor())
			return Collections.EMPTY_LIST;
		EntityQuery query = new EntityQuery(param.getEditor().getSource());
		List rs = (List) entityService.search(query);
		logger.debug("param size {},source:{} ", new Integer(rs.size()), param.getEditor()
				.getSource());
		return rs;
	}

	public Set select(Collection values, List restrictions, Param param) {
		Set selected = new HashSet();
		for (Iterator iterator = restrictions.iterator(); iterator.hasNext();) {
			final Restriction restriction = (Restriction) iterator.next();
			selected.addAll(select(values, restriction, param));
		}
		return selected;
	}

	public Set select(Collection values, final Restriction restriction, Param param) {
		Set selected = new HashSet();
		String value = restriction.getItem(param);
		if (StringUtils.isNotEmpty(value)) {
			if (value.equals(Restriction.ALL)) {
				selected.addAll(values);
				return selected;
			}
			final Set paramValue = (Set) restriction.getValue(param);
			for (Iterator iterator = values.iterator(); iterator.hasNext();) {
				Object obj = (Object) iterator.next();
				try {
					if (paramValue.contains(PropertyUtils.getProperty(obj, param.getEditor()
							.getIdProperty()))) {
						selected.add(obj);
					}
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}
		return selected;
	}

	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}

	public void setRestrictionApply(RestrictionApply restrictionApply) {
		this.restrictionApply = restrictionApply;
	}

	public void apply(EntityQuery query, Collection patterns, Collection restrictions) {
		restrictionApply.apply(query, patterns, restrictions);
	}

	public void apply(EntityQuery query, Pattern pattern, Restriction restriction) {
		restrictionApply.apply(query, pattern, restriction);
	}

}
