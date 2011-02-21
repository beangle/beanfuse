package org.beanfuse.webapp.security.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.NoParameters;
import org.beanfuse.struts2.action.EntityDrivenAction;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.security.Authentication;
import org.beanfuse.security.AuthenticationException;
import org.beanfuse.security.AuthorityException;
import org.beanfuse.security.Resource;
import org.beanfuse.security.User;
import org.beanfuse.security.restriction.Param;
import org.beanfuse.security.restriction.Restriction;
import org.beanfuse.security.restriction.service.RestrictionService;
import org.beanfuse.security.service.AuthorityService;
import org.beanfuse.security.web.DefaultResourceExtractor;

import com.opensymphony.xwork2.ActionContext;

public class SecurityAction extends EntityDrivenAction implements NoParameters {

	protected AuthorityService authorityService;

	protected RestrictionService restrictionService;

	protected Resource getResource() {
		return authorityService.getResource(new DefaultResourceExtractor()
				.extract(ServletActionContext.getRequest()));
	}

	protected List getRestrictions() {
		final Map session = ActionContext.getContext().getSession();
		Map restrictionMap = (Map) session.get("security.restriction");
		if (null == restrictionMap) {
			restrictionMap = new HashMap();
			session.put("security.restriction", restrictionMap);
		}
		Resource resource = getResource();
		if (resource.getPatterns().isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		List realms = (List) restrictionMap.get(resource.getId());
		User user = getUser();
		if (null == realms) {
			realms = restrictionService.getRestrictions(user, resource);
			restrictionMap.put(resource.getId(), realms);
		}
		// 没有权限就报错
		if (realms.isEmpty()) {
			throw new AuthorityException(resource.getName());
		}
		return realms;
	}

	protected List getRestricitonValues(String name) {
		List restrictions = getRestrictions();
		Set values = new HashSet();
		boolean gotIt = false;
		for (Iterator iterator = restrictions.iterator(); iterator.hasNext();) {
			Restriction restiction = (Restriction) iterator.next();
			Param param = restiction.getParamGroup().getParam(name);
			if (null != param) {
				String value = restiction.getItem(param);
				if (null != value) {
					gotIt = true;
					values.addAll(restrictionService.select(restrictionService.getValues(param),
							restiction, param));
				}
			}
		}
		if (!gotIt) {
			List params = (List) entityService.load(Param.class, "name", name);
			if (params.isEmpty()) {
				throw new RuntimeException("bad pattern parameter named :" + name);
			}
			Param param = (Param) params.get(0);
			return restrictionService.getValues(param);
		} else {
			return new ArrayList(values);
		}
	}

	protected void applyRestriction(EntityQuery query) {
		Resource resource = getResource();
		restrictionService.apply(query, resource.getPatterns(), getRestrictions());
	}

	protected Long getUserId() {
		Long userId = (Long) ActionContext.getContext().getSession().get(Authentication.USERID);
		if (null == userId)
			throw new AuthenticationException();
		else
			return userId;
	}

	protected String getLoginName() {
		String loginName = (String) ActionContext.getContext().getSession().get(
				Authentication.LOGINNAME);
		if (null == loginName)
			throw new AuthenticationException();
		else
			return loginName;
	}

	protected String getFullName() {
		String fullname = (String) ActionContext.getContext().getSession().get(
				Authentication.FULLNAME);
		if (null == fullname)
			throw new AuthenticationException();
		else
			return fullname;
	}

	protected User getUser() {
		return (User) entityService.get(User.class, getUserId());
	}

	public Long getUserCategoryId() {
		return (Long) ActionContext.getContext().getSession().get(Authentication.USER_CATEGORYID);
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public void setRestrictionService(RestrictionService restrictionService) {
		this.restrictionService = restrictionService;
	}
}
