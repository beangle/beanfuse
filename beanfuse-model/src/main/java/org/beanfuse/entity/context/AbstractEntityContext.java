package org.beanfuse.entity.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beanfuse.entity.EntityContext;
import org.beanfuse.entity.Type;
import org.beanfuse.entity.types.EntityType;
import org.beanfuse.entity.types.IdentifierType;
import org.beanfuse.model.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEntityContext implements EntityContext {
	/** entity-name->entity-type */
	protected Map<String, EntityType> entityTypes = new HashMap();

	/** class-name->entity-type */
	protected Map<String, EntityType> classEntityTypes = new HashMap();

	protected static final Logger logger = LoggerFactory.getLogger(AbstractEntityContext.class);

	public String[] getEntityNames(Class clazz) {
		return new String[0];
	}

	public Type getType(String name) {
		Type type = getEntityType(name);
		if (null == type) {
			try {
				return new IdentifierType(Class.forName(name));
			} catch (ClassNotFoundException e) {
				logger.error("system doesn't contains entity {}", name);
			}
			return null;
		} else {
			return type;
		}
	}

	public EntityType getEntityType(Class entityClass) {
		String className = entityClass.getName();

		EntityType type = entityTypes.get(className);
		if (null != type) {
			return type;
		}

		type = classEntityTypes.get(className);
		if (null == type) {
			List<EntityType> matched = new ArrayList();
			for (EntityType entityType : entityTypes.values()) {
				if (className.equals(entityType.getEntityName())
						|| className.equals(entityType.getEntityClass().getName())) {
					matched.add(entityType);
				}
			}
			if (matched.size() > 1) {
				throw new RuntimeException("multi-entityName for class:" + className);
			}
			if (matched.isEmpty()) {
				EntityType tmp = new EntityType(entityClass);
				classEntityTypes.put(className, tmp);
				return tmp;
			} else {
				classEntityTypes.put(className, matched.get(0));
				type = (EntityType) matched.get(0);
			}
		}
		return type;
	}

	public EntityType getEntityType(String entityName) {
		EntityType type = entityTypes.get(entityName);
		if (null != type) {
			return type;
		}
		type = classEntityTypes.get(entityName);
		// last try by it's interface
		if (null == type) {
			try {
				Class entityClass = Class.forName(entityName);
				if (Entity.class.isAssignableFrom(entityClass)) {
					type = new EntityType(entityClass);
				}
			} catch (ClassNotFoundException e) {
				logger.error("system doesn't contains entity {}", entityName);
			}
		}
		return type;
	}
}
