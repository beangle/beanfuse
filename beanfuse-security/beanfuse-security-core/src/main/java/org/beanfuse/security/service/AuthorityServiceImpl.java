//$Id: AuthorityServiceImpl.java,v 1.6 2007/01/22 13:01:24 chaostone Exp $
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
 * chaostone            2005-9-26           rename some method's signature
 *  
 ********************************************************************************/

package org.beanfuse.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.beanfuse.entity.Model;
import org.beanfuse.persist.impl.BaseServiceImpl;
import org.beanfuse.security.Authority;
import org.beanfuse.security.Group;
import org.beanfuse.security.Resource;
import org.beanfuse.security.User;
import org.beanfuse.security.dao.AuthorityDao;

/**
 * 授权信息的服务实现类
 * 
 * @author dell,chaostone 2005-9-26
 */
public class AuthorityServiceImpl extends BaseServiceImpl implements AuthorityService {

	protected AuthorityDao authorityDao;

	protected UserService userService;

	protected ResourceService resourceService;

	public Resource getResource(String name) {
		return resourceService.getResource(name);
	}

	public List getAuthorities(User user) {
		if (null == user)
			return Collections.EMPTY_LIST;
		Map authorities = new HashMap();
		if (null != user.getGroups()) {
			for (Iterator it = user.getAllGroups().iterator(); it.hasNext();) {
				List groupAuths = authorityDao.getAuthorities(((Group) it.next()));
				for (Iterator iter = groupAuths.iterator(); iter.hasNext();) {
					Authority groupAuth = (Authority) iter.next();
					if (authorities.containsKey(groupAuth.getResource())) {
						Authority existed = (Authority) authorities.get(groupAuth.getResource());
						existed.merge(groupAuth);
					} else {
						authorities.put(groupAuth.getResource(), groupAuth);
					}
				}
			}
		}
		List authorityList = new ArrayList(authorities.values());
		Collections.sort(authorityList);
		return authorityList;
	}

	public Authority getAuthority(User user, Resource resource) {
		if ((null == user) || null == resource)
			return null;
		Authority au = null;
		Set groups = user.getAllGroups();
		if (null != groups)
			for (Iterator it = groups.iterator(); it.hasNext();) {
				Group one = (Group) it.next();
				Authority ar = getAuthority(one, resource);
				if (null == au) {
					au = ar;
				} else {
					au.merge(ar);
				}
			}
		return au;
	}

	public List getResources(User user) {
		Set resources = new HashSet();
		Map params = new HashMap();
		Set groups = user.getAllGroups();
		String hql = "select distinct m from Group as r join r.authorities as a"
				+ " join a.resource as m where  r.id = :groupId";
		params.clear();
		for (Iterator iter = groups.iterator(); iter.hasNext();) {
			Group group = (Group) iter.next();
			params.put("groupId", group.getId());
			resources.addAll(entityDao.searchHQLQuery(hql, params));
		}
		return new ArrayList(resources);
	}

	public List getAuthorities(Group group) {
		return authorityDao.getAuthorities(group);
	}

	public Authority getAuthority(Group group, Resource resource) {
		if (null == group || null == resource)
			return null;
		else
			return authorityDao.getAuthority(group, resource);
	}

	public List getResources(Group group) {
		return authorityDao.getResources(group);
	}

	public Set getResourceIds(Group group) {
		return authorityDao.getResourceIds(group);
	}

	public void copyAuthority(Group fromGroup, Collection toGroups) {
		List fromAuthorities = getAuthorities(fromGroup);
		List allAdded = new ArrayList();
		for (Iterator iter = toGroups.iterator(); iter.hasNext();) {
			Group toGroup = (Group) iter.next();
			List toAuthorities = getAuthorities(toGroup);
			Collection newAuthorities = CollectionUtils.subtract(fromAuthorities, toAuthorities);
			for (Iterator iterator = newAuthorities.iterator(); iterator.hasNext();) {
				Authority auth = (Authority) iterator.next();
				allAdded.add(auth.clone());
			}
		}
		entityService.saveOrUpdate(allAdded);
	}

	public void authorize(Group group, Set resources) {
		// 查找保留的权限
		Set reserved = new HashSet();
		for (Iterator iterator = group.getAuthorities().iterator(); iterator.hasNext();) {
			Authority authority = (Authority) iterator.next();
			if (resources.contains(authority.getResource())) {
				reserved.add(authority);
				resources.remove(authority.getResource());
			}
		}
		group.getAuthorities().clear();
		group.getAuthorities().addAll(reserved);
		// 新权限
		Authority model = null;
		try {
			model = (Authority) Model.newInstance(Authority.class);
		} catch (Exception e) {
			throw new RuntimeException("cannot init authroity by class:" + Authority.class);
		}
		model.setGroup(group);
		for (Iterator iter = resources.iterator(); iter.hasNext();) {
			Resource element = (Resource) iter.next();
			Authority authority = (Authority) model.clone();
			authority.setResource(element);
			group.getAuthorities().add(authority);
		}
		entityService.saveOrUpdate(group);
	}

	public void remove(Authority authority) {
		if (null != authority)
			entityService.remove(authority);
	}

	public void saveOrUpdate(Authority authority) {
		entityService.saveOrUpdate(authority);
	}

	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
