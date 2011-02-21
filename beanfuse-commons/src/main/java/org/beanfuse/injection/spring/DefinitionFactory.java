package org.beanfuse.injection.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.io.Resource;

public class DefinitionFactory {

	private DefinitionReader reader = new DefinitionReader();

	private Map beans = new HashMap();
	private Map yamlBeans = new HashMap();

	public DefinitionFactory() {
		super();
	}

	public DefinitionFactory(Resource resource) {
		reader.loadDefinitions(this, resource);
	}

	public void addResource(Resource resource) {
		reader.loadDefinitions(this, resource);
	}

	public void setReader(DefinitionReader reader) {
		this.reader = reader;
	}

	public YamlBeanDefinition getYamlBeanDefinition(String name) {
		return (YamlBeanDefinition) yamlBeans.get(name);
	}

	public BeanDefinition getBeanDefinition(String name) {
		return (BeanDefinition) beans.get(name);
	}

	public void registeYamlBean(String name, YamlBeanDefinition definition) {
		yamlBeans.put(name, definition);
	}

	public void registe(String name, BeanDefinition definition) {
		beans.put(name, definition);
	}

	public Map getBeans() {
		return beans;
	}

	public void setBeans(Map beans) {
		this.beans = beans;
	}

	public DefinitionReader getReader() {
		return reader;
	}

	public Map getYamlBeans() {
		return yamlBeans;
	}

	public void setYamlBeans(Map yamlBeans) {
		this.yamlBeans = yamlBeans;
	}

}
