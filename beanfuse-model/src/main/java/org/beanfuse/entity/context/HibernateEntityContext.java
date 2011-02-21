//$Id: HibernateEntityContext.java May 3, 2008 9:13:49 PM chaostone Exp $
/*
 * Copyright c 2005-2009
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============   ============  ============
 * chaostone      May 3, 2008   Created
 *  
 ********************************************************************************/
package org.beanfuse.entity.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beanfuse.entity.Type;
import org.beanfuse.entity.types.CollectionType;
import org.beanfuse.entity.types.ComponentType;
import org.beanfuse.entity.types.EntityType;
import org.hibernate.EntityMode;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateEntityContext extends AbstractEntityContext {

	private static final Logger logger = LoggerFactory
			.getLogger(HibernateEntityContext.class);

	private final Map collectionTypes = new HashMap();

	public void initFrom(SessionFactory sessionFactory) {
		if (null != sessionFactory && entityTypes.isEmpty()) {
			Map classMetadatas = sessionFactory.getAllClassMetadata();
			for (Iterator iter = classMetadatas.values().iterator(); iter
					.hasNext();) {
				ClassMetadata cm = (ClassMetadata) iter.next();
				buildEntityType(sessionFactory, cm.getEntityName());
			}
			logger.info("success confiure {} entity types from hibernate!",
					new Integer(entityTypes.size()));
			logger.info("success confiure {} collection types from hibernate!",
					new Integer(collectionTypes.size()));

			if (logger.isDebugEnabled()) {
				loggerTypeInfo();
			}
			collectionTypes.clear();
		}
	}

	private void loggerTypeInfo() {
		List names = new ArrayList(entityTypes.keySet());
		Collections.sort(names);
		for (Iterator iter = names.iterator(); iter.hasNext();) {
			String entityName = (String) iter.next();
			EntityType entityType = (EntityType) entityTypes.get(entityName);
			logger.debug("entity:{}-->{}", entityType.getEntityName(),
					entityType.getEntityClass().getName());
			logger.debug("propertyType size:{}", new Integer(entityType
					.getPropertyTypes().size()));
		}
		names.clear();
		names.addAll(collectionTypes.keySet());
		Collections.sort(names);
		for (Iterator iter = names.iterator(); iter.hasNext();) {
			String entityName = (String) iter.next();
			CollectionType collectionType = (CollectionType) collectionTypes
					.get(entityName);
			logger.debug("collection:{}", collectionType.getName());
			logger.debug("class:{}", collectionType.getCollectionClass());
			logger.debug("elementType:{}", collectionType.getElementType()
					.getReturnedClass());
		}
	}

	/**
	 * 按照实体名，构建或者查找实体类型信息.<br>
	 * 调用后，实体类型则存放与entityTypes中.
	 * 
	 * @param entityName
	 * @return
	 */
	private EntityType buildEntityType(SessionFactory sessionFactory,
			String entityName) {
		EntityType entityType = (EntityType) entityTypes.get(entityName);
		if (null == entityType) {
			ClassMetadata cm = sessionFactory.getClassMetadata(entityName);
			if (null == cm) {
				logger.error("Cannot find ClassMetadata for {}", entityName);
				return null;
			}
			entityType = new EntityType();
			entityType.setEntityName(cm.getEntityName());
			entityType.setIdPropertyName(cm.getIdentifierPropertyName());
			entityType.setEntityClass(cm.getMappedClass(EntityMode.POJO));
			entityTypes.put(cm.getEntityName(), entityType);

			Map propertyTypes = entityType.getPropertyTypes();
			String[] ps = cm.getPropertyNames();
			for (int i = 0; i < ps.length; i++) {
				org.hibernate.type.Type type = cm.getPropertyType(ps[i]);
				if (type.isEntityType()) {
					propertyTypes.put(ps[i], buildEntityType(sessionFactory,
							type.getName()));
				} else if (type.isComponentType()) {
					propertyTypes.put(ps[i], buildComponentType(sessionFactory,
							entityName, ps[i]));
				} else if (type.isCollectionType()) {
					propertyTypes.put(ps[i], buildCollectionType(
							sessionFactory, defaultCollectionClass(type),
							entityName + "." + ps[i]));
				}
			}
		}
		return entityType;
	}

	private CollectionType buildCollectionType(SessionFactory sessionFactory,
			Class collectionClass, String role) {
		CollectionMetadata cm = sessionFactory.getCollectionMetadata(role);
		org.hibernate.type.Type type = cm.getElementType();
		EntityType elementType = null;
		if (type.isEntityType()) {
			elementType = (EntityType) entityTypes.get(type.getName());
			if (null == elementType) {
				elementType = buildEntityType(sessionFactory, type.getName());
			}
		} else {
			elementType = new EntityType(type.getReturnedClass());
		}

		CollectionType collectionType = new CollectionType();
		collectionType.setElementType(elementType);
		collectionType.setArray(cm.isArray());
		collectionType.setCollectionClass(collectionClass);
		if (!collectionTypes.containsKey(collectionType.getName())) {
			collectionTypes.put(collectionType.getName(), collectionType);
		}
		return collectionType;
	}

	private ComponentType buildComponentType(SessionFactory sessionFactory,
			String entityName, String propertyName) {
		EntityType entityType = (EntityType) entityTypes.get(entityName);
		if (null != entityType) {
			Type propertyType = (Type) entityType.getPropertyTypes().get(
					propertyName);
			if (null != propertyType) {
				return (ComponentType) propertyType;
			}
		}

		ClassMetadata cm = sessionFactory.getClassMetadata(entityName);
		org.hibernate.type.ComponentType hcType = (org.hibernate.type.ComponentType) cm
				.getPropertyType(propertyName);
		String[] propertyNames = hcType.getPropertyNames();

		ComponentType cType = new ComponentType(hcType.getReturnedClass());
		Map propertyTypes = cType.getPropertyTypes();
		for (int j = 0; j < propertyNames.length; j++) {
			org.hibernate.type.Type type = cm.getPropertyType(propertyName
					+ "." + propertyNames[j]);
			if (type.isEntityType()) {
				propertyTypes.put(propertyNames[j], buildEntityType(
						sessionFactory, type.getName()));
			} else if (type.isComponentType()) {
				propertyTypes.put(propertyNames[j], buildComponentType(
						sessionFactory, entityName, propertyName + "."
								+ propertyNames[j]));
			} else if (type.isCollectionType()) {
				propertyTypes.put(propertyNames[j], buildCollectionType(
						sessionFactory, defaultCollectionClass(type),
						entityName + "." + propertyName + "."
								+ propertyNames[j]));
			}
		}
		return cType;
	}

	private Class defaultCollectionClass(org.hibernate.type.Type collectionType) {
		if (collectionType.isAnyType()) {
			return null;
		} else if (org.hibernate.type.SetType.class
				.isAssignableFrom(collectionType.getClass())) {
			return HashSet.class;
		} else if (org.hibernate.type.MapType.class
				.isAssignableFrom(collectionType.getClass())) {
			return HashMap.class;
		} else {
			return ArrayList.class;
		}
	}

}
