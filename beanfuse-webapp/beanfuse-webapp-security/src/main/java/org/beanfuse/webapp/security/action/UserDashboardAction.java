package org.beanfuse.webapp.security.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beanfuse.collection.Order;
import org.beanfuse.collection.page.PageLimit;
import org.beanfuse.collection.page.SinglePage;
import org.beanfuse.query.Condition;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.security.Group;
import org.beanfuse.security.User;
import org.beanfuse.security.menu.MenuProfile;
import org.beanfuse.security.menu.service.MenuAuthorityService;
import org.beanfuse.security.monitor.SecurityMonitor;
import org.beanfuse.security.online.SessionActivity;
import org.beanfuse.security.restriction.RestrictionHolder;
import org.beanfuse.security.service.UserService;
import org.beanfuse.struts2.route.Action;
import org.beanfuse.webapp.security.helper.RestrictionHelper;

public class UserDashboardAction extends SecurityAction {

	private MenuAuthorityService menuAuthorityService;

	private SecurityMonitor securityMonitor;

	private UserService userService;

	protected void indexSetting() {
		User user = getUser();
		buildDashboard(user);
	}

	private void buildDashboard(User user) {
		put("user", user);
		populateMenus(user);
		populateSessionActivities(user);
		populateOnlineActivities(user);
		RestrictionHelper helper = new RestrictionHelper(entityService);
		helper.setRestrictionService(restrictionService);
		helper.populateInfo((RestrictionHolder) user);
	}

	public String dashboard() {
		Long userId = getLong("user.id");
		User managed = (User) entityService.get(User.class, userId);
		User manager = getUser();
		if (userService.isManagedBy(manager, managed)) {
			buildDashboard(managed);
			return forward();
		} else {
			return forward(ERROR);
		}
	}

	private void populateOnlineActivities(User user) {
		Collection onlineActivities = securityMonitor.getSessionController().getOnlineActivities(
				user);
		put("onlineActivities", onlineActivities);
	}

	private void populateSessionActivities(User user) {
		EntityQuery onlineQuery = new EntityQuery(SessionActivity.class, "sessionActivity");
		onlineQuery.add(new Condition("sessionActivity.name =:name", user.getName()));
		onlineQuery.addOrder(Order.asc("sessionActivity.loginAt desc"));
		onlineQuery.setLimit(new PageLimit(1,5));
		SinglePage page = (SinglePage) entityService.search(onlineQuery);
		put("sessionActivities", page.getItems());
	}

	private void populateMenus(User user) {
		EntityQuery query = new EntityQuery(MenuProfile.class, "menuProfile");
		query.add(new Condition("menuProfile.category in(:categories)", user.getCategories()));
		List menuProfiles = (List) entityService.search(query);
		put("menuProfiles", menuProfiles);

		Long menuProfileId = getLong("menuProfileId");
		if (null == menuProfileId && !menuProfiles.isEmpty()) {
			menuProfileId = ((MenuProfile) (menuProfiles.get(0))).getId();
		}
		if (null != menuProfileId) {
			MenuProfile menuProfile = (MenuProfile) entityService.get(MenuProfile.class,
					menuProfileId);
			List menus = menuAuthorityService.getMenus(menuProfile, user);
			Set resources = new HashSet(authorityService.getResources(user));
			Map groupMap = new HashMap();
			Map groupMenusMap = new HashMap();

			for (Iterator iterator = user.getGroups().iterator(); iterator.hasNext();) {
				Group group = (Group) iterator.next();
				groupMap.put(group.getId().toString(), group);
				groupMenusMap.put(group.getId().toString(), menuAuthorityService.getMenus(
						menuProfile, group));
			}
			put("menus", menus);
			put("groupMap", groupMap);
			put("groupMenusMap", groupMenusMap);
			put("resources", resources);
		}
	}

	public String restrictionInfo() {
		User user = getUser();
		return forward(new Action(RestrictionAction.class, "info", "&restriction.holder.id="
				+ user.getId() + "&restrictionType=user"));
	}

	public void setMenuAuthorityService(MenuAuthorityService menuAuthorityService) {
		this.menuAuthorityService = menuAuthorityService;
	}

	public void setSecurityMonitor(SecurityMonitor securityMonitor) {
		this.securityMonitor = securityMonitor;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
