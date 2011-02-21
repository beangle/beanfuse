package org.beanfuse.security.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beanfuse.persist.impl.BaseServiceImpl;
import org.beanfuse.security.Group;
import org.beanfuse.security.Resource;
import org.beanfuse.security.User;
import org.springframework.beans.factory.InitializingBean;

public class AuthorityDecisionServiceImpl extends BaseServiceImpl implements
		AuthorityDecisionService, InitializingBean {
	/** [userId,groupIds[]] */
	protected Map userGroupIds = new HashMap();

	/** [groupId,parentGroupId] */
	protected Map parentGroupIds = new HashMap();

	/** 用户组权限 [groupId,actionIdSet] */
	protected Map authorities = new HashMap();

	/** 公开资源name */
	protected Set publicResources;

	/** 公有资源id */
	protected Set protectedResourceIds;

	/** 所有资源map [resourceName,resourceId] */
	protected Map resourceMap;

	/** 授权服务 */
	protected AuthorityService authorityService;

	/** 资源服务 */
	protected ResourceService resourceService;

	/** 用户服务 */
	protected UserService userService;

	public boolean isPublicResource(String resourceName) {
		return publicResources.contains(resourceName);
	}

	/**
	 * 资源是否被授权<br>
	 * 1)检查是否是属于公有资源<br>
	 * 2)检查用户组权限<br>
	 */
	public boolean isAuthorized(Long userId, String resourceName) {
		Long resourceId = (Long) resourceMap.get(resourceName);
		if (null == resourceId) {
			return false;
		}
		if (protectedResourceIds.contains(resourceId)) {
			return true;
		}
		Long[] groupIds = (Long[]) userGroupIds.get(userId);
		if (null == groupIds) {
			return false;
		}
		for (int i = 0; i < groupIds.length; i++) {
			if (isAuthorizedByGroup(groupIds[i], resourceId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断组内是否含有该资源
	 * 
	 * @param groupId
	 * @param resourceId
	 * @return
	 */
	private boolean isAuthorizedByGroup(Long groupId, Long resourceId) {
		Set actions = (Set) authorities.get(groupId);
		boolean success = (null != actions && actions.contains(resourceId));
		if (success) {
			return success;
		} else {
			Long parentId = (Long) parentGroupIds.get(groupId);
			if (null != parentId) {
				return isAuthorizedByGroup(parentId, resourceId);
			} else {
				return false;
			}
		}
	}

	public void registerAuthorities(Long userId) {
		User user = (User) entityDao.get(User.class, userId);
		Set groups = userService.getGroups(user);
		Long[] groupIds = new Long[groups.size()];
		int i = 0;
		for (Iterator iter = groups.iterator(); iter.hasNext();) {
			Group group = (Group) iter.next();
			registerGroupAuthorities(group);
			groupIds[i++] = group.getId();
		}
		userGroupIds.put(user.getId(), groupIds);
	}

	public void registerGroupAuthorities(Group group) {
		Set ra = authorityService.getResourceIds(group);
		authorities.put(group.getId(), ra);
		if (null != group.getParent()) {
			parentGroupIds.put(group.getId(), group.getParent().getId());
			registerGroupAuthorities(group.getParent());
		}
		logger.debug("add authorities for group:{} resource:{}", group.getName(), ra);
	}

	public void removeAuthorities(Long userId) {
		logger.debug("remove authorities for userId:{}", userId);
		if (null != userId) {
			userGroupIds.remove(userId);
		}
	}

	public void afterPropertiesSet() throws Exception {
		refreshResourceCache();
	}

	/**
	 * 加载三类资源
	 */
	public void refreshResourceCache() {
		resourceMap = new HashMap();
		publicResources = new HashSet();
		protectedResourceIds = new HashSet();
		List resources = resourceService.getResources();
		for (Iterator iter = resources.iterator(); iter.hasNext();) {
			Resource resource = (Resource) iter.next();
			switch (resource.getScope()) {
			case Resource.PUBLIC:
				publicResources.add(resource.getName());
				break;
			case Resource.PROTECTED:
				protectedResourceIds.add(resource.getId());
				break;
			}
			resourceMap.put(resource.getName(), resource.getId());
		}
	}

	public Set getPublicResources() {
		return publicResources;
	}

	public void setPublicResources(Set ignoreResources) {
		this.publicResources = ignoreResources;
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
}
