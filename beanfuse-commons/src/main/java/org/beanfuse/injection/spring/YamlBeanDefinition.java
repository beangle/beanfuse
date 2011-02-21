package org.beanfuse.injection.spring;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.CharUtils;

public class YamlBeanDefinition {

	private String id;

	private String name;

	private Pattern pattern;

	private Map properties;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
		for (int i = 0; i < id.length(); i++) {
			if (!CharUtils.isAsciiAlphanumeric(id.charAt(i))
					&& id.charAt(i) != '_') {
				this.pattern = Pattern.compile(id);
				break;
			}
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map getProperties() {
		return properties;
	}

	public void setProperties(Map properties) {
		this.properties = properties;
	}

	public boolean matched(String name) {
		if (null == pattern) {
			return id.equals(name);
		} else {
			return pattern.matcher(name).matches();
		}
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

}
