package org.beanfuse.injection.spring;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ReflectionUtils;

public class DependencyInjection implements BeanFactoryPostProcessor {

	private String dependencyLocation = "/dependency.yml";

	private static final Logger logger = LoggerFactory
			.getLogger(DependencyInjection.class);

	public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) {
		DefinitionFactory definitionFactory = new DefinitionFactory(
				new ClassPathResource(dependencyLocation));
		// 找到所有的spring的bean作为匹配目标对象
		String[] names = factory.getBeanDefinitionNames();
		for (Iterator iter = definitionFactory.getBeans().keySet().iterator(); iter
				.hasNext();) {
			String beanName = (String) iter.next();
			// yamlbean定义
			YamlBeanDefinition yamlBean = definitionFactory
					.getYamlBeanDefinition(beanName);
			// 对应的spring格式的bean定义
			BeanDefinition dependecyDefinition = (BeanDefinition) definitionFactory
					.getBeanDefinition(beanName);
			// 开始匹配和合并
			List matchedNames = getMatchedNames(names, yamlBean);
			for (Iterator iter1 = matchedNames.iterator(); iter1.hasNext();) {
				String beanId = (String) iter1.next();
				mergeDefinition(beanId, factory.getBeanDefinition(beanId),
						dependecyDefinition);
			}
		}
	}

	public void mergeDefinition(String id, BeanDefinition target,
			BeanDefinition source) {
		try {
			if (null == target.getBeanClassName()) {
				return;
			}
			Class beanClass = Class.forName(target.getBeanClassName());
			MutablePropertyValues pvs = source.getPropertyValues();
			for (Iterator iter2 = pvs.getPropertyValueList().iterator(); iter2
					.hasNext();) {
				PropertyValue pv = (PropertyValue) iter2.next();
				String name = pv.getName();
				Field f = ReflectionUtils.findField(beanClass, name);
				if (null != f) {
					logger.info("replace {} with {}", id + '.' + name, pv
							.getValue());
					target.getPropertyValues().addPropertyValue(name,
							pv.getValue());
				}
			}
		} catch (ClassNotFoundException e) {
			logger.error("class not found", e);
		}
	}

	public List getMatchedNames(String[] names, YamlBeanDefinition yamlBean) {
		if (null == yamlBean.getPattern()) {
			return Collections.singletonList(yamlBean.getId());
		} else {
			List matchedNames = new ArrayList();
			for (int i = 0; i < names.length; i++) {
				if (yamlBean.matched(names[i])) {
					matchedNames.add(names[i]);
				}
			}
			return matchedNames;
		}
	}

	public String getDependencyLocation() {
		return dependencyLocation;
	}

	public void setDependencyLocation(String locaction) {
		this.dependencyLocation = locaction;
	}

}
