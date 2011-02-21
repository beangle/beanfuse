package org.beanfuse.webapp.security.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.struts2.action.helper.ParamHelper;
import org.beanfuse.bean.comparators.PropertyComparator;
import org.beanfuse.persist.EntityService;
import org.beanfuse.security.Authority;
import org.beanfuse.security.Group;
import org.beanfuse.security.User;
import org.beanfuse.security.restriction.Param;
import org.beanfuse.security.restriction.ParamGroup;
import org.beanfuse.security.restriction.Pattern;
import org.beanfuse.security.restriction.Restriction;
import org.beanfuse.security.restriction.RestrictionHolder;
import org.beanfuse.security.restriction.service.RestrictionService;

public class RestrictionHelper {

	public static final Map restrictionTypeMap = new HashMap();
	static {
		restrictionTypeMap.put("user", "org.beanfuse.security.restriction.UserRestriction");
		restrictionTypeMap.put("group", "org.beanfuse.security.restriction.GroupRestriction");
		restrictionTypeMap.put("authority",
				"org.beanfuse.security.restriction.AuthorityRestriction");
	}

	EntityService entityService;

	RestrictionService restrictionService;

	public RestrictionHelper(EntityService entityService) {
		super();
		this.entityService = entityService;
	}

	public RestrictionHolder getHolder() {
		Long restrictionHolderId = ParamHelper.getLong("restriction.holder.id");
		String restrictionType = ParamHelper.get("restrictionType");
		RestrictionHolder holer = null;
		if ("user".equals(restrictionType)) {
			holer = (RestrictionHolder) entityService.get(User.class, restrictionHolderId);
		} else if ("group".equals(restrictionType)) {
			holer = (RestrictionHolder) entityService.get(Group.class, restrictionHolderId);
		} else {
			holer = (RestrictionHolder) entityService.get(Authority.class, restrictionHolderId);
		}
		return holer;
	}

	/**
	 * 查看限制资源界面
	 */
	public void populateInfo(RestrictionHolder holder) {
		List restrictions = new ArrayList(holder.getRestrictions());
		Collections.sort(restrictions, new PropertyComparator("paramGroup.name"));
		Map paramMaps = new HashMap();
		for (Iterator iterator = restrictions.iterator(); iterator.hasNext();) {
			Restriction restriction = (Restriction) iterator.next();
			Map aoParams = new HashMap();
			for (Iterator iter = restriction.getParamGroup().getParams().iterator(); iter.hasNext();) {
				Param param = (Param) iter.next();
				String value = restriction.getItem(param);
				if (StringUtils.isNotEmpty(value)) {
					if (null == param.getEditor()) {
						aoParams.put(param.getName(), value);
					} else {
						aoParams.put(param.getName(), restrictionService.select(restrictionService
								.getValues(param), restriction, param));
					}
				}
			}
			paramMaps.put(restriction.getId().toString(), aoParams);
		}
		String forEdit = ParamHelper.get("forEdit");
		if (StringUtils.isNotEmpty(forEdit)) {
			List paramGroups = new ArrayList();
			if (holder instanceof Authority) {
				Authority au = (Authority) holder;
				for(Iterator iter=au.getResource().getPatterns().iterator();iter.hasNext();){
					Pattern pattern=(Pattern)iter.next();
					paramGroups.add(pattern.getParamGroup());
				}
			} else {
				paramGroups = entityService.loadAll(ParamGroup.class);
			}
			ParamHelper.put("paramGroups", paramGroups);
		}
		ParamHelper.put("paramMaps", paramMaps);
		ParamHelper.put("restrictions", restrictions);
	}

	public RestrictionService getRestrictionService() {
		return restrictionService;
	}

	public void setRestrictionService(RestrictionService restrictionService) {
		this.restrictionService = restrictionService;
	}

}
