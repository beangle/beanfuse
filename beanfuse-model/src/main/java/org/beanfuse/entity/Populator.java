package org.beanfuse.entity;

import java.util.Map;

public interface Populator {

	public Object populate(Map params, Object target);

	public Object populate(Map params, Class entityClass);

	public Object populate(Map params, Object target, String entityName);

	public Object populate(Map params, String entityName);

	public void populateValue(String attr, Object value, Object target);

	public void populateValue(String attr, Object value, Object target, String entityName);

	public ObjectAndType initProperty(String attr, Object target, String entityName);

}
