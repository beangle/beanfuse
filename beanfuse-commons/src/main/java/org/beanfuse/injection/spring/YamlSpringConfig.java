package org.beanfuse.injection.spring;

public class YamlSpringConfig {

	private YamlBeanDefinition[] beans;

	public YamlBeanDefinition[] getBeans() {
		return beans;
	}

	public void setBeans(YamlBeanDefinition[] beans) {
		this.beans = beans;
	}

}
