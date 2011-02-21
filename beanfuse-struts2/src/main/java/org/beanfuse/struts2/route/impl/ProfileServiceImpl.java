package org.beanfuse.struts2.route.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.beanfuse.struts2.route.ActionNameBuilder;
import org.beanfuse.struts2.route.Convention;
import org.beanfuse.struts2.route.Profile;
import org.beanfuse.struts2.route.ProfileService;
import org.beanfuse.struts2.route.ViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileServiceImpl implements ProfileService {

	private final static Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);

	private List<Profile> profiles = new ArrayList<Profile>();

	private Profile defaultProfile = new Profile();

	// 匹配缓存[String,Profile]
	private Map<String, Profile> cache = new HashMap();

	public ProfileServiceImpl() {
		super();
		defaultProfile.setViewExtension("ftl");
		defaultProfile.setDefaultMethod("index");
		defaultProfile.setUriStyle(ActionNameBuilder.SEO);
		defaultProfile.setUriExtension("action");
		defaultProfile.setActionSuffix("Action");
		defaultProfile.setViewPath("/pages/");
		defaultProfile.setPathStyle(ViewMapper.SIMPLE);
		loadProfiles();
	}

	public Profile getProfile(String className) {
		Profile matched = cache.get(className);
		if (null != matched) {
			return matched;
		}
		int index = -1;
		for (Profile profile : profiles) {
			int newIndex = profile.matchedIndex(className);
			if (newIndex > index) {
				matched = profile;
				index = newIndex;
			}
		}
		if (matched == null) {
			matched = defaultProfile;
		}
		synchronized (cache) {
			cache.put(className, matched);
		}
		logger.debug("{} match profile:{}", className, matched.getPattern());
		return matched;
	}

	public Profile getProfile(Class clazz) {
		return getProfile(clazz.getName());
	}

	private Properties getProperties(URL url) {
		logger.debug("loading {}", url);
		InputStream in = null;
		try {
			in = url.openStream();
			if (in != null) {
				Properties p = new Properties();
				p.load(in);
				return p;
			}
		} catch (IOException e) {
			logger.error("Error while loading " + url, e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException io) {
					logger.warn("Unable to close input stream", io);
				}
			}
		}
		return null;
	}

	/**
	 * 初始化配置
	 */
	public void loadProfiles() {
		try {
			URL convention_default = ProfileServiceImpl.class.getClassLoader().getResource(
					"convention-default.properties");
			Properties props = getProperties(convention_default);
			defaultProfile = populatProfile(props, "default");
			if (null == defaultProfile) {
				throw new RuntimeException("cannot find default profile !");
			}
			populateProfiles(props);
			Enumeration<URL> em = ProfileServiceImpl.class.getClassLoader().getResources(
					"META-INF/convention-route.properties");
			while (em.hasMoreElements()) {
				props = getProperties(em.nextElement());
				populateProfiles(props);
			}
			// 不要排序,让用户决定那个先选择
			// Collections.sort(profiles);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private void populateProfiles(Properties props) {
		int profileIndex = 0;
		while (true) {
			Profile profile = populatProfile(props, "profile" + profileIndex);
			if (null == profile) {
				break;
			} else {
				profiles.add(profile);
			}
			profileIndex++;
		}
	}

	private Profile populatProfile(Properties props, String name) {
		Profile profile = new Profile();
		profile.setName(name);
		String packageName = props.getProperty(profile.getName() + ".packageName");
		if (StringUtils.isEmpty(packageName))
			return null;
		profile.setPackageName(packageName);
		// 保证控制器前缀以点结束,默认和包路径一致
		String pattern = props.getProperty(profile.getName() + ".pattern", packageName + ".");
		if (!pattern.endsWith(".")) {
			pattern += ".";
		}
		profile.setPattern(pattern);
		profile.setActionSuffix(props.getProperty(profile.getName() + ".ctlPostfix", defaultProfile
				.getActionSuffix()));
		// 保证页面路径以/结束
		profile.setViewPath(props.getProperty(profile.getName() + ".viewPath", defaultProfile
				.getViewPath()));
		if (!profile.getViewPath().endsWith(Convention.separator + "")) {
			profile.setViewPath(profile.getViewPath() + Convention.separator);
		}
		populateAttr(profile, "viewExtension", props);
		populateAttr(profile, "defaultMethod", props);
		populateAttr(profile, "pathStyle", props);
		populateAttr(profile, "uriStyle", props);
		populateAttr(profile, "uriExtension", props);
		return profile;
	}

	private void populateAttr(Profile profile, String attr, Properties props) {
		Object value = props.getProperty(profile.getName() + "." + attr);
		try {
			if (null == value) {
				value = PropertyUtils.getProperty(defaultProfile, attr);
			}
			PropertyUtils.setProperty(profile, attr, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	public Profile getDefaultProfile() {
		return defaultProfile;
	}

	public void setDefaultProfile(Profile defaultProfile) {
		this.defaultProfile = defaultProfile;
	}

}
