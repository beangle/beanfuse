package org.beanfuse.webapp.security.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beanfuse.query.Condition;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.security.Group;
import org.beanfuse.security.Resource;
import org.beanfuse.security.User;
import org.beanfuse.security.UserCategory;
import org.beanfuse.security.menu.Menu;
import org.beanfuse.security.menu.MenuProfile;
import org.beanfuse.security.online.CategoryProfile;
import org.beanfuse.security.restriction.Param;
import org.beanfuse.security.restriction.Pattern;
import org.beanfuse.webapp.security.helper.GeneralDatasetProducer;
import org.jfree.data.general.DefaultPieDataset;

public class DashboardAction extends SecurityAction {

	public String index() {
		EntityQuery categoryQuery = new EntityQuery(UserCategory.class, "category");
		List categories = (List) entityService.search(categoryQuery);
		Map categoryMap = new HashMap();
		for (Iterator iterator = categories.iterator(); iterator.hasNext();) {
			UserCategory category = (UserCategory) iterator.next();
			categoryMap.put(category.getId().toString(), category);
		}
		put("categories", categoryMap);

		EntityQuery categoryProfileQuery = new EntityQuery(CategoryProfile.class, "categoryProfile");
		List categoryProfiles = (List) entityService.search(categoryProfileQuery);
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Iterator iterator = categoryProfiles.iterator(); iterator.hasNext();) {
			CategoryProfile categoryProfile = (CategoryProfile) iterator.next();
			dataset.setValue(categoryProfile.getCategory().getName(), categoryProfile.getCapacity());
		}
		put("categoryProfilesData", new GeneralDatasetProducer("categoryProfilesData", dataset));

		populateUserStat(categoryMap);

		EntityQuery groupQuery = new EntityQuery(Group.class, "group");
		groupQuery.setSelect("group.category.id,group.enabled,count(*)");
		groupQuery.groupBy("group.category.id,group.enabled");
		List groupStat = (List) entityService.search(groupQuery);
		put("groupStat", groupStat);

		List menuProfiles = (List) entityService.loadAll(MenuProfile.class);
		Map menuStats = new HashMap();
		for (Iterator iterator = menuProfiles.iterator(); iterator.hasNext();) {
			MenuProfile profile = (MenuProfile) iterator.next();
			EntityQuery menuQuery = new EntityQuery(Menu.class, "menu");
			menuQuery.add(new Condition("menu.profile=:profile", profile));
			menuQuery.setSelect("menu.enabled,count(*)");
			menuQuery.groupBy("enabled");
			menuStats.put(profile.getId().toString(), entityService.search(menuQuery));
		}
		put("menuProfiles", menuProfiles);
		put("menuStats", menuStats);

		EntityQuery resourceQuery = new EntityQuery(Resource.class, "resource");
		resourceQuery.setSelect("resource.enabled,count(*)");
		resourceQuery.groupBy("enabled");
		put("resourceStat", entityService.search(resourceQuery));

		EntityQuery patternQuery = new EntityQuery(Pattern.class, "pattern");
		patternQuery.setSelect("count(*)");
		put("patternStat", entityService.search(patternQuery));

		EntityQuery paramsQuery = new EntityQuery(Param.class, "param");
		paramsQuery.setSelect("count(*)");
		put("paramStat", entityService.search(paramsQuery));
		return forward();
	}

	private void populateUserStat(Map categoryMap) {
		EntityQuery userQuery = new EntityQuery(User.class, "user");
		userQuery.setSelect("user.defaultCategory.id,user.status,count(*)");
		userQuery.groupBy("user.defaultCategory.id,user.status");
		List userStat = (List) entityService.search(userQuery);
		DefaultPieDataset dataset2 = new DefaultPieDataset();
		for (Iterator iterator = userStat.iterator(); iterator.hasNext();) {
			Object[] data = (Object[]) iterator.next();
			dataset2.setValue(((UserCategory) categoryMap.get(data[0].toString())).getName()
					+ (data[1].toString().equals("1") ? "(激活)" : "(冻结)"), ((Number) data[2])
					.intValue());
		}
		put("userStatData", new GeneralDatasetProducer("userStatData", dataset2));
	}

}
