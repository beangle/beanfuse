package org.beanfuse.webapp.security.action;

import java.sql.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.beanfuse.query.Condition;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.security.Authentication;
import org.beanfuse.security.User;
import org.beanfuse.security.UserCategory;
import org.beanfuse.security.menu.MenuProfile;
import org.beanfuse.security.menu.service.MenuAuthorityService;
import org.beanfuse.security.monitor.SecurityMonitor;

import com.opensymphony.xwork2.ActionContext;

/**
 * 加载用户登陆信息
 * 
 * @author duyaming
 * @author chaostone
 * 
 */
public class HomeAction extends SecurityAction {

	private MenuAuthorityService menuAuthorityService;

	protected SecurityMonitor securityMonitor;

	public String index() {
		User user = getUser();
		Long categoryId = getLong(Authentication.USER_CATEGORYID);
		Long curCategoryId = (Long) ActionContext.getContext().getSession().get(
				Authentication.USER_CATEGORYID);
		if (null == categoryId) {
			categoryId = curCategoryId;
		}
		if (!categoryId.equals(curCategoryId)) {
			UserCategory category = (UserCategory) entityService
					.get(UserCategory.class, categoryId);
			securityMonitor.getSessionController().changeCategory(
					ServletActionContext.getRequest().getSession().getId(), category);
			ActionContext.getContext().getSession().put(Authentication.USER_CATEGORYID, categoryId);
		}
		List dd = menuAuthorityService.getMenus(getMenuProfile(categoryId), user, 1, "");
		put("menus", dd);
		put("user", user);
		put("categories", user.getCategories());
		return forward();
	}

	/**
	 * 加载特定模块下的所有子模块
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String moduleList() {
		Long curCategory = (Long) ActionContext.getContext().getSession().get(
				Authentication.USER_CATEGORYID);
		List modulesTree = menuAuthorityService.getMenus(getMenuProfile(curCategory), getUser(),
				-1, get("parentCode"));
		put("moduleTree", modulesTree);
		return forward();
	}

	public String welcome() {
		put("date", new Date(System.currentTimeMillis()));
		return forward();
	}

	protected MenuProfile getMenuProfile(Long categoryId) {
		EntityQuery query = new EntityQuery(MenuProfile.class, "mp");
		query.add(new Condition("category.id=:categoryId", categoryId));
		query.setCacheable(true);
		List mps = (List) entityService.search(query);
		if (mps.isEmpty()) {
			return null;
		} else {
			return (MenuProfile) mps.get(0);
		}
	}

	public void setMenuAuthorityService(MenuAuthorityService menuAuthorityService) {
		this.menuAuthorityService = menuAuthorityService;
	}

	public void setSecurityMonitor(SecurityMonitor securityMonitor) {
		this.securityMonitor = securityMonitor;
	}

}
