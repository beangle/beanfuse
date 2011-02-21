package org.beanfuse.security.access.config;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigLoader {
	private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);

	private AccessConfig config;
	private static ConfigLoader singleton;

	public static ConfigLoader getInstance() {
		if (singleton == null) {
			singleton = new ConfigLoader();
		}
		return singleton;
	}

	private ConfigLoader() {
		super();
	}

	public synchronized AccessConfig getConfig() {
		if (null != config) {
			return config;
		}
		Properties props = new Properties();
		InputStream is = ConfigLoader.class.getResourceAsStream("/access.properties");
		if (null == is) {
			is = ConfigLoader.class
					.getResourceAsStream("/org/beanfuse/security/access/access-default.properties");
		}
		try {
			logger.debug("Loading config...");
			props.load(is);
			config = new AccessConfig();
			for (Iterator iterator = props.keySet().iterator(); iterator.hasNext();) {
				String name = (String) iterator.next();
				BeanUtils.copyProperty(config, name, props.getProperty(name));
			}
			logger.info(config.toString());
		} catch (Exception e) {
			logger.error("Exception", e);
			throw new RuntimeException(e.getMessage());
		}
		return config;
	}
}
