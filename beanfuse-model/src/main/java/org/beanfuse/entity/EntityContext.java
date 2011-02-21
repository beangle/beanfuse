package org.beanfuse.entity;

import org.beanfuse.entity.types.EntityType;

public interface EntityContext {
	/**
	 * 根据实体名查找实体类型
	 * 
	 * @param entityName
	 * @return
	 */
	public Type getType(String name);

	/**
	 * 根据实体名查找实体类型
	 * 
	 * @param entityName
	 * @return null, if cannot find entityType
	 */
	public EntityType getEntityType(String entityName);

	/**
	 * 根据类型,查找实体类型<br>
	 * 找到实体名或者实体类名和指定类类名相同的entityType
	 * 
	 * @param entityClass
	 * @return
	 */
	public EntityType getEntityType(Class entityClass);

	/**
	 * 一个具体类所对应的实体名数组.
	 * 
	 * @param clazz
	 * @return
	 */
	public String[] getEntityNames(Class clazz);

}
