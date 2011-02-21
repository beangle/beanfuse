package org.beanfuse.security.concurrent.category;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beanfuse.security.Authentication;
import org.beanfuse.security.AuthenticationException;
import org.beanfuse.security.User;
import org.beanfuse.security.UserCategory;
import org.beanfuse.security.UserDetails;
import org.beanfuse.security.concurrent.ConcurrentSessionControllerImpl;
import org.beanfuse.security.dao.SessionProfileProvider;
import org.beanfuse.security.online.CategoryProfile;
import org.beanfuse.security.online.OnlineActivity;
import org.beanfuse.security.online.OnlineActivityService;
import org.beanfuse.security.online.SessionProfile;
import org.beanfuse.security.ui.WebUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategorySessionControllerImpl extends ConcurrentSessionControllerImpl implements
		CategorySessionController {
	protected static Logger logger = LoggerFactory.getLogger(CategorySessionControllerImpl.class);

	/** 各类监测类型的监测数据 */
	protected Map profileMap = new HashMap();

	/** 用户类型配置提供服务 */
	protected SessionProfileProvider profileProvider;

	/** 记录session信息 */
	private OnlineActivityService onlineActivityService;

	/** 是否加载了用户配置 */
	private boolean loaded = false;

	public int getMax() {
		int max = 0;
		for (Iterator iter = profileMap.values().iterator(); iter.hasNext();) {
			OnlineProfile element = (OnlineProfile) iter.next();
			max += element.getCategoryProfile().getCapacity();
		}
		return max;
	}

	public int getMax(UserCategory category) {
		return ((OnlineProfile) profileMap.get(category)).getCategoryProfile().getCapacity();
	}

	public int getOnlineCount() {
		int online = 0;
		for (Iterator iter = profileMap.values().iterator(); iter.hasNext();) {
			OnlineProfile element = (OnlineProfile) iter.next();
			online += element.getOnline();
		}
		return online;
	}

	public int getOnlineCount(UserCategory category) {
		return ((OnlineProfile) profileMap.get(category)).getOnline();
	}

	public boolean isMaxArrived() {
		return getOnlineCount() >= getMax();
	}

	public boolean isMaxArrived(UserCategory category) {
		return getOnlineCount(category) >= getMax(category);
	}

	public CategoryProfile getCategoryProfile(UserCategory category) {
		return ((OnlineProfile) profileMap.get(category)).getCategoryProfile();
	}

	public List getOnlineProfiles() {
		return new ArrayList(profileMap.values());
	}

	public void setCategoryProfile(UserCategory category, CategoryProfile profile) {
		((OnlineProfile) profileMap.get(category)).setCategoryProfile(profile);
	}

	public void changeCategory(String sessionId, UserCategory category) {
		OnlineActivity record = (OnlineActivity) sessionRegistry.getOnlineActivity(sessionId);
		if (!record.getCategory().equals(category)) {
			((OnlineProfile) profileMap.get(record.getCategory())).decrease();
			((OnlineProfile) profileMap.get(category)).increase();
			record.setCategory(category);
		}
	}

	/**
	 * 注销会话
	 */
	public void removeAuthentication(String sessionId) {
		OnlineActivity info = sessionRegistry.remove(sessionId);
		if (null != info) {
			UserCategory category = info.getCategory();
			OnlineProfile profile = ((OnlineProfile) profileMap.get(category));
			profile.decrease();
			info.setRemark(info.getRemark());
			onlineActivityService.save(info);
		}
	}

	/**
	 * 根据用户身份确定单个用户的最大会话数
	 */
	protected int getMaximumSessionsForThisUser(Authentication auth) {
		checkProfiles();
		UserDetails details = (UserDetails) auth.getDetails();
		OnlineProfile profile = (OnlineProfile) profileMap.get(details.getCategory());
		if (null == profile) {
			logger.error("cannot find profile for {}", details.getCategory().getId());
			throw new RuntimeException("cannot find profile:" + details.getCategory().getName());
		}
		return profile.getCategoryProfile().getUserMaxSessions();
	}

	/**
	 * 注册用户
	 */
	public void registerAuthentication(Authentication authentication) {
		WebUserDetails details = (WebUserDetails) authentication.getDetails();
		OnlineProfile profile = (OnlineProfile) profileMap.get(details.getCategory());
		if (profile.hasCapacity()) {
			OnlineActivity existed = getOnlineActivity(details.getSessionId());
			sessionRegistry.register(details.getSessionId(), authentication);
			if (null == existed) {
				profile.increase();
			}
		} else {
			throw new AuthenticationException(Authentication.ERROR_OVERMAX);
		}
		calculateOnline();
	}

	public void calculateOnline() {
		int all = 0;
		for (Iterator iterator = profileMap.keySet().iterator(); iterator.hasNext();) {
			UserCategory category = (UserCategory) iterator.next();
			OnlineProfile p = (OnlineProfile) profileMap.get(category);
			all += p.getOnline();
		}
		if (all != sessionRegistry.count()) {
			synchronized (profileMap) {
				logger.info("start calculate...registry {} profile {}", new Integer(sessionRegistry
						.count()), new Integer(all));
				Map newProfileMap = new HashMap();
				for (Iterator iterator = profileMap.keySet().iterator(); iterator.hasNext();) {
					UserCategory category = (UserCategory) iterator.next();
					OnlineProfile profile = (OnlineProfile) profileMap.get(category);
					OnlineProfile newProfile = new OnlineProfile();
					newProfile.setCategoryProfile(profile.getCategoryProfile());
					newProfile.setOnline(0);
					newProfileMap.put(category, newProfile);
				}
				List infos = sessionRegistry.getOnlineActivities();
				for (Iterator iterator = infos.iterator(); iterator.hasNext();) {
					OnlineActivity info = (OnlineActivity) iterator.next();
					OnlineProfile profile = (OnlineProfile) newProfileMap.get(info.getCategory());
					profile.increase();
				}
				profileMap.putAll(newProfileMap);
			}
		}
	}

	public Collection getOnlineActivities(UserCategory category) {
		ArrayList activities = new ArrayList();
		for (Iterator iterator = sessionRegistry.getOnlineActivities().iterator(); iterator.hasNext();) {
			OnlineActivity record = (OnlineActivity) iterator.next();
			if (record.getCategory().equals(category)) {
				activities.add(record);
			}
		}
		return activities;
	}

	private void checkProfiles() {
		if (!loaded) {
			loadProfiles();
			loaded = !(profileMap.isEmpty());
		}
	}

	public void loadProfiles() {
		if (null == profileProvider) {
			return;
		}
		SessionProfile sessionProfile = profileProvider.getProfile();
		if (null != sessionProfile) {
			for (Iterator iterator = sessionProfile.getCategoryProfiles().values().iterator(); iterator
					.hasNext();) {
				OnlineProfile profile = new OnlineProfile((CategoryProfile) iterator.next());
				profileMap.put(profile.getCategoryProfile().getCategory(), profile);
				logger.info(profile.getCategoryProfile().toString());
			}
		}

	}
	public OnlineActivityService getOnlineActivityService() {
		return onlineActivityService;
	}

	public void setOnlineActivityService(OnlineActivityService onlineActivityService) {
		this.onlineActivityService = onlineActivityService;
	}

	public SessionProfileProvider getProfileProvider() {
		return profileProvider;
	}
	public void setProfileProvider(SessionProfileProvider profileProvider) {
		this.profileProvider = profileProvider;
	}

	public Collection getOnlineActivities(User user) {
		return sessionRegistry.getOnlineActivities(user.getName(), true);
	}

}
