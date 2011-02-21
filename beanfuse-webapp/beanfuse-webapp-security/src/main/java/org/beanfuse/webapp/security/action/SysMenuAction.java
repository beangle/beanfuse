package org.beanfuse.webapp.security.action;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.beanfuse.security.Group;
import org.beanfuse.security.Resource;
import org.beanfuse.security.User;
import org.beanfuse.security.menu.dao.MenuAuthorityDao;
import org.beanfuse.security.service.ResourceService;

public class SysMenuAction extends SecurityAction {
	ResourceService resourceService;

	MenuAuthorityDao menuAuthorityDao;

	public String index() {
		List<Resource> resources = resourceService.getResources();
		put("resources", resources);
		ServletActionContext.getResponse().setContentType("text/xml; charset=utf-8");
		return forward();
	}

	public String user() {
		String userName = get("uid");
		List<User> users = entityService.load(User.class, "name", userName);
		if (!users.isEmpty()) {
			User user = users.get(0);
			put("authorities", getMenuAuthorities(user));
		}
		return forward();
	}

	private Collection getMenuAuthorities(User user) {
		if (null == user)
			return Collections.EMPTY_LIST;
		Set menuAuthorities = new HashSet();
		if (null != user.getGroups()) {
			for (Iterator it = user.getAllGroups().iterator(); it.hasNext();) {
				List groupAuths = menuAuthorityDao.getMenuAuthorities(null,(Group) it.next(),-1,null);
				menuAuthorities.addAll(groupAuths);
			}
		}
		return menuAuthorities;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public void setMenuAuthorityDao(MenuAuthorityDao menuAuthorityDao) {
		this.menuAuthorityDao = menuAuthorityDao;
	}

}
