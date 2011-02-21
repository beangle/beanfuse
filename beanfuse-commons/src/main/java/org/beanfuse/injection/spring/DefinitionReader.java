package org.beanfuse.injection.spring;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.ho.util.BiDirectionalMap;
import org.ho.yaml.YamlConfig;
import org.ho.yaml.wrapper.OneArgConstructorTypeWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.ManagedSet;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

public class DefinitionReader {

	public static YamlConfig configureYaml() {
		YamlConfig config = new YamlConfig();
		BiDirectionalMap map = new BiDirectionalMap();
		map.put("map", ManagedMap.class.getName());
		map.put("list", ManagedList.class.getName());
		map.put("set", ManagedSet.class.getName());
		map.put("ref", RuntimeBeanReference.class.getName());
		map.put("props", Properties.class.getName());
		map.put("bean", YamlBeanDefinition.class.getName());
		config.setTransfers(map);
		config.getHandlers().put(
				RuntimeBeanReference.class.getName(),
				new OneArgConstructorTypeWrapper(RuntimeBeanReference.class,
						String.class.getName()));
		return config;
	}

	MutablePropertyValues convertProperties(Map props) {
		if (props == null) {
			return null;
		}
		MutablePropertyValues ret = new MutablePropertyValues();
		for (Iterator iter = props.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			Object value = props.get(name);
			ret.addPropertyValue(name, value);
		}
		return ret;
	}

	public int loadDefinitions(DefinitionFactory factory, Resource resource) {
		YamlConfig yaml = configureYaml();
		try {
			YamlSpringConfig config = (YamlSpringConfig) yaml.loadType(resource
					.getInputStream(), YamlSpringConfig.class);
			for (int i = 0; i < config.getBeans().length; i++) {
				YamlBeanDefinition bean = config.getBeans()[i];
				AbstractBeanDefinition bd = (AbstractBeanDefinition) BeanDefinitionReaderUtils
						.createBeanDefinition(null/* bean.getClazz() */, null,
								ClassUtils.getDefaultClassLoader());
				bd.setPropertyValues(convertProperties(bean.getProperties()));
				factory.registe(bean.getId(), bd);
				factory.registeYamlBean(bean.getId(), bean);
			}
			return config.getBeans().length;
		} catch (Exception e) {
			throw new BeanDefinitionStoreException(
					"Could not load spring configuration.", e);
		}
	}
}
