package org.beanfuse.transfer.importer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.beanfuse.entity.Model;
import org.beanfuse.entity.ObjectAndType;
import org.beanfuse.entity.Populator;
import org.beanfuse.entity.populator.EntityPopulator;
import org.beanfuse.entity.types.EntityType;
import org.beanfuse.model.Entity;
import org.beanfuse.transfer.TransferMessage;

public class MultiEntityImporter extends ItemImporter implements EntityImporter {

	protected Map current = new HashMap();

	protected Set foreignerKeys = new HashSet();

	protected Populator populator = new EntityPopulator();

	// [alias,entityType]
	protected Map entityTypes = new HashMap();

	public MultiEntityImporter() {
		super();
		foreignerKeys.add("code");
	}

	public void transferItem() {
		if (logger.isDebugEnabled()) {
			logger.debug("tranfer index:" + getTranferIndex() + ":" + ArrayUtils.toString(values));
		}
		// 在给定的值的范围内
		for (int i = 0; i < attrs.length; i++) {
			String value = (String) values.get(attrs[i]);
			// 过滤空列
			if (StringUtils.isBlank(attrs[i]))
				continue;
			// 处理空字符串并对所有的字符串进行trim
			if (value instanceof String) {
				String strValue = (String) value;
				if (StringUtils.isBlank(strValue)) {
					value = null;
				} else {
					value = StringUtils.trim(strValue);
				}
			}
			// 处理null值
			if (null == value) {
				continue;
			} else {
				if (value.equals(Model.NULL)) {
					value = null;
				}
			}
			Object entity = getCurrent(attrs[i]);
			String attr = processAttr(attrs[i]);
			String entityName = getEntityName(attrs[i]);
			// 当有深层次属性
			if (StringUtils.contains(attr, '.')) {
				if (null != foreignerKeys) {
					boolean isForeigner = isForeigner(attr);
					// 如果是个外键,先根据parentPath生成新的外键实体。
					// 因此导入的是外键,只能有一个属性导入.
					if (isForeigner) {
						String parentPath = StringUtils.substringBeforeLast(attr, ".");
						ObjectAndType propertyType = populator.initProperty(parentPath, entity,
								entityName);
						Object property = propertyType.getObj();
						if (property instanceof Entity) {
							if (((Entity) property).isPO()) {
								populator.populateValue(parentPath, null, entity);
								populator.initProperty(parentPath, entity, entityName);
							}
						}
					}
				}
			}
			populator.populateValue(attr, value, entity);
		}
	}

	public String processAttr(String attr) {
		return StringUtils.substringAfter(attr, ".");
	}

	protected Class getEntityClass(String attr) {
		return getEntityType(attr).getEntityClass();
	}

	protected EntityType getEntityType(String attr) {
		String alias = StringUtils.substringBefore(attr, ".");
		EntityType entityType = (EntityType) entityTypes.get(alias);
		if (null == entityType) {
			entityType = (EntityType) entityTypes.get(attr);
		}
		return entityType;
	}

	public void addEntity(String alias, Class entityClass) {
		EntityType entityType = Model.getEntityType(entityClass);
		if (null == entityType) {
			throw new RuntimeException("cannot find entity type for " + entityClass);
		}
		entityTypes.put(alias, entityType);
	}

	public void addEntity(String alias, String entityName) {
		EntityType entityType = Model.getEntityType(entityName);
		if (null == entityType) {
			throw new RuntimeException("cannot find entity type for " + entityName);
		}
		entityTypes.put(alias, entityType);
	}

	protected String getEntityName(String attr) {
		return getEntityType(attr).getEntityName();
	}

	public Object getCurrent(String attr) {
		String alias = StringUtils.substringBefore(attr, ".");
		Object entity = current.get(alias);
		if (null == entity) {
			EntityType entityType = (EntityType) entityTypes.get(alias);
			if (null == entityType) {
				logger.error("Not register entity type for {}", alias);
				throw new RuntimeException("Not register entity type for " + alias);
			} else {
				entity = entityType.newInstance();
				current.put(alias, entity);
				return entity;
			}
		}
		return entity;
	}

	public void beforeImport() {
		// 读取标题
		super.beforeImport();
		// 检查标题生命的属性是否在对象里面
		List errorAttrs = checkAttrs();
		if (!errorAttrs.isEmpty()) {
			transferResult.addFailure(TransferMessage.ERROR_ATTRS, errorAttrs.toString());
			throw new RuntimeException("error attrs:" + errorAttrs);
		}
	}

	/**
	 * 检查是否含有错误的属性描述
	 * TODO 没有对实体的简单属性进行检查，例如name
	 * @return
	 */
	protected List checkAttrs() {
		List errorAttrs = new ArrayList();
		List rightAttrs = new ArrayList();
		for (int i = 0; i < attrs.length; i++) {
			if (StringUtils.isBlank(attrs[i])) {
				continue;
			}
			try {
				EntityType entityType = getEntityType(attrs[i]);
				Entity example = (Entity) entityType.newInstance();
				String entityName = entityType.getEntityName();
				String attr = processAttr(attrs[i]);
				if (attr.indexOf('.') > -1) {
					populator.initProperty(StringUtils.substringBeforeLast(attr, "."), example,
							entityName);
				}
				rightAttrs.add(attrs[i]);
			} catch (Exception e) {
				errorAttrs.add(attrs[i]);
			}
		}

		attrs = new String[rightAttrs.size()];
		rightAttrs.toArray(attrs);
		return errorAttrs;
	}

	public Object getCurrent() {
		return current;
	}

	public void setCurrent(Object object) {
		this.current = (Map) object;
	}

	public String getDataName() {
		return "multi entity";
	}

	public void beforeImportItem() {
		this.current = new HashMap();
	}

	private boolean isForeigner(String attr) {
		String property = StringUtils.substringAfterLast(attr, ".");
		return foreignerKeys.contains(property);
	}

	public Set getForeignerKeys() {
		return foreignerKeys;
	}

	public void setForeignerKeys(Set foreignerKeys) {
		this.foreignerKeys = foreignerKeys;
	}

	public void addForeignedKeys(String foreignerKey) {
		foreignerKeys.add(foreignerKey);
	}

	public void setPopulator(Populator populator) {
		this.populator = populator;
	}

	public Map getEntityTypes() {
		return entityTypes;
	}

	public void setEntityTypes(Map entityTypes) {
		this.entityTypes = entityTypes;
	}

}
